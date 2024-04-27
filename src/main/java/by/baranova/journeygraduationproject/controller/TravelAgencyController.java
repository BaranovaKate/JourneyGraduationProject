package by.baranova.journeygraduationproject.controller;

import by.baranova.journeygraduationproject.dto.TravelAgencyDto;
import by.baranova.journeygraduationproject.model.TravelAgency;
import by.baranova.journeygraduationproject.service.AgencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/travel-agencies")
@AllArgsConstructor
@Tag(name = "Работа с туристическими агенствами",
        description = "Данный контроллер позволяет получать,"
                +  " добавлять, обновлять и удалять тур агенства")
public class TravelAgencyController {
    private static final String ERROR = "404 Not Found: {}";

    private final AgencyService agencyService;

    static final Logger LOGGER = LogManager
            .getLogger(TravelAgencyController.class);


    @GetMapping("/{id}")
    @Operation(
            method = "GET",
            summary = "Получить список тур агентств по id",
            description = "Выводит тур агентство по id,"
                    + " содержащеесся в базе данных"
    )
    public ResponseEntity<TravelAgency> getTravelAgencyById(
            final @PathVariable Long id) {
        try {
            LOGGER.info("Display Travel Agencies by id");
            TravelAgency travelAgency = agencyService.findAgencyById(id);
            return ResponseEntity.ok(travelAgency);
        } catch (EntityNotFoundException e) {
            LOGGER.error(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    @Operation(
            method = "GET",
            summary = "Получить список всех тур агентств",
            description = "Выводит список всех тур агентств,"
                    + " содержащихся в базе данных"
    )
    public List<TravelAgency> getAllTravelAgenciesWithJourneys() {
        LOGGER.info("Display Travel Agencies");
        return agencyService.findAgencies();
    }

    @PostMapping("/create")
    @Operation(
            method = "POST",
            summary = "Создать туристическое агенство",
            description = "Создает новое туристическе агенство"
                    + " и добавляет его в базу данных."
    )
    public String createTravelAgency(
            final @RequestBody TravelAgency newTravelAgency) {
        agencyService.save(newTravelAgency);
        LOGGER.info("Create Travel Agencies");
        return "Successfully created a new agency";
    }

    @DeleteMapping("/{id}")
    @Operation(
            method = "DELETE",
            summary = "Удалить туристическое агенство",
            description = "Удаляет туристическое агенство из базы данных"
    )
    public ResponseEntity<String> deleteTravelAgencyById(
            final @PathVariable Long id) {
        try {
            agencyService.deleteById(id);
            LOGGER.info("Delete Travel Agencies by id");
            return ResponseEntity
                    .ok("Successfully deleted the agency with id: " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(
            method = "PUT",
            summary = "Обновить туристическое агентство",
            description = "Обновляет существуещее "
                    + "туристичесоке агенство в базе данных,"
    )
    public ResponseEntity<String> handleAgencyUpdate(
            final @PathVariable Long id,
            final @RequestBody TravelAgencyDto travelAgency) {
        try {
            agencyService.update(id, travelAgency);
            LOGGER.info("Update Travel Agencies by id");
            return ResponseEntity
                    .ok("Successfully updated journey with id " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
