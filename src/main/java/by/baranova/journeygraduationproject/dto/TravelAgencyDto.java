package by.baranova.journeygraduationproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class TravelAgencyDto implements Serializable {

    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
}
