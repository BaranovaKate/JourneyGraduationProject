package by.baranova.journeygraduationproject.security.facade;

import by.baranova.journeygraduationproject.security.dto.LoginRequestDto;
import by.baranova.journeygraduationproject.security.dto.RegisterRequestDto;
import by.baranova.journeygraduationproject.security.dto.TokenResponseDto;
import by.baranova.journeygraduationproject.security.service.AuthService;
import by.baranova.journeygraduationproject.security.service.JwtService;
import by.baranova.journeygraduationproject.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthFacade {
    private final AuthService authService;
    private final UserService userService;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public TokenResponseDto login(LoginRequestDto dto){
        authService.login(dto.getUsername(), dto.getPassword());

        return jwtService.generateToken(dto.getUsername());

    }
    @Transactional
    public TokenResponseDto register(RegisterRequestDto dto){
        final String username = dto.getUsername();
        final String password = dto.getPassword();

        userService.createUser(username, password);
        authService.login(username, password);

        return jwtService.generateToken(username);
    }
}
