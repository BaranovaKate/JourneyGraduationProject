package by.baranova.journeygraduationproject.controller;

import by.baranova.journeygraduationproject.model.TravelAgency;
import by.baranova.journeygraduationproject.service.AgencyService;
import lombok.AllArgsConstructor;
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
        TravelAgency travelAgency = agencyService.findAgencyById(id);
        return ResponseEntity.ok(travelAgency);

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

}
