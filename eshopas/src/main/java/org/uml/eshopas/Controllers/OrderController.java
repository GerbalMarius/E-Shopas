package org.uml.eshopas.Controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.*;
import org.uml.eshopas.DTOS.CartItem;
import org.uml.eshopas.EshopApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
