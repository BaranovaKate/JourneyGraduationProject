package by.baranova.journeygraduationproject.security.entity;

import by.baranova.journeygraduationproject.security.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "users")
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}
