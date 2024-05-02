package by.baranova.journeygraduationproject.security.repository;

import by.baranova.journeygraduationproject.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
