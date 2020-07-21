package com.rs.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class Oauth2ClientConfig extends WebSecurityConfigurerAdapter {

  public static final String CONTACT_CLIENT_CREDENTIALS_QUALIFIER = "contactConfig";

  @Value("${auth.endpoint}")
  private String accessTokenUri;

  @Value("${auth.client}")
  private String clientId;

  @Value("${auth.secret}")
  private String clientSecret;

  private final OAuth2ClientContext oAuth2ClientContext;

  public Oauth2ClientConfig(final OAuth2ClientContext oAuth2ClientContext) {
    this.oAuth2ClientContext = oAuth2ClientContext;
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.csrf().disable();
  }

  @Bean
  @Qualifier(CONTACT_CLIENT_CREDENTIALS_QUALIFIER)
  public OAuth2RestTemplate contactConfigRestTemplate() {
    return new OAuth2RestTemplate(contactCCResourceDetails(), oAuth2ClientContext);
  }

  private ClientCredentialsResourceDetails contactCCResourceDetails() {
    final ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
    details.setId(CONTACT_CLIENT_CREDENTIALS_QUALIFIER);
    details.setClientId(clientId);
    details.setClientSecret(clientSecret);
    details.setAccessTokenUri(accessTokenUri);
    return details;
  }
}
