package by.baranova.journeygraduationproject.security.service;

import by.baranova.journeygraduationproject.security.Role;
import by.baranova.journeygraduationproject.security.entity.User;
import by.baranova.journeygraduationproject.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

   @Transactional
    public void createUser(String username, String password) {
        final User user = new User();
        user.setUsername(username);

        final String passwordHash = passwordEncoder.encode(password);
        user.setPassword(passwordHash);

        user.setRole(Role.ROLE_USER);

        userRepository.save(user);
    }
}
