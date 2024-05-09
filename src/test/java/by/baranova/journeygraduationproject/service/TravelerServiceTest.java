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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TravelerServiceTest {

    @Mock
    private TravelerRepository travelerRepository;

    @Mock
    private JourneyRepository journeyRepository;

    @InjectMocks
    private TravelerService travelerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTravelers() {
        // Given
        List<Traveler> travelers = new ArrayList<>();
        Traveler traveler1 = new Traveler();
        Traveler traveler2 = new Traveler();
        travelers.add(traveler1);
        travelers.add(traveler2);

        when(travelerRepository.findAllWithDetails()).thenReturn(travelers);

        // When
        List<Traveler> result = travelerService.getAllTravelers();

        // Then
        assertEquals(2, result.size());
        verify(travelerRepository, times(1)).findAllWithDetails();
    }

    @Test
    void testFindTravelerById() {
        // Given
        Long id = 1L;
        Traveler traveler = new Traveler();
        traveler.setId(id);

        when(travelerRepository.findByIdWithDetails(id)).thenReturn(traveler);

        // When
        Traveler result = travelerService.findTravelerById(id);

        // Then
        assertEquals(id, result.getId());
        verify(travelerRepository, times(1)).findByIdWithDetails(id);
    }

    @Test
    void testAddTraveler() {
        // Given
        Traveler traveler = new Traveler();

        // When
        travelerService.addTraveler(traveler);

        // Then
        verify(travelerRepository, times(1)).save(traveler);
    }

    @Test
    void testUpdateTraveler() {
        // Given
        Long id = 1L;
        Traveler existingTraveler = new Traveler();
        existingTraveler.setId(id);
        existingTraveler.setName("Old Name");

        Traveler updatedTraveler = new Traveler();
        updatedTraveler.setName("New Name");

        when(travelerRepository.findByIdWithDetails(id)).thenReturn(existingTraveler);

        // When
        travelerService.updateTraveler(id, updatedTraveler);

        // Then
        assertEquals("New Name", existingTraveler.getName());
        verify(travelerRepository, times(1)).save(existingTraveler);
    }

//    @Test
//    void testDeleteTraveler() {
//        // Given
//        Long id = 1L;
//        Traveler traveler = new Traveler();
//        traveler.setId(id);
//
//        Journey journey = new Journey();
//        journey.setId(1L);
//        journey.getTravelers().add(traveler);
//
//        List<Journey> journeys = List.of(journey);
//        traveler.setJourneys(journeys);
//
//        when(travelerRepository.findByIdWithDetails(id)).thenReturn(traveler);
//
//        // When
//        travelerService.deleteTraveler(id);
//
//        // Then
//        verify(travelerRepository, times(1)).delete(traveler);
//        verify(journeyRepository, times(1)).save(journey);
//        assertFalse(journey.getTravelers().contains(traveler));
//    }

//    @Test
//    void testFindTravelerById_NotFound() {
//        // Given
//        Long id = 1L;
//
//        when(travelerRepository.findByIdWithDetails(id)).thenReturn(null);
//
//        // When/Then
//        assertThrows(EntityNotFoundException.class, () -> travelerService.findTravelerById(id));
//    }
}