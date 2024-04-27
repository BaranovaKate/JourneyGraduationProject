package by.baranova.journeygraduationproject.service;

import by.baranova.journeygraduationproject.model.TravelAgency;
import by.baranova.journeygraduationproject.repository.TravelAgencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AgencyService {

    private final TravelAgencyRepository travelAgencyRepository;

    public TravelAgency findAgencyById(final Long id) {
        return travelAgencyRepository.findById(id);
    }

    public void save(final TravelAgency travelAgency) {
        travelAgencyRepository.save(travelAgency);
    }

    public List<TravelAgency> findAgencies() {
        return travelAgencyRepository.findAllWithJourneys();
    }


}
