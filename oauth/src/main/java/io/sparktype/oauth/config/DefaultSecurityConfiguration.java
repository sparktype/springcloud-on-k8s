package io.sparktype.oauth.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class DefaultSecurityConfiguration {
  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeRequests(authorizeRequests ->
            authorizeRequests.anyRequest().authenticated()
        )
        .formLogin(withDefaults());
    return http.build();
  }

  @Bean
  public UserDetailsService users() {
    UserDetails user = User.withDefaultPasswordEncoder()
        .username("user1")
        .password("password")
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user);
  }

}
