package by.baranova.journeygraduationproject.security.service;

import by.baranova.journeygraduationproject.security.entity.User;
import by.baranova.journeygraduationproject.security.model.ExtendedUserDetails;
import by.baranova.journeygraduationproject.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public ExtendedUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден: %s".formatted(username));
        }

        return ExtendedUserDetails.create(
                optionalUser.get()
        );
    }
}
