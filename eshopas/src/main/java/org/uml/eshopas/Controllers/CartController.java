package org.uml.eshopas.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uml.eshopas.EshopApplication;
import org.uml.eshopas.Models.Cart;

import lombok.val;

import org.uml.eshopas.Repositories.CartRepository;
import org.uml.eshopas.Services.ProductService;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("api/cart")
@CrossOrigin(origins = EshopApplication.REACT_FRONT_URL, allowCredentials = "true")
public class CartController {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartController(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    public ResponseEntity<Cart> requestCurrentCart(Long productId){
        Optional<Cart> currCart = cartRepository.findCartByGuest_id(1l);
        Cart cart;
        if(currCart.isEmpty()){
            cart = new Cart();
            cart.setTotalPrice(BigDecimal.valueOf(0));
            //cart.setGuest();
            cartRepository.save(cart);
        }else{
            cart = currCart.get();
        }
        if (productService.addProductToCart(cart.getId(), productId, 1) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(currCart.get());
    }

}
