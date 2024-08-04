package translate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomRestTemplateConfig {

    @Bean
    public RestTemplate customRestTemplate() {
        return new RestTemplate();
    }
}
