package org.uml.eshopas.Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.aop.support.AopUtils;
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
import org.uml.eshopas.Repositories.GuestRepository;

@RestController
@RequestMapping("api/cart")
@CrossOrigin(origins = EshopApplication.REACT_FRONT_URL, allowCredentials = "true")
public class CartController {
    private final CartRepository cartRepository;
    private final GuestRepository guestRepository;

    public CartController(CartRepository cartRepository, GuestRepository guestRepository) {
        this.cartRepository = cartRepository;
        this.guestRepository = guestRepository;
    }

    Cart requestCurrentCart(Long Guest_id){
        val currCart = cartRepository.findCartByGuest_id(Guest_id);

        if(!currCart.isPresent()){
            Cart cart = new Cart();
            cart.setTotalPrice(BigDecimal.valueOf(0));
            cart.setGuest(guestRepository.getReferenceById(Guest_id));
            cartRepository.save(cart);
            return cart;
        }
        return currCart.get();
    }

    @GetMapping("/cartItems")
    ResponseEntity<List<CartItem>> requestCurrentCartItems(){        
        val currCart = requestCurrentCart(1l); // change later
        List<CartItem> currItems = new ArrayList<>();

        for (val item : currCart.getCartProducts()) {
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
