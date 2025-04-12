package org.uml.eshopas.Controllers;

import com.stripe.Stripe;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uml.eshopas.EshopApplication;

@RestController
@CrossOrigin(origins = EshopApplication.REACT_FRONT_URL, allowCredentials = "true")
@RequestMapping("api/order")
public class OrderController {

    private static final String API_KEY_BACKEND = "SECRET_KEY_STRIPE";

    public OrderController() {
        Stripe.apiKey = System.getenv(API_KEY_BACKEND);
    }
}
