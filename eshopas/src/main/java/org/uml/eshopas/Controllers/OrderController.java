package org.uml.eshopas.Controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.uml.eshopas.DTOS.CartItem;
import org.uml.eshopas.DTOS.PaymentDetails;
import org.uml.eshopas.EshopApplication;

import org.uml.eshopas.Models.*;
import org.uml.eshopas.Repositories.AddressRepository;
import org.uml.eshopas.Repositories.CartRepository;
import org.uml.eshopas.Repositories.CityRepository;
import org.uml.eshopas.Repositories.GuestRepository;
import org.uml.eshopas.Repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = EshopApplication.REACT_FRONT_URL, allowCredentials = "true")
@RequestMapping("api/order")
@Slf4j
public class OrderController {

    private static final String API_KEY_BACKEND = "SECRET_KEY_STRIPE";

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    private final GuestRepository guestRepository;

    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;



    public OrderController(CartRepository cartRepository, OrderRepository orderRepository,
                           GuestRepository guestRepository,
                           AddressRepository addressRepository, CityRepository cityRepository) {


        Stripe.apiKey = System.getenv(API_KEY_BACKEND);

        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.guestRepository = guestRepository;
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
    }

    @PostMapping("/payment-intent")
    public Map<String, String> payment(@RequestBody List<CartItem> cartItems) throws StripeException {
        long totalAmount = cartItems.stream()
                .mapToLong(item -> (long) (item.getPrice() * 100 * item.getQuantity()))
                .sum(); // Convert to cents and sum

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(totalAmount)
                .setCurrency("EUR")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                ).build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        Map<String, String> response = new HashMap<>();
        response.put("clientSecret", paymentIntent.getClientSecret());
        return response;
    }

    @PostMapping("/submit-payment")
    @Transactional
    public ResponseEntity<Map<String, List<String>>> submitPayment(@Valid @RequestBody PaymentDetails details, BindingResult bindingResult, HttpSession session){

        if (bindingResult.hasErrors()) {
            attemptRefund(details.paymentIntentId());

            Map<String, List<String>> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.computeIfAbsent(error.getField(), key -> new ArrayList<>()).add(error.getDefaultMessage())
            );

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        City city = cityRepository.findCityByName(details.cityName());
        if (city == null) {
            city = new City();
            city.setName(details.cityName());
            city = cityRepository.save(city);
        }

        Address address = addressRepository.findAddressByStreetAndHouseNumber(details.street(), details.houseNumber());
        if (address == null) {
            address = new Address();
            address.setStreet(details.street());
            address.setHouseNumber(details.houseNumber());
            address.setCity(city);
            address = addressRepository.save(address);
        }

        Guest guest = new Guest();
        guest.setName(details.firstName());
        guest.setLastName(details.lastName());
        guest.setEmail(details.email());
        guest.setTelephoneNumber(details.phone());

        Cart cart = new Cart();
        cart.setGuest(guest);
        guest.setCart(cart);

        guest = guestRepository.save(guest);

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.SUBMITTED);
        order.setDeliveryMethod(DeliveryMethod.AT_LOCATION);
        order.setDeliveryAddress(address);
        order.setCart(guest.getCart());

        orderRepository.save(order);

        System.out.println("Cart ID before saving order: " + guest.getCart().getId());
        System.out.println("Payment info received: " + details);

        return new ResponseEntity<>(Collections.emptyMap(), HttpStatus.OK);
    }

    private void attemptRefund(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            if (!Objects.equals(paymentIntent.getStatus(), "succeeded")) {
                System.out.println("PaymentIntent not in a refundable state: " + paymentIntent.getStatus());
                return;
            }

            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(paymentIntentId)
                    .build();

            Refund refund = Refund.create(params);
            System.out.println("Refund successful: " + refund.getId());

        } catch (StripeException e) {
            System.err.println("Failed to refund: " + e.getMessage());
        }
    }
}
