package org.uml.eshopas.Repositories;

import org.uml.eshopas.Models.Cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByGuest_id(Long guest_id);
}
