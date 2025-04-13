package org.uml.eshopas.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uml.eshopas.Models.Address;
import org.uml.eshopas.Models.City;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findAddressByStreetAndHouseNumber(String street, String houseNumber);
}
