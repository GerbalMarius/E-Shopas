package org.uml.eshopas.Controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.uml.eshopas.DTOS.CartItem;
import org.uml.eshopas.DTOS.PaymentDetails;
import org.uml.eshopas.EshopApplication;

import java.util.*;

@RestController
@CrossOrigin(origins = EshopApplication.REACT_FRONT_URL, allowCredentials = "true")
@RequestMapping("api/order")
public class OrderController {

    private static final String API_KEY_BACKEND = "SECRET_KEY_STRIPE";

    public OrderController() {
        Stripe.apiKey = System.getenv(API_KEY_BACKEND);
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
    public ResponseEntity<Map<String, List<String>>> submitPayment(@Valid @RequestBody PaymentDetails details, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            attemptRefund(details.paymentIntentId());

            Map<String, List<String>> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.computeIfAbsent(error.getField(), key -> new ArrayList<>()).add(error.getDefaultMessage());
            });

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        System.out.println("Payment info received: " + details.email());
        return new ResponseEntity<>(Collections.emptyMap(), HttpStatus.OK);
    }

    private void attemptRefund(String paymentIntentId) {
        try {
            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(paymentIntentId)
                    .build();
            Refund.create(params);
        } catch (StripeException e) {
            System.err.println("Failed to refund: " + e.getMessage());
        }
    }
}
