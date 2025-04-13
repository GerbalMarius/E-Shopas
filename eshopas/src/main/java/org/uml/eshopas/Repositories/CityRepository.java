package org.uml.eshopas.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uml.eshopas.Models.City;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    City findCityByName(String name);
}
