package by.baranova.journeygraduationproject.controller;

import by.baranova.journeygraduationproject.model.Journey;
import by.baranova.journeygraduationproject.service.JourneyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journeys")
@AllArgsConstructor
@Tag(name = "Работа с путешествиями", description = "Данный "
        + "контроллер позволяет получать, добавлять, "
        + "обновлять и удалять путешествия")
public class JourneyController {

    private final JourneyService journeyService;

    static final Logger LOGGER = LogManager.getLogger(JourneyController.class);

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Получить список всех путешествий",
            description = "Выводит список всех путешествий. "
                    + "Так же выполняет поиск по стране"
    )
    public List<Journey> findJourneys() {
        LOGGER.info("Display all Journeys");
        return journeyService.findJourneys();
    }

    @GetMapping("/{id}")
    @Operation(
            method = "GET",
            summary = "Получить путешествие по id",
            description = "Выводит путешествие по id,"
                    + " содержащееся в базе данных"
    )
    public ResponseEntity<Journey> findJourney(@PathVariable Long id) {
        try {
            Journey journey = journeyService.findJourneyById(id);
            return ResponseEntity.ok(journey);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Journey not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            method = "POST",
            summary = "Создать путешествие",
            description = "Создает новое путешествие в базе данных"
    )
    public ResponseEntity<String> createJourney(@Valid @RequestBody Journey newJourney) {
        try {
            journeyService.save(newJourney);
            LOGGER.info("Journey created: {}", newJourney);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created a new journey");
        } catch (Exception e) {
            LOGGER.error("Error creating journey: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating journey");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            method = "PUT",
            summary = "Обновить путешествие",
            description = "Обновляет информацию о путешествии в базе данных"
    )
    public ResponseEntity<String> updateJourney(@PathVariable Long id, @Valid @RequestBody Journey updatedJourney) {
        try {
            journeyService.update(id, updatedJourney);
            LOGGER.info("Journey updated: {}", updatedJourney);
            return ResponseEntity.ok("Successfully updated journey with ID: " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Journey not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey not found");
        } catch (Exception e) {
            LOGGER.error("Error updating journey: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating journey");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            method = "DELETE",
            summary = "Удалить путешествие по id",
            description = "Удаляет путешествие по id,"
                    + " из базы данных"
    )
    public ResponseEntity<String> deleteJourney(@PathVariable Long id) {
        try {
            journeyService.deleteById(id);
            LOGGER.info("Journey deleted");
            return ResponseEntity.ok("Journey deleted");
        } catch (EntityNotFoundException e) {
            LOGGER.error("Journey not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey not found");
        }
    }


    @PostMapping("/new/bulk/{agency}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            method = "PUT",
            summary = "Создать путешествие по агентству",
            description = "Создает новое путешествие в базе данных"
    )
    public ResponseEntity<String> createJourneysBulk(@RequestBody List<Journey> journeyDtos,
                                                     @PathVariable("agency") String agency) {
        LOGGER.info("POST endpoint /journeys/new/bulk/{agency} was called");

        try {
            journeyService.createJourneysBulk(journeyDtos, agency);
            return ResponseEntity.ok("Successfully created journeys in bulk for agency: " + agency);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}
