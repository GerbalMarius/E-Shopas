package org.uml.eshopas.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uml.eshopas.Models.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
