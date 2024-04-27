package by.baranova.journeygraduationproject.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.hibernate")
@Getter
@Setter
public class HibernateProperties {

    private String url;
    private String username;
    private String password;
}