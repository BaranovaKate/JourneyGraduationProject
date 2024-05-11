package by.baranova.journeygraduationproject.service;

import static org.junit.jupiter.api.Assertions.*;
import by.baranova.journeygraduationproject.model.Journey;
import by.baranova.journeygraduationproject.model.Traveler;
import by.baranova.journeygraduationproject.repository.JourneyRepository;
import by.baranova.journeygraduationproject.repository.TravelerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TravelerServiceTest {

    @Mock
    private TravelerRepository travelerRepository;

    @Mock
    private JourneyRepository journeyRepository;

    @InjectMocks
    private TravelerService travelerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTravelers() {
        List<Traveler> travelers = new ArrayList<>();
        travelers.add(new Traveler());
        when(travelerRepository.findAllWithDetails()).thenReturn(travelers);

        List<Traveler> result = travelerService.getAllTravelers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(travelerRepository, times(1)).findAllWithDetails();
    }

    @Test
    public void testFindTravelerById_Success() {
        Traveler traveler = new Traveler();
        traveler.setId(1L);
        when(travelerRepository.findByIdWithDetails(1L)).thenReturn(traveler);

        Traveler result = travelerService.findTravelerById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(travelerRepository, times(1)).findByIdWithDetails(1L);
    }

    @Test
    public void testFindTravelerById_NotFound() {

        when(travelerRepository.findByIdWithDetails(1L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            travelerService.findTravelerById(1L);
        });
    }

    @Test
    public void testAddTraveler() {
        Traveler traveler = new Traveler();
        travelerService.addTraveler(traveler);
        verify(travelerRepository, times(1)).save(traveler);
    }

    @Test
    public void testUpdateTraveler() {
        Traveler existingTraveler = new Traveler();
        existingTraveler.setId(1L);
        existingTraveler.setName("Old Name");

        Traveler updatedTraveler = new Traveler();
        updatedTraveler.setName("New Name");

        when(travelerRepository.findByIdWithDetails(1L)).thenReturn(existingTraveler);

        travelerService.updateTraveler(1L, updatedTraveler);

        assertEquals("New Name", existingTraveler.getName());
        verify(travelerRepository, times(1)).save(existingTraveler);
    }

    @Test
    public void testDeleteTraveler() {
        // Given
        Traveler traveler = new Traveler();
        traveler.setId(1L);
        Journey journey = new Journey();
        journey.setTravelers(new ArrayList<>(List.of(traveler)));

        traveler.setJourneys(new ArrayList<>(List.of(journey)));

        when(travelerRepository.findByIdWithDetails(1L)).thenReturn(traveler);

        travelerService.deleteTraveler(1L);

        verify(travelerRepository, times(1)).delete(traveler);
        verify(journeyRepository, times(1)).save(journey);
        assertFalse(journey.getTravelers().contains(traveler));
    }
}