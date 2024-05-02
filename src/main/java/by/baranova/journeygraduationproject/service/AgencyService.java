package by.baranova.journeygraduationproject.service;

import by.baranova.journeygraduationproject.repository.JourneyRepository;
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
        TravelAgency agency = travelAgencyRepository.findAById(id);
        if (agency == null) {
            throw new EntityNotFoundException(
                    "Agency with ID " + id + " not found");
        }
        return agency;
    }

    public void deleteById(final Long id) {
        TravelAgency agencyToDelete = findAgencyById(id);
        if (agencyToDelete == null) {
            throw new EntityNotFoundException(
                    "Travel Agency with ID " + id + " not found");
        }
        List<Journey> journeysWithAgency = journeyRepository.findByTravelAgencyId(id);
        journeyRepository.deleteAll(journeysWithAgency);

        travelAgencyRepository.delete(agencyToDelete);
    }


    public List<TravelAgency> findAgencies() {
        return travelAgencyRepository.findAllWithJourneys();
    }

    public void update(final Long id, final TravelAgency updatedAgency) {
        TravelAgency agency = findAgencyById(id);
        agency.setName(updatedAgency.getName());
        travelAgencyRepository.save(agency);
    }
}
