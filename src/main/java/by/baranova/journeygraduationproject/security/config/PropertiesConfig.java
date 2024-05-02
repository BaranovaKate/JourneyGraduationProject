package by.baranova.journeygraduationproject.security.config;


import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = "by.ralovets.shop.config.property")
public class PropertiesConfig {
}
