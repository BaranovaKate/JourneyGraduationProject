package by.baranova.journeygraduationproject.service;

import by.baranova.journeygraduationproject.model.Journey;
import by.baranova.journeygraduationproject.model.TravelAgency;
import by.baranova.journeygraduationproject.repository.JourneyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class JourneyService {

    private final JourneyRepository journeyRepository;

    public Journey findJourneyById(final Long id) {
        return journeyRepository.findJourneyWithTravelersAndAgenciesById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Journey with ID " + id + " not found"));
    }

    public void deleteById(final Long id) {
        journeyRepository.deleteById(id);
    }

    public List<Journey> findJourneys() {
        return journeyRepository.findAllWithTravelersAndAgencies();
    }

    public void save(final Journey journey) {
        journeyRepository.save(journey);
    }

    public void update(final Long id, final Journey updatedJourney) {
        Journey journey = findJourneyById(id);
        journey.setTown(updatedJourney.getTown());
        journey.setCountry(updatedJourney.getCountry());
        journey.setDateToJourney(updatedJourney.getDateToJourney());
        journey.setDateFromJourney(updatedJourney.getDateFromJourney());
        journey.setTravelAgency(updatedJourney.getTravelAgency());
        journey.setTravelers(updatedJourney.getTravelers());
        journeyRepository.save(journey);
    }


    public void createJourneysBulk(List<Journey> journeyDtos, String agency) {
        if (journeyDtos == null || journeyDtos.isEmpty()) {
            throw new IllegalArgumentException("No journeys provided");
        }
        boolean agencyNameConflict = journeyDtos.stream()
                .anyMatch(journeyDto -> {
                    TravelAgency travelAgency = journeyDto.getTravelAgency();
                    return travelAgency == null || !travelAgency.getName().equals(agency);
                });
        if (agencyNameConflict) {
            throw new IllegalArgumentException("Agency name in URL does not" +
                    " match agency name in provided journey data");
        }
        List<String> errors = journeyDtos.stream()
                .map(journeyDto -> {
                    try {
                        journeyRepository.save(journeyDto);
                        return null;
                    } catch (Exception e) {
                        return "Error creating journey: " + e.getMessage();
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Errors occurred during bulk creation:\n"
                    + String.join("\n", errors));
        }
    }

}