package by.baranova.journeygraduationproject.controller;

import by.baranova.journeygraduationproject.dto.TravelAgencyDto;
import by.baranova.journeygraduationproject.model.TravelAgency;
import by.baranova.journeygraduationproject.service.AgencyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/travel-agencies")
@AllArgsConstructor
public class TravelAgencyController {

    private final AgencyService agencyService;

    @GetMapping("/{id}")
    public ResponseEntity<TravelAgency> getTravelAgencyById(
            final @PathVariable Long id) {
        try {
            TravelAgency travelAgency = agencyService.findAgencyById(id);
            return ResponseEntity.ok(travelAgency);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")

    public List<TravelAgency> getAllTravelAgenciesWithJourneys() {
        return agencyService.findAgencies();
    }

    @PostMapping("/create")
    public String createTravelAgency(
            final @RequestBody TravelAgency newTravelAgency) {
        agencyService.save(newTravelAgency);
        return "Successfully created a new agency";
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTravelAgencyById(
            final @PathVariable Long id) {
        try {
            agencyService.deleteById(id);
            return ResponseEntity
                    .ok("Successfully deleted the agency with id: " + id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> handleAgencyUpdate(
            final @PathVariable Long id,
            final @RequestBody TravelAgencyDto travelAgency) {
        try {
            agencyService.update(id, travelAgency);
            return ResponseEntity
                    .ok("Successfully updated journey with id " + id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
