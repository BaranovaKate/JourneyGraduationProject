package by.baranova.journeygraduationproject.repository;

import by.baranova.journeygraduationproject.model.TravelAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelAgencyRepository extends JpaRepository<TravelAgency, Long> {

    @org.springframework.data.jpa.repository.Query("SELECT ta FROM TravelAgency ta LEFT JOIN FETCH ta.journeys")
    List<TravelAgency> findAllWithJourneys();

    @Query("SELECT t FROM TravelAgency t LEFT JOIN FETCH t.journeys WHERE t.id = :id")
    TravelAgency findAById(@Param("id") Long id);

}
