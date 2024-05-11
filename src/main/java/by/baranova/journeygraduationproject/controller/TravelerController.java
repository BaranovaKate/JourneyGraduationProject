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
import org.springframework.security.access.prepost.PreAuthorize;
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
            summary = "Получить список всех путешественников ",
            description = "Возвращает список всех путешественников"
    )
    public ResponseEntity<List<Traveler>> getAllTravelers() {
        List<Traveler> travelers = travelerService.getAllTravelers();
        LOGGER.info("Display all travelers");
        return ResponseEntity.ok(travelers);
    }

    @GetMapping("/{id}")
    @Operation(
            method = "GET",
            summary = "Получить Путешественника по ID",
            description = "Возвращает путешественника по ID"
    )
    public ResponseEntity<Traveler> getTravelerById(@PathVariable Long id) {
        try {
            LOGGER.info("Traveler with ID: {}", id);
            Traveler traveler = travelerService.findTravelerById(id);
            return ResponseEntity.ok(traveler);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Traveler not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            method = "POST",
            summary = "Создать нового путешественника",
            description = "Создает нового путешественника"
    )
    public ResponseEntity<String> addTraveler(
            @RequestBody Traveler traveler) {
        travelerService.addTraveler(traveler);
        LOGGER.info("Created new Traveler");
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created a traveler");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            method = "PUT",
            summary = "Обновить путешественника по id",
            description = "Обновляет существующего путешественника"
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            method = "DELETE",
            summary = "Удалить путешественника по id",
            description = "Удаляет путешесвенника по ID"
    )
    public ResponseEntity<String> deleteTraveler(@PathVariable Long id) {
        try {
            travelerService.deleteTraveler(id);
            LOGGER.info("Deleted Traveler by ID: {}", id);
            return ResponseEntity.ok("Successfully deleted traveler with ID: " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}