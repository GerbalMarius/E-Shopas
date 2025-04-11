package Services;

import Repositories.CartRepository;
import Repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    public ProductService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }
}
