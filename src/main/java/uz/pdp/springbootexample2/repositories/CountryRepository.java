package uz.pdp.springbootexample2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springbootexample2.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {


}
