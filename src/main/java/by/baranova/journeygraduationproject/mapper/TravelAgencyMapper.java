package by.baranova.journeygraduationproject.mapper;


import by.baranova.journeygraduationproject.dto.TravelAgencyDto;
import by.baranova.journeygraduationproject.model.TravelAgency;
import org.springframework.stereotype.Component;


@Component
public final class TravelAgencyMapper {

    public static TravelAgencyDto toDto(final TravelAgency travelAgency) {
        final TravelAgencyDto dto = new TravelAgencyDto();
        dto.setId(travelAgency.getId());
        dto.setName(travelAgency.getName());
        return dto;
    }

    private TravelAgencyMapper() {
    }
}
