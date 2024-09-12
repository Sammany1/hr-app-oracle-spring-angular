package com.example.oraclebridge.region;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/regions")
@CrossOrigin(origins = "http://localhost:4200")
public class RegionController {
    @Autowired
    private RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRegions() {
        return handleRequest(() -> {
            List<Region> regions = regionService.getAllRegions();
            return new ResponseEntity<>(regions, HttpStatus.OK);
        });
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<?> getRegionById(@PathVariable Integer id) {
        return handleRequest(() -> {
            Region region = regionService.getRegionById(id);
            if (region == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(region, HttpStatus.OK);
        });
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRegion(@RequestBody Region region) {
        return handleRequest(() -> {
            Integer newId = region.getRegionId();
            if (regionService.getRegionById(newId) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                regionService.addRegion(region);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        });
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRegion(@PathVariable Integer id, @RequestBody Region region) {
        return handleRequest(() -> {
            if (regionService.getRegionById(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            regionService.updateRegion(region);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteRegion(@PathVariable Integer id) {
        return handleRequest(() -> {
            if (regionService.getRegionById(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            regionService.deleteRegion(id);
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