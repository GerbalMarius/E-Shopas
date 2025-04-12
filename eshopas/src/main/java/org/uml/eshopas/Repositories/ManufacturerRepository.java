package org.uml.eshopas.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uml.eshopas.Models.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
