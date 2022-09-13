package uz.pdp.springbootexample2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CountryDto {

    private Integer id;

    private String name;


    private String description;

    private int population;

    private double area;

    private Integer continentId;
    private String continentName;


    public CountryDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public CountryDto(Integer id, String name, int population, String continentName) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.continentName = continentName;
    }
}
