package by.baranova.journeygraduationproject.controller;

import by.baranova.journeygraduationproject.model.Traveler;
import by.baranova.journeygraduationproject.service.TravelerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/travelers")
@AllArgsConstructor
public class TravelerController {
    private static final Logger LOGGER = LogManager.getLogger(TravelAgencyController.class);

    private final TravelerService travelerService;

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Получить список всех туристических агентств",
            description = "Возвращает список всех туристических агентств"
    )
    public ResponseEntity<List<Traveler>> getAllTravelers() {
        List<Traveler> travelers = travelerService.getAllTravelers();
        LOGGER.info("Retrieved all Travel Agencies");
        return ResponseEntity.ok(travelers);
    }

    @GetMapping("/{id}")
    @Operation(
            method = "GET",
            summary = "Получить туристическое агентство по ID",
            description = "Возвращает туристическое агентство по ID"
    )
    public ResponseEntity<Traveler> getTravelerById(@PathVariable Long id) {
        try {
            LOGGER.info("Fetching Travel Agency with ID: {}", id);
            Traveler traveler = travelerService.findTravelerById(id);
            return ResponseEntity.ok(traveler);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Travel Agency not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    @Operation(
            method = "POST",
            summary = "Создать новое туристическое агентство",
            description = "Создает новое туристическое агентство"
    )
    public ResponseEntity<String> addTraveler(
            @RequestBody Traveler traveler) {
        travelerService.addTraveler(traveler);
        LOGGER.info("Created new Travel Agency");
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created a new agency");
    }

    @PutMapping("/{id}")
    @Operation(
            method = "PUT",
            summary = "Обновить туристическое агентство",
            description = "Обновляет существующее туристическое агентство"
    )
    public ResponseEntity<String> updateTraveler(
            @PathVariable Long id,
            @RequestBody Traveler updatedTraveler) {
        try {
            travelerService.updateTraveler(id, updatedTraveler);
            return ResponseEntity.ok("Successfully updated traveller with ID: " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            method = "DELETE",
            summary = "Удалить туристическое агентство",
            description = "Удаляет туристическое агентство по ID"
    )
    public ResponseEntity<String> deleteTraveler(@PathVariable Long id) {
        try {
            travelerService.deleteTraveler(id);
            LOGGER.info("Deleted Travel Agency by ID: {}", id);
            return ResponseEntity.ok("Successfully deleted agency with ID: " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}