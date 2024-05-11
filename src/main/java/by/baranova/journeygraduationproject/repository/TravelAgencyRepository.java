package by.baranova.journeygraduationproject.repository;

import by.baranova.journeygraduationproject.model.TravelAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelAgencyRepository extends JpaRepository<TravelAgency, Long> {

    @Query("SELECT DISTINCT t FROM TravelAgency t WHERE t.id = :id")
    TravelAgency findAById(@Param("id") Long id);

}
