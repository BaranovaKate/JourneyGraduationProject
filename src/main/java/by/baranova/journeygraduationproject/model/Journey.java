package by.baranova.journeygraduationproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "journeys")
public class Journey {
    private static final int CONST = 32;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "country", nullable = false, length = CONST)
    private String country;

    @Column(name = "town", nullable = false, length = CONST)
    private String town;

    @Column(name = "dateToJourney", nullable = false)
    private LocalDate dateToJourney;

    @Column(name = "dateFromJourney", nullable = false)
    private LocalDate dateFromJourney;

    @ManyToOne
    @JoinColumn(name = "travel_agency_id", nullable = false)
    @JsonIgnoreProperties("journeys")
    private TravelAgency travelAgency;
}