package org.uml.eshopas.Repositories;

import org.uml.eshopas.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
