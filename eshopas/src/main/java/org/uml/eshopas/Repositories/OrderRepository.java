package org.uml.eshopas.Repositories;

import org.uml.eshopas.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
