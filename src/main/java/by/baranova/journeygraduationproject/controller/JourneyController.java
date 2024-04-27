package by.baranova.journeygraduationproject.controller;

import by.baranova.journeygraduationproject.dto.JourneyDto;
import by.baranova.journeygraduationproject.service.JourneyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journeys")
@AllArgsConstructor

public class JourneyController {

    private final JourneyService journeyService;

    @GetMapping
    public List<JourneyDto> findJourneys() {
        return journeyService.findJourneys();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JourneyDto> findJourney(
            final @PathVariable("id") Long id) {
        try {
            JourneyDto journey = journeyService.findJourneyById(id);
            return ResponseEntity.ok(journey);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/new")
    public String handleJourneyCreation(
            final @Valid @RequestBody JourneyDto journey) {
        journeyService.save(journey);
        return "Successfully created a new journey";
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> handleJourneyUpdate(
            final @PathVariable Long id,
            final @Valid @RequestBody JourneyDto journey) {
        try {
            journeyService.update(id, journey);
            return ResponseEntity
                    .ok("Successfully updated journey with id " + id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> handleJourneyDelete(
            final @PathVariable Long id) {
        try {
            journeyService.deleteById(id);
            return ResponseEntity
                    .ok("Successfully deleted journey with id " + id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
