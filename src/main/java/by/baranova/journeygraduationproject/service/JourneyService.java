package by.baranova.journeygraduationproject.service;

import by.baranova.journeygraduationproject.dto.JourneyDto;
import by.baranova.journeygraduationproject.repository.JourneyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class JourneyService {

    private final JourneyRepository journeyRepository;

    public JourneyDto findJourneyById(final Long id) {
        return journeyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Journey with ID " + id + " not found"));
    }

    public void deleteById(final Long id) {
        journeyRepository.deleteById(id);
    }

    public List<JourneyDto> findJourneys() {
        return journeyRepository.findAll();
    }

    public void save(final JourneyDto journeyDto) {
        journeyRepository.save(journeyDto);
    }

    public void update(final Long id, final JourneyDto journey) {
        journeyRepository.update(id, journey);
    }
}
