package org.uml.eshopas.Repositories;

import org.uml.eshopas.Models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
