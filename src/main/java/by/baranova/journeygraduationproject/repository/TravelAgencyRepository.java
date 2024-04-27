package by.baranova.journeygraduationproject.repository;

import by.baranova.journeygraduationproject.dto.TravelAgencyDto;
import by.baranova.journeygraduationproject.model.TravelAgency;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class TravelAgencyRepository {

    private final SessionFactory sessionFactory;

    public TravelAgency findById(final Long id) {
        return sessionFactory.fromSession(session -> {
            Query<TravelAgency> query = session.createQuery("""
                            FROM TravelAgency J LEFT JOIN FETCH J.journeys\s
                            WHERE J.id = :id""",
                    TravelAgency.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }

    public List<TravelAgency> findAllWithJourneys() {
        return sessionFactory.fromSession(session -> {
            Query<TravelAgency> query = session
                    .createQuery(
                            "FROM TravelAgency J LEFT JOIN FETCH J.journeys",
                            TravelAgency.class);
            return query.list();
        });
    }

    public void save(final TravelAgency travelAgency) {
        sessionFactory.inTransaction(session -> session.persist(travelAgency));
    }

    public void deleteById(final Long id) {
        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session
                    .createMutationQuery(
                            "DELETE FROM TravelAgency WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
        });
    }

    public void update(final Long id, final TravelAgencyDto updatedAgency) {
        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session.createMutationQuery("""
                    UPDATE TravelAgency T SET
                       T.name = :name
                    WHERE T.id = :id
                    """);

            query.setParameter("id", id);
            query.setParameter("name", updatedAgency.getName());
            query.executeUpdate();
        });
    }

}
