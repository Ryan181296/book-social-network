package com.socialnetwork.gateway.config;

import com.socialnetwork.gateway.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebClientConfig {
    @NonFinal
    @Value("${clients.identity.host}")
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
