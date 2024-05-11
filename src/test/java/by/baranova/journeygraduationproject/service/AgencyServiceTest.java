package by.baranova.journeygraduationproject.service;

import by.baranova.journeygraduationproject.model.Journey;
import by.baranova.journeygraduationproject.model.TravelAgency;
import by.baranova.journeygraduationproject.repository.JourneyRepository;
import by.baranova.journeygraduationproject.repository.TravelAgencyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AgencyServiceTest {

    @Mock
    private TravelAgencyRepository travelAgencyRepository;

    @Mock
    private JourneyRepository journeyRepository;

    @InjectMocks
    private AgencyService agencyService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAgencyById_Found() {
        TravelAgency agency = new TravelAgency();
        agency.setId(1L);
        agency.setName("Test Agency");

        when(travelAgencyRepository.findAById(1L)).thenReturn(agency);

        TravelAgency foundAgency = agencyService.findAgencyById(1L);

        assertNotNull(foundAgency);
        assertEquals("Test Agency", foundAgency.getName());
    }

    @Test
    void testFindAgencyById_NotFound() {
        when(travelAgencyRepository.findAById(1L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> agencyService.findAgencyById(1L));
    }

    @Test
    void testSave() {
        TravelAgency agency = new TravelAgency();
        agency.setName("New Agency");

        agencyService.save(agency);

        verify(travelAgencyRepository, times(1)).save(agency);
    }

    @Test
    void testDeleteById_Found() {
        TravelAgency agency = new TravelAgency();
        agency.setId(1L);
        agency.setName("Agency to Delete");

        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey());

        when(travelAgencyRepository.findAById(1L)).thenReturn(agency);
        when(journeyRepository.findByTravelAgencyId(1L)).thenReturn(journeys);

        agencyService.deleteById(1L);

        verify(journeyRepository, times(1)).deleteAll(journeys);
        verify(travelAgencyRepository, times(1)).delete(agency);
    }

    @Test
    public void testDeleteById_NotFound() {
        when(travelAgencyRepository.findAById(1L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> agencyService.deleteById(1L));
    }

    @Test
    void testFindAgencies() {
        List<TravelAgency> agencies = new ArrayList<>();
        agencies.add(new TravelAgency());

        when(travelAgencyRepository.findAll()).thenReturn(agencies);

        List<TravelAgency> result = agencyService.findAgencies();

        assertEquals(1, result.size());
    }

    @Test
    void testUpdate_Found() {
        TravelAgency agency = new TravelAgency();
        agency.setId(1L);
        agency.setName("Old Name");

        TravelAgency updatedAgency = new TravelAgency();
        updatedAgency.setName("New Name");

        when(travelAgencyRepository.findAById(1L)).thenReturn(agency);

        agencyService.update(1L, updatedAgency);

        assertEquals("New Name", agency.getName());
        verify(travelAgencyRepository, times(1)).save(agency);
    }

    @Test
    void testUpdate_NotFound() {
        TravelAgency updatedAgency = new TravelAgency();
        updatedAgency.setName("New Name");

        when(travelAgencyRepository.findAById(1L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> agencyService.update(1L, updatedAgency));
    }
}