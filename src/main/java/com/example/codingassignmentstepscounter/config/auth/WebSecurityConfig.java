package com.example.codingassignmentstepscounter.config.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  private static final String[] WHITE_LIST = {
      // -- Swagger UI:
      "/api-docs/**",
      "/swagger-ui/**",
      "/swagger-ui.html",
      // -- API:
      "/api/login",
  };

  @Value("${authentication.username}")
  private String username;
  @Value("${authentication.userpwd}")
  private String userpass;

  @Bean
  public SecurityFilterChain protectedSecurityFilterChain(HttpSecurity http)
      throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)

        .securityContext(context ->
            context.securityContextRepository(securityContextRepository()))
        .requestCache(RequestCacheConfigurer::disable)

        .authorizeHttpRequests(authorize ->
            authorize
                .requestMatchers(WHITE_LIST).permitAll()
                .anyRequest().authenticated())

        .httpBasic(Customizer.withDefaults())
        .build();
  }

  @Bean
  public SecurityContextRepository securityContextRepository() {
    return new HttpSessionSecurityContextRepository();
  }

  @Bean
  public AuthenticationManager authManager(HttpSecurity http, UserDetailsService uds)
      throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(uds)
        .and()
        .build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails userDetails = User.withDefaultPasswordEncoder()
        .username(username)
        .password(userpass)
        .build();
    return new InMemoryUserDetailsManager(userDetails);
  }
}
