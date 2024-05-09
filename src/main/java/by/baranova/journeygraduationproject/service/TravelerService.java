package by.baranova.journeygraduationproject.service;

import by.baranova.journeygraduationproject.model.Journey;
import by.baranova.journeygraduationproject.model.Traveler;
import by.baranova.journeygraduationproject.repository.JourneyRepository;
import by.baranova.journeygraduationproject.repository.TravelerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TravelerService {

    private final TravelerRepository travelerRepository;
    private final JourneyRepository journeyRepository;

    public List<Traveler> getAllTravelers() {
        return travelerRepository.findAllWithDetails();
    }

    public Traveler findTravelerById(Long id) {
        return travelerRepository.findByIdWithDetails(id);
    }

    public Traveler addTraveler(Traveler traveler) {
        return travelerRepository.save(traveler);
    }

    @Transactional
    public Traveler updateTraveler(Long id, Traveler updatedTraveler) {
        Traveler existingTraveler = findTravelerById(id);

        existingTraveler.setName(updatedTraveler.getName());

        return travelerRepository.save(existingTraveler);
    }


    @Transactional
    public void deleteTraveler(Long id) {
        Traveler traveler = findTravelerById(id);
        List<Journey> journeys = traveler.getJourneys();
        for (Journey journey : journeys) {
            journey.getTravelers().remove(traveler);
            journeyRepository.save(journey);
        }
        travelerRepository.delete(traveler);

    }

}