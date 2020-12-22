package com.mtv.data.config;

import com.mtv.data.properties.UnsplashProperties;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizationCodeOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;

@Configuration
public class UnsplashFeignConfig {

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        AuthorizationCodeOAuth2AuthorizedClientProvider authorizationCodeOAuth2AuthorizedClientProvider =
                new AuthorizationCodeOAuth2AuthorizedClientProvider();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizationCodeOAuth2AuthorizedClientProvider);

        return authorizedClientManager;
    }

    @Bean
    public RequestInterceptor requestInterceptor(UnsplashProperties unsplashProperties) {
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer " + unsplashProperties.getToken());
        };
    }
}