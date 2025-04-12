package org.uml.eshopas.Services;

import org.uml.eshopas.Repositories.CartRepository;
import org.uml.eshopas.Repositories.OrderRepository;
import org.uml.eshopas.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

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
