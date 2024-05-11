package by.baranova.journeygraduationproject.repository;

import by.baranova.journeygraduationproject.model.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelerRepository extends JpaRepository<Traveler, Long> {
    @Query("SELECT t FROM Traveler t LEFT JOIN FETCH t.journeys j LEFT JOIN FETCH j.travelAgency")
    List<Traveler> findAllWithDetails();

    @Query("SELECT t FROM Traveler t LEFT JOIN FETCH t.journeys j LEFT JOIN FETCH j.travelAgency WHERE t.id = :id")
    Traveler findByIdWithDetails(Long id);
}