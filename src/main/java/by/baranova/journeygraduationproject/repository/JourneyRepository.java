package by.baranova.journeygraduationproject.repository;

import by.baranova.journeygraduationproject.dto.JourneyDto;
import by.baranova.journeygraduationproject.mapper.JourneyMapper;
import by.baranova.journeygraduationproject.model.Journey;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class JourneyRepository {

    private final SessionFactory sessionFactory;

    private final JourneyMapper journeyMapper;

    public List<JourneyDto> findAll() {
        final List<Journey> journeys = sessionFactory.fromSession(session -> {
            Query<Journey> query = session.createQuery(
                    "FROM Journey j JOIN FETCH j.travelAgency ", Journey.class);
            return query.list();
        });
        return journeys.stream()
                .map(journeyMapper::toDto)
                .toList();
    }

    public Optional<JourneyDto> findById(final Long id) {
        return Optional.ofNullable(sessionFactory.fromSession(session -> {
            Query<Journey> query = session.createQuery("""
                FROM Journey J JOIN FETCH J.travelAgency\s
                WHERE J.id = :id""", Journey.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        })).map(journeyMapper::toDto);
    }


    public void deleteById(final Long id) {
        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session.createMutationQuery("""
                DELETE FROM Journey
                WHERE id = :id
                """);
            query.setParameter("id", id);
            query.executeUpdate();
        });
    }

    public void save(final JourneyDto journeyDto) {
        final Journey journey = journeyMapper.toEntity(journeyDto);
        sessionFactory.inTransaction(session -> session.persist(journey));
    }
}
