package by.baranova.journeygraduationproject.service;
import by.baranova.journeygraduationproject.repository.JourneyRepository;
import by.baranova.journeygraduationproject.dto.TravelAgencyDto;
import by.baranova.journeygraduationproject.model.Journey;
import by.baranova.journeygraduationproject.model.TravelAgency;
import by.baranova.journeygraduationproject.repository.TravelAgencyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AgencyService {

    private final TravelAgencyRepository travelAgencyRepository;

    private final JourneyRepository journeyRepository;

    public TravelAgency findAgencyById(final Long id) {
        return travelAgencyRepository.findById(id);
    }

    public void save(final TravelAgency travelAgency) {
        travelAgencyRepository.save(travelAgency);
    }

    public List<TravelAgency> findAgencies() {
        return travelAgencyRepository.findAllWithJourneys();
    }

    public void deleteById(final Long id) {
        List<Journey> journeysWithAgency = journeyRepository
                .findByTravelAgencyId(id);
        journeysWithAgency.forEach(journey -> journeyRepository
                .deleteById(journey.getId()));
        travelAgencyRepository.deleteById(id);
    }

    public void update(final Long id, final TravelAgencyDto updatedAgency) {
        TravelAgency agency = findAgencyById(id);
        if (agency == null) {
            throw new EntityNotFoundException(
                    "Travel agency with ID " + id + " does not exist");
        }
        travelAgencyRepository.update(id, updatedAgency);
    }
}
