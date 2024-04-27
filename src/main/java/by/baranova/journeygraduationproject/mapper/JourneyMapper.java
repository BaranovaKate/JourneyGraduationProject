package by.baranova.journeygraduationproject.mapper;

import by.baranova.journeygraduationproject.dto.JourneyDto;
import by.baranova.journeygraduationproject.model.Journey;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
@AllArgsConstructor
@Component
public class JourneyMapper {

    public JourneyDto toDto(final Journey journey) {
        final JourneyDto dto = new JourneyDto();
        dto.setId(journey.getId());
        dto.setCountry(journey.getCountry());
        dto.setTown(journey.getTown());
        dto.setDateToJourney(journey.getDateToJourney());
        dto.setDateFromJourney(journey.getDateFromJourney());
        return dto;
    }

    public Journey toEntity(final JourneyDto dto) {
        final Journey entity = new Journey();
        entity.setId(dto.getId());
        entity.setCountry(dto.getCountry());
        entity.setTown(dto.getTown());
        entity.setDateToJourney(dto.getDateToJourney());
        entity.setDateFromJourney(dto.getDateFromJourney());
        return entity;
    }
}
