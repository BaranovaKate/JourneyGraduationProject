package by.baranova.journeygraduationproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "travel_agencies")
public class TravelAgency {
    private static final int CONST = 64;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = CONST)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "travelAgency",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("travelAgency")
    private List<Journey> journeys;
}
