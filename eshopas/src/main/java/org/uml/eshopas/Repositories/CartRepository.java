package org.uml.eshopas.Repositories;

import org.uml.eshopas.Models.Cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uml.eshopas.Models.Guest;

import java.util.List;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByGuest_id(Long guest_id);

    Cart findCartByGuest_Email(String email);
}
