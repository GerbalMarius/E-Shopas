package org.uml.eshopas.Services;

import org.uml.eshopas.Models.CartProduct;
import org.uml.eshopas.Models.Product;
import org.uml.eshopas.Repositories.CartRepository;
import org.uml.eshopas.Repositories.OrderRepository;
import org.uml.eshopas.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

import org.uml.eshopas.Models.Cart;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public final class ProductService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    public ProductService(CartRepository cartRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }


}
