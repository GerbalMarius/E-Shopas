package org.uml.eshopas.Repositories;

import org.uml.eshopas.Models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByGuest_id(Long guest_id);
}
