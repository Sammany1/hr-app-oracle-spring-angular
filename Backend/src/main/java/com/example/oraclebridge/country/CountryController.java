package com.example.oraclebridge.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/countries")
@CrossOrigin(origins = "http://localhost:4200")
public class CountryController {
    @Autowired
    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCountries() {
        return handleRequest(() -> {
            List<Country> countries = countryService.getAllCountries();
            return new ResponseEntity<>(countries, HttpStatus.OK);
        });
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<?> getCountryById(@PathVariable String id) {
        return handleRequest(() -> {
            Country country = countryService.getCountryById(id);
            if (country == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(country, HttpStatus.OK);
        });
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCountry(@RequestBody Country country) {
        return handleRequest(() -> {
            String newId = country.getCountryId();
            if (countryService.getCountryById(newId) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                countryService.addCountry(country);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        });
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCountry(@PathVariable String id, @RequestBody Country country) {
        return handleRequest(() -> {
            if (countryService.getCountryById(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            countryService.updateCountry(country);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable String id) {
        return handleRequest(() -> {
            if (countryService.getCountryById(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            countryService.deleteCountry(id);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    private ResponseEntity<?> handleRequest(Supplier<ResponseEntity<?>> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}