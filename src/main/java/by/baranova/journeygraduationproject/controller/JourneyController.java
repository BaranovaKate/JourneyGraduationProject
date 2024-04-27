package by.baranova.journeygraduationproject.controller;

import by.baranova.journeygraduationproject.dto.JourneyDto;
import by.baranova.journeygraduationproject.service.JourneyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journeys")
@AllArgsConstructor
public class JourneyController {

    private static final String ERROR = "404 Not Found: {}";

    private final JourneyService journeyService;

    @GetMapping
    public List<JourneyDto> findJourneys() {
        return journeyService.findJourneys();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JourneyDto> findJourney(
            final @PathVariable("id") Long id) {
        JourneyDto journey = journeyService.findJourneyById(id);
        return ResponseEntity.ok(journey);

    }

    @PostMapping("/new")
    public String handleJourneyCreation(
            final @Valid @RequestBody JourneyDto journey) {
        journeyService.save(journey);
        return "Successfully created a new journey";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> handleJourneyDelete(final @PathVariable Long id) {
        journeyService.deleteById(id);
        return ResponseEntity
                .ok("Successfully deleted journey with id " + id);
    }
}
