package by.baranova.journeygraduationproject.controller;

import by.baranova.journeygraduationproject.dto.JourneyDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journeys")
@AllArgsConstructor
@Tag(name = "Работа с путешествиями", description = "Данный "
        + "контроллер позволяет получать, добавлять, "
        + "обновлять и удалять путешествия")
public class JourneyController {

    private static final String ERROR = "404 Not Found: {}";

    private final JourneyService journeyService;

    static final Logger LOGGER = LogManager.getLogger(JourneyController.class);

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Получить список всех путешествий",
            description = "Выводит список всех путешествий. "
                    + "Так же выполняет поиск по стране"
    )
    public List<JourneyDto> findJourneys() {
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
    public ResponseEntity<JourneyDto> findJourney(
            final @PathVariable("id") Long id) {
        try {
            LOGGER.info("Display Journey by id");
            JourneyDto journey = journeyService.findJourneyById(id);
            return ResponseEntity.ok(journey);
        } catch (EntityNotFoundException e) {
            LOGGER.error(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/new")
    @Operation(
            method = "POST",
            summary = "Создать путешествие",
            description = "Создает новое путешествие в базе данных"
    )
    public String handleJourneyCreation(
            final @Valid @RequestBody JourneyDto journey) {
        journeyService.save(journey);
        LOGGER.info("Create Journey");
        return "Successfully created a new journey";
    }

    @PutMapping("/{id}")
    @Operation(
            method = "PUT",
            summary = "Обновить путешествие",
            description = "Обновляет информацию о путешествии в базе данных"
    )
    public ResponseEntity<String> handleJourneyUpdate(
            final @PathVariable Long id,
            final @Valid @RequestBody JourneyDto journey) {
        try {
            journeyService.update(id, journey);
            LOGGER.info("Update Journey");
            return ResponseEntity
                    .ok("Successfully updated journey with id " + id);
        } catch (EntityNotFoundException e) {
            LOGGER
                    .error(ERROR, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            method = "DELETE",
            summary = "Удалить путешествие по id",
            description = "Удаляет путешествие по id,"
                    + " из базы данных"
    )
    public ResponseEntity<String> handleJourneyDelete(
            final @PathVariable Long id) {
        try {
            journeyService.deleteById(id);
            LOGGER.info("Delete Journey by id");
            return ResponseEntity
                    .ok("Successfully deleted journey with id " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
