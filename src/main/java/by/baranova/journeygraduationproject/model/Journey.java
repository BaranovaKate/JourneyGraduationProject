package by.baranova.journeygraduationproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "date_to_journey", nullable = false)
    private LocalDate dateToJourney;

    @Column(name = "date_from_journey", nullable = false)
    private LocalDate dateFromJourney;

    @ManyToOne
    @JoinColumn(name = "travel_agency_id", nullable = false)
    @JsonIgnoreProperties("journeys")
    private TravelAgency travelAgency;


    @ManyToMany
    @JoinTable(
            name = "journey_traveler",
            joinColumns = @JoinColumn(name = "journey_id"),
            inverseJoinColumns = @JoinColumn(name = "traveler_id")
    )
    @JsonIgnoreProperties("journeys")
    private List<Traveler> travelers;
}