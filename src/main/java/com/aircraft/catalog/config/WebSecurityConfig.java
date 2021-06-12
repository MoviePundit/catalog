package com.aircraft.catalog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Security custom configs
 *
 * @author Ashutosh Tomar
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private static final List<String> LIST = Collections.singletonList("*");

  @Value("${cors-ignore.domains}")
  private String[] domains;

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Autowired
  private UserDetailsService jwtUserDetailsService;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  /**
   * Configure AuthenticationManager so that it knows from where
   * to load user for matching credentials
   *
   * @param auth
   * @throws Exception
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      //no need for cross site request forgery
      .csrf()
      .disable()
      //no need for frame options
      .headers()
      .frameOptions()
      .disable()
      .and()
      //custom cors config
      .cors()
      .configurationSource(corsConfigurationSource())
      .and()
      // no authentication for these request
      .authorizeRequests()
      .antMatchers("/authenticate")
      .permitAll()
      .and()
      .authorizeRequests()
      .antMatchers("/h2-console/*")
      .permitAll()
      // all other requests need to be authenticated
      .anyRequest()
      .authenticated()
      .and()
      // auth custom exception handling
      .exceptionHandling()
      .authenticationEntryPoint(jwtAuthenticationEntryPoint)
      .and()
      // make sure we use stateless session
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Add a filter to validate the tokens with every request
    httpSecurity.addFilterBefore(
      jwtRequestFilter,
      UsernamePasswordAuthenticationFilter.class
    );
  }

  /**
   * custom cors configuration
   * @return CorsConfigurationSource
   */
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    List<String> allowOrigins = Arrays.asList(domains);
    configuration.setAllowedOrigins(allowOrigins);
    configuration.setAllowedMethods(LIST);
    configuration.setAllowedHeaders(LIST);
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  /**
   * Provide PasswordEncoder bean
   * @return PasswordEncoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Provide AuthenticationManager bean
   * @return AuthenticationManager
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
