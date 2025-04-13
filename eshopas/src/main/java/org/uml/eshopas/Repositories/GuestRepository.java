package org.uml.eshopas.Repositories;

import org.uml.eshopas.Models.Guest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
