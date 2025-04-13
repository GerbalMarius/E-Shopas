package org.uml.eshopas.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uml.eshopas.EshopApplication;
import org.uml.eshopas.Models.Cart;
import org.uml.eshopas.Models.Product;
import org.uml.eshopas.DTOS.CartItem;
import org.uml.eshopas.Repositories.CategoryRepository;
import org.uml.eshopas.Repositories.ManufacturerRepository;

import lombok.val;

import org.uml.eshopas.Repositories.CartRepository;

@RestController
@RequestMapping("api/cart")
@CrossOrigin(origins = EshopApplication.REACT_FRONT_URL, allowCredentials = "true")
public class CartController {
    private final CartRepository cartRepository;

    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

   Optional<Cart> requestCurrentCart(Long Guest_id){
        return cartRepository.findCartByGuest_id(Guest_id);
    }

    @GetMapping("/cartItems")
    @Transactional
    ResponseEntity<List<CartItem>> requestCurrentCartItems(){
        System.out.println("Cart endpoint hit");
        
        val currCart = requestCurrentCart(1l); // change later

        List<CartItem> currItems = new ArrayList<>();

        if(!currCart.isPresent()){
            return ResponseEntity.ok(currItems);
        }

        for (val item : currCart.get().getCartProducts()) {
            System.out.println("Was");
            CartItem cartItem = new CartItem();
            Product product = item.getProduct();
            cartItem.setName(product.getName());
            cartItem.setPrice(product.getPrice().doubleValue());
            cartItem.setQuantity(item.getAmount());

            currItems.add(cartItem);
        }

        return ResponseEntity.ok(currItems);
    }

}
