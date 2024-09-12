package com.example.oraclebridge.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/locations")
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {
    @Autowired
    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<?> getAllLocations() {
        return handleRequest(() -> {
            List<Location> locations = locationService.getAllLocations();
            return new ResponseEntity<>(locations, HttpStatus.OK);
        });
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<?> getLocationById(@PathVariable Integer id) {
        return handleRequest(() -> {
            Location location = locationService.getLocationById(id);
            if (location == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(location, HttpStatus.OK);
        });
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addLocation(@RequestBody Location location) {
        return handleRequest(() -> {
            Integer newId = location.getLocationId();
            if (locationService.getLocationById(newId) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                locationService.addLocation(location);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        });
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateLocation(@PathVariable Integer id, @RequestBody Location location) {
        return handleRequest(() -> {
            if (locationService.getLocationById(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            locationService.updateLocation(location);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Integer id) {
        return handleRequest(() -> {
            if (locationService.getLocationById(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            locationService.deleteLocation(id);
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