package uz.pdp.springbootexample2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import uz.pdp.springbootexample2.entity.Continent;


@RepositoryRestResource(collectionResourceRel = "continents", path = "continent")
@CrossOrigin
public interface ContinentRepository extends JpaRepository<Continent, Integer> {
}
