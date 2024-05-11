package by.baranova.journeygraduationproject.service;

import by.baranova.journeygraduationproject.model.Journey;
import by.baranova.journeygraduationproject.repository.JourneyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class JourneyServiceTest {

    private JourneyRepository journeyRepository;
    private JourneyService journeyService;

    @BeforeEach
    void setUp() {
        journeyRepository = mock(JourneyRepository.class);
        journeyService = new JourneyService(journeyRepository);
    }

    @Test
    void testFindJourneyById_ExistingJourney() {
        Long id = 1L;
        Journey existingJourney = new Journey();
        existingJourney.setId(id);
        when(journeyRepository.findJourneyWithTravelersAndAgenciesById(id)).thenReturn(Optional.of(existingJourney));

        Journey foundJourney = journeyService.findJourneyById(id);

        assertNotNull(foundJourney);
        assertEquals(existingJourney.getId(), foundJourney.getId());
    }

    @Test
    void testFindJourneyById_NonExistingJourney() {
        Long id = 1L;
        when(journeyRepository.findJourneyWithTravelersAndAgenciesById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> journeyService.findJourneyById(id));
    }

    @Test
    void testDeleteById() {
        Long id = 1L;

        journeyService.deleteById(id);

        verify(journeyRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindJourneys() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey());
        journeys.add(new Journey());
        when(journeyRepository.findAllWithTravelersAndAgencies()).thenReturn(journeys);

        List<Journey> foundJourneys = journeyService.findJourneys();

        assertEquals(journeys.size(), foundJourneys.size());
    }

    @Test
    void testSave() {
        Journey journey = new Journey();

        journeyService.save(journey);

        verify(journeyRepository, times(1)).save(journey);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        Journey existingJourney = new Journey();
        existingJourney.setId(id);
        Journey updatedJourney = new Journey();
        updatedJourney.setId(id);
        updatedJourney.setCountry("New Country");
        updatedJourney.setTown("New Town");
        updatedJourney.setDateToJourney(LocalDate.now());
        updatedJourney.setDateFromJourney(LocalDate.now().plusDays(7));

        when(journeyRepository.findJourneyWithTravelersAndAgenciesById(id)).thenReturn(Optional.of(existingJourney));

        journeyService.update(id, updatedJourney);

        verify(journeyRepository, times(1)).save(existingJourney);
        assertEquals(updatedJourney.getCountry(), existingJourney.getCountry());
        assertEquals(updatedJourney.getTown(), existingJourney.getTown());
        assertEquals(updatedJourney.getDateToJourney(), existingJourney.getDateToJourney());
        assertEquals(updatedJourney.getDateFromJourney(), existingJourney.getDateFromJourney());
    }
}