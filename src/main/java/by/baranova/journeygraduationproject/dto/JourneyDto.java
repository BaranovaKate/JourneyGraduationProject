package by.baranova.journeygraduationproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class JourneyDto implements Serializable {

    private static final int CONST = 20;

    private Long id;

    @NotBlank
    @Size(min = 2, max = CONST)
    private String country;

    @NotBlank
    @Size(min = 2, max = CONST)
    private String town;

    @NotNull
    private LocalDate dateToJourney;

    @NotNull
    private LocalDate dateFromJourney;

    private TravelAgencyDto travelAgency;

}
