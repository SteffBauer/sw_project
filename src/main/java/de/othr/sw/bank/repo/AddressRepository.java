package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address,Long> {
    List<Address> findByCountryAndCityAndZipCodeAndStreetAndHouseNr(String country, String city,long zipCode, String street, int houseNr);
}
