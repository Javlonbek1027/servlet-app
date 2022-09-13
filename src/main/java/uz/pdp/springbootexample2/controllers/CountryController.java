package uz.pdp.springbootexample2.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springbootexample2.dto.CountryDto;
import uz.pdp.springbootexample2.entity.Continent;
import uz.pdp.springbootexample2.entity.Country;
import uz.pdp.springbootexample2.payload.ApiResponse;
import uz.pdp.springbootexample2.repositories.ContinentRepository;
import uz.pdp.springbootexample2.repositories.CountryRepository;
import uz.pdp.springbootexample2.services.CountryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/country")
public class CountryController {

    @Autowired
    CountryService countryService;

    @Autowired
    CountryRepository countryRepo;

    @Autowired
    ContinentRepository continentRepo;

    /**
     * BARCHA DAVLATLAR RO'YXATINI QAYTARADI (id, name)
     *
     * @return
     */
    @GetMapping
    public HttpEntity getAllCountries() {
        List<CountryDto> countryDtoList = countryService.getCountries();
        return ResponseEntity.ok(new ApiResponse("Success", true, countryDtoList));
    }

    /**
     * BERILGAN ID BO'YICHA DAVLATNI QAYTARADI (hamma fieldlarini)
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HttpEntity getCountryById(@PathVariable Integer id) {
        Country country = countryService.getCountryById(id);
        if (country != null) {
            return ResponseEntity.ok(new ApiResponse("Success", true, country));
        } else
            return ResponseEntity.badRequest().body("Country with id:" + id + " not found!!!");
    }

    @PostMapping
    public HttpEntity<CountryDto> saveCountry(@RequestBody CountryDto countryDto) {

        ResponseEntity<CountryDto> response = countryService.saveCountry(countryDto);

        return response;

    }

//    @PostMapping
//    public HttpEntity saveCountry(@RequestBody CountryDto countryDto) {
//
//        ResponseEntity response = countryService.saveNewCountry(countryDto);
//
//        return response;
//
//    }
//
//    @PutMapping("/{id}")
//    public HttpEntity editCountry(@PathVariable Integer id, @RequestBody CountryDto countryDto) {
//
//        ResponseEntity response = countryService.editCountryById(id, countryDto);
//        return response;
//    }


    //    @GetMapping()
//    public String greeting(
//            @RequestParam(name = "name",
//            defaultValue = "G9") String name,
//            @RequestParam(name = "surname",
//                    defaultValue = "") String surname
//    ){
//
//        return "Salom "+ name + " " + surname;
//    }


    @DeleteMapping("/{id}")
    public HttpEntity deleteCountryById(@PathVariable Integer id) {
        try {
            Country country = countryRepo.findById(id).orElseThrow(() -> new IllegalStateException("Not found"));
            String countryName = country.getName();
            countryRepo.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(countryName + " successfully deleted", true));

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse("Not found", false));

        }


    }


}
