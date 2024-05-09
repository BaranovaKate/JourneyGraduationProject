package by.baranova.journeygraduationproject.service;

import by.baranova.journeygraduationproject.model.Journey;
import by.baranova.journeygraduationproject.repository.JourneyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JourneyServiceTest {

    @Mock
    private JourneyRepository journeyRepository;

    @InjectMocks
    private JourneyService journeyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindJourneyById_Success() {
        // Given
        Journey expectedJourney = new Journey();
        expectedJourney.setId(1L);
        when(journeyRepository.findById(1L)).thenReturn(Optional.of(expectedJourney));

        // When
        Journey journey = journeyService.findJourneyById(1L);

        // Then
        assertNotNull(journey);
        assertEquals(1L, journey.getId());
    }

    @Test
    void testFindJourneyById_NotFound() {
        // Given
        when(journeyRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> journeyService.findJourneyById(1L)
        );

        // Then
        assertEquals("Journey with ID 1 not found", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        // Given
        doNothing().when(journeyRepository).deleteById(1L);

        // When
        journeyService.deleteById(1L);

        // Then
        verify(journeyRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindJourneys() {
        // Given
        List<Journey> journeys = new ArrayList<>();
        Journey journey1 = new Journey();
        Journey journey2 = new Journey();
        journeys.add(journey1);
        journeys.add(journey2);
        when(journeyRepository.findAll()).thenReturn(journeys);

        // When
        List<Journey> result = journeyService.findJourneys();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testSave() {
        // Given
        Journey journey = new Journey();
        journey.setId(1L); // Добавляем ID к объекту, чтобы симулировать успешное сохранение

        when(journeyRepository.save(journey)).thenReturn(journey);

        // When
        journeyService.save(journey);

        // Then
        verify(journeyRepository, times(1)).save(journey);
    }



    @Test
    void testUpdate_NotFound() {
        // Given
        when(journeyRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> journeyService.update(1L, new Journey())
        );

        // Then
        assertEquals("Journey with ID 1 not found", exception.getMessage());
    }
}
