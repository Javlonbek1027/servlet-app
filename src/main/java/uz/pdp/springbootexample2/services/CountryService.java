package uz.pdp.springbootexample2.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springbootexample2.dto.CountryDto;
import uz.pdp.springbootexample2.entity.Continent;
import uz.pdp.springbootexample2.entity.Country;
import uz.pdp.springbootexample2.payload.ApiResponse;
import uz.pdp.springbootexample2.repositories.ContinentRepository;
import uz.pdp.springbootexample2.repositories.CountryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepo;

    @Autowired
    ContinentRepository continentRepo;

    public List<CountryDto> getCountries() {
        List<Country> countryList = countryRepo.findAll();

        // 1.
        List<CountryDto> countryDtoList = new ArrayList<>();
        for (Country country : countryList) {
            countryDtoList.add(
                    new CountryDto(
                            country.getId(),
                            country.getName(),
                            country.getPopulation(),
                            country.getContinent().getName()
                    ));
        }
//        return countryDtoList;
        // ==========================

        //2.
        return countryList.stream().map(
                country -> new CountryDto(
                        country.getId(),
                        country.getName(),
                        country.getPopulation(),
                        country.getContinent().getName()
                )).collect(Collectors.toList());

        // ==========================
    }

    public Country getCountryById(Integer id) {
        Optional<Country> optionalCountry = countryRepo.findById(id);
        if (optionalCountry.isEmpty()) {
//            throw new IllegalStateException("Country with id:"+ id+ " not found!!!");
            return null;
        }

        return optionalCountry.get();
    }

    public ResponseEntity saveCountry(CountryDto countryDto) {

        try {
            Country country = new Country();
            if (countryDto.getId()!=null) {
                country =  countryRepo.findById(countryDto.getId()).orElseThrow(() -> new IllegalStateException("Country " +
                        "not " +
                        "found!!!"));
            }
            country.setName(countryDto.getName());
            country.setDescription(countryDto.getDescription());
            country.setArea(countryDto.getArea());
            country.setPopulation(countryDto.getPopulation());
            Optional<Continent> optionalContinent = continentRepo.findById(countryDto.getContinentId());
            if (optionalContinent.isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse("Continent not found!!", false));
            }
            Continent continentFromDb = optionalContinent.get();

            country.setContinent(continentFromDb);
            countryRepo.save(country);
            return ResponseEntity.ok(new ApiResponse("Successfully saved!!", true));
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse("Name unique bo'" +
                    "lishi kerak!!!", false));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("Xatolik yuz berdi!!!", false), HttpStatus.CONFLICT);
        }

    }
}


//    public ResponseEntity saveNewCountry(CountryDto countryDto) {
//
//        try {
//            Country yangiDavlat = new Country();
//            yangiDavlat.setName(countryDto.getName());
//            yangiDavlat.setDescription(countryDto.getDescription());
//            yangiDavlat.setArea(countryDto.getArea());
//            yangiDavlat.setPopulation(countryDto.getPopulation());
//            Optional<Continent> optionalContinent = continentRepo.findById(countryDto.getContinentId());
//            if (optionalContinent.isEmpty()) {
//                return ResponseEntity.badRequest().body(new ApiResponse("Continent not found!!", false));
//            }
//            Continent continentFromDb = optionalContinent.get();
//
//            yangiDavlat.setContinent(continentFromDb);
//            countryRepo.save(yangiDavlat);
//            return ResponseEntity.ok(new ApiResponse("Successfully saved!!", true));
//        } catch (DataIntegrityViolationException e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(new ApiResponse("Name unique bo'" +
//                    "lishi kerak!!!", false));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity(new ApiResponse("Xatolik yuz berdi!!!", false), HttpStatus.CONFLICT);
//        }
//    }

//    public ResponseEntity editCountryById(Integer id, CountryDto countryDto) {
//
//        try {
//
//            Country countryFromDb = countryRepo.findById(id).orElseThrow(() -> new IllegalStateException("Country not " +
//                    "found!!!"));
//
//
//            countryFromDb.setName(countryDto.getName());
//            countryFromDb.setDescription(countryDto.getDescription());
//            countryFromDb.setArea(countryDto.getArea());
//            countryFromDb.setPopulation(countryDto.getPopulation());
//            Optional<Continent> optionalContinent = continentRepo.findById(countryDto.getContinentId());
//            if (optionalContinent.isEmpty()) {
//                return ResponseEntity.badRequest().body(new ApiResponse("Continent not found!!", false));
//            }
//            Continent continentFromDb = optionalContinent.get();
//
//            countryFromDb.setContinent(continentFromDb);
//            countryRepo.save(countryFromDb);
//            return ResponseEntity.ok(new ApiResponse("Successfully edited!!", true));
//        } catch (DataIntegrityViolationException e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(new ApiResponse("Name unique bo'" +
//                    "lishi kerak!!!", false));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity(new ApiResponse("Xatolik yuz berdi!!!", false), HttpStatus.CONFLICT);
//        }
//
//    }




