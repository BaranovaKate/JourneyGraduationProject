package by.baranova.journeygraduationproject.controller;
import by.baranova.journeygraduationproject.model.Traveler;
import by.baranova.journeygraduationproject.service.TravelerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travelers")
@AllArgsConstructor
public class TravelerController {

    private final TravelerService travelerService;

    @GetMapping
    public ResponseEntity<List<Traveler>> getAllTravelers() {
        List<Traveler> travelers = travelerService.getAllTravelers();
        return ResponseEntity.ok(travelers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Traveler> getTravelerById(@PathVariable Long id) {
        try {
            Traveler traveler = travelerService.findTravelerById(id);
            return ResponseEntity.ok(traveler);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Traveler> addTraveler(
            @RequestBody Traveler traveler) {
        try {
            Traveler newTraveler = travelerService.addTraveler(traveler);
            return ResponseEntity.status(201).body(newTraveler);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Traveler> updateTraveler(
            @PathVariable Long id,
            @RequestBody Traveler updatedTraveler) {
            Traveler existingTraveler = travelerService.updateTraveler(id, updatedTraveler);
            return ResponseEntity.ok(existingTraveler);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraveler(@PathVariable Long id) {
        try {
            travelerService.deleteTraveler(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}