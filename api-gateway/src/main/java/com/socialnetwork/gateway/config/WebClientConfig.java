package com.socialnetwork.gateway.config;

import com.socialnetwork.gateway.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebClientConfig {
    @NonFinal
    @Value("${app.services.identity}")
    String identityBaseUrl;

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .baseUrl(identityBaseUrl)
                .build();
    }

    @Bean
    IdentityClient identityClient(WebClient webClient) {
        var httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();

        return httpServiceProxyFactory.createClient(IdentityClient.class);
    }
}
