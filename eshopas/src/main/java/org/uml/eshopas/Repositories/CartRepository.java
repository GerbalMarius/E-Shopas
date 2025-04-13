package org.uml.eshopas.Repositories;

import org.uml.eshopas.Models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.uml.eshopas.Models.Guest;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findCartByGuest_Id(long guestId);

    Cart findCartByGuest_Email(String email);
}
