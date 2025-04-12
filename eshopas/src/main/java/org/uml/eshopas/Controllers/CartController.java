package org.uml.eshopas.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uml.eshopas.EshopApplication;

@RestController
@RequestMapping("api/cart")
@CrossOrigin(origins = EshopApplication.REACT_FRONT_URL, allowCredentials = "true")
public class CartController {


}
