package io.obouh.backend.xpensetrackr.client.gocardless.config;

import io.obouh.backend.xpensetrackr.client.gocardless.interceptor.GoCardlessClientInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoCardlessFeignClientConfig {

    @Value("goCardless.token")
    private String token;

    @Bean
    public GoCardlessClientInterceptor goCardlessClientInterceptor() {
        return new GoCardlessClientInterceptor(token);
    }
}
