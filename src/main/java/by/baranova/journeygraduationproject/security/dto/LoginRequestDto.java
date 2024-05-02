package by.baranova.journeygraduationproject.security.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
