package by.baranova.journeygraduationproject.service;

import by.baranova.journeygraduationproject.model.Journey;
import by.baranova.journeygraduationproject.repository.JourneyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

}