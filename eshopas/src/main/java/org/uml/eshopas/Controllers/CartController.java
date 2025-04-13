package org.uml.eshopas.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uml.eshopas.EshopApplication;
import org.uml.eshopas.Models.Cart;


import lombok.val;

import org.uml.eshopas.Models.CartProduct;
import org.uml.eshopas.Models.Product;
import org.uml.eshopas.Repositories.CartRepository;
import org.uml.eshopas.Repositories.ProductRepository;

import org.uml.eshopas.DTOS.CartItem;

import org.uml.eshopas.Repositories.GuestRepository;


@RestController
@RequestMapping("api/cart")
@CrossOrigin(origins = EshopApplication.REACT_FRONT_URL, allowCredentials = "true")
public class CartController {
    private final CartRepository cartRepository;
    private final GuestRepository guestRepository;
    private final ProductRepository productRepository;

    public CartController(CartRepository cartRepository, ProductRepository productRepository, GuestRepository guestRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.guestRepository = guestRepository;
    }

    public Cart addProductToCart(Long cartId, Long productId, int amount) {

        return null;
    }

    public ResponseEntity<Integer> requestCurrentCart(Long productId, int amount) {
        Optional<Cart> currCart = cartRepository.findCartByGuest_id(1l);
        Cart cart;
        if (currCart.isEmpty()) {
            cart = new Cart();
            cart.setTotalPrice(BigDecimal.valueOf(0));
            cartRepository.save(cart);
        } else {
            cart = currCart.get();
        }

        Optional<Product> product = productRepository.findById(productId);
        Optional<CartProduct> cartProduct = cart.getCartProducts().stream().filter(item -> item.getProduct().equals(product.get())).findAny();

        if (product.isPresent()) {
            amount = Math.min(amount, product.get().getUnits());
            product.get().setUnits(product.get().getUnits() - amount);

            if (cartProduct.isPresent()) {
                cartProduct.get().setAmount(cartProduct.get().getAmount() + amount);
            } else {
                CartProduct cartProd = new CartProduct();
                cartProd.setCart(cart);
                cartProd.setProduct(product.get());
                cartProd.setAmount(amount);

                cart.getCartProducts().add(cartProd);
                product.get().getCartProducts().add(cartProd);
            }
            productRepository.save(product.get());
            cartRepository.save(cart);

            return ResponseEntity.ok(amount);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
