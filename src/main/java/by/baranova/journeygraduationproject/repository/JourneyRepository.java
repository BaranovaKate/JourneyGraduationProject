package by.baranova.journeygraduationproject.repository;

import by.baranova.journeygraduationproject.model.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long> {

    @Query("SELECT j FROM Journey j WHERE j.travelAgency.id = :travelAgencyId")
    List<Journey> findByTravelAgencyId(@Param("travelAgencyId") Long travelAgencyId);

    @Query("SELECT DISTINCT j FROM Journey j "
            + "LEFT JOIN FETCH j.travelers LEFT JOIN FETCH j.travelAgency")
    List<Journey> findAllWithTravelersAndAgencies();

    @Query("SELECT DISTINCT j FROM Journey j LEFT JOIN FETCH "
            + "j.travelers LEFT JOIN FETCH j.travelAgency WHERE j.id = :id")
    Optional<Journey> findJourneyWithTravelersAndAgenciesById(@Param("id") Long id);
}
