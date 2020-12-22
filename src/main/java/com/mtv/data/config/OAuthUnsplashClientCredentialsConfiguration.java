package com.mtv.data.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;

@Configuration
@RequiredArgsConstructor
public class OAuthUnsplashClientCredentialsConfiguration extends WebSecurityConfigurerAdapter {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2Client(oauth2Client ->
                oauth2Client.clientRegistrationRepository(this.clientRegistrationRepository)
                        .authorizedClientRepository(this.oAuth2AuthorizedClientRepository)
        );
    }

}