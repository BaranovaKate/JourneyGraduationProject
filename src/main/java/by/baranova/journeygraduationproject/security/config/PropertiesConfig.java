package by.baranova.journeygraduationproject.security.config;


import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = "by.baranova.journeygraduationproject.security.config.property")
public class PropertiesConfig {
}
