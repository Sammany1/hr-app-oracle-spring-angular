package com.example.oraclebridge.temp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/temp")
public class TempController {
    @Autowired
    private TempService tempService;

    public TempController(TempService tempService) {
        this.tempService = tempService;
    }
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Temp> temps = tempService.getAll();
            return new ResponseEntity<>(temps, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occurred");
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Temp temp = tempService.getById(id);
            if (temp != null) {
                return new ResponseEntity<>(temp, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id does not exist");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occurred");
        }
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTemp(@RequestBody Temp temp) {
        try {
            Temp newTemp = tempService.addTemp(temp);
            return new ResponseEntity<>(newTemp, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occurred");
        }
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTemp(@PathVariable Long id, @RequestBody Temp temp) {
        try {
            if (tempService.getById(id) == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("id does not exist");
            } else if (!temp.getId().equals(id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("the entered id does not match the provided one");
            } else {
                Temp updatedTemp = tempService.updateTemp(id, temp);
                return ResponseEntity.status(HttpStatus.OK).body("Updated Successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error occurred");
        }
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            if (tempService.getById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id does not exist");
            } else {
                tempService.deleteTemp(id);
                return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occurred");
        }
    }
}
