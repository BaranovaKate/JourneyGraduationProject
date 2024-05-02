package by.baranova.journeygraduationproject.security.service;

import by.baranova.journeygraduationproject.security.dto.TokenResponseDto;
import by.baranova.journeygraduationproject.security.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtils jwtUtils;

    public TokenResponseDto generateToken(String username){
        final Date now = new Date();
        final Date expiryDate = Date.from(Instant.now().plus(7, ChronoUnit.DAYS));

        final String token = jwtUtils.generateToken(username, now, expiryDate);
        return new TokenResponseDto(token);
    }
}
