package org.uml.eshopas.Controllers;

import com.stripe.Stripe;
import org.springframework.web.bind.annotation.*;
import org.uml.eshopas.DTOS.CartData;
import org.uml.eshopas.EshopApplication;

import java.util.Map;

@RestController
@CrossOrigin(origins = EshopApplication.REACT_FRONT_URL, allowCredentials = "true")
@RequestMapping("api/order")
public class OrderController {

    private static final String API_KEY_BACKEND = "SECRET_KEY_STRIPE";

    public OrderController() {
        Stripe.apiKey = System.getenv(API_KEY_BACKEND);
    }

    @PostMapping("/payment")
    public Map<String, String> payment(@RequestBody CartData order) {
        return null;
    }
}
