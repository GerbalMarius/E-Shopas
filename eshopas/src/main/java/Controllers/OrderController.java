package Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uml.eshopas.EshopApplication;

@RestController
@CrossOrigin(origins = EshopApplication.REACT_FRONT_URL, allowCredentials = "true")
@RequestMapping("api/order")
public class OrderController {
}
