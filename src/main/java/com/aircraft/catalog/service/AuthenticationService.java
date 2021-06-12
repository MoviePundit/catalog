package com.aircraft.catalog.service;

import com.aircraft.catalog.exceptions.InvalidCredentialsException;
import com.aircraft.catalog.model.JwtRequest;
import com.aircraft.catalog.model.JwtResponse;
import com.aircraft.catalog.utility.JwtTokenUtil;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private UserDetailsService jwtInMemoryUserDetailsService;

  /**
   * Authenticate request
   * Attempts to authenticate the passed authentication request,
   * returning a Jwt response with a valid token
   * if successful.
   *
   * @param authenticationRequest
   * @return JwtResponse
   * @throws InvalidCredentialsException
   */
  public JwtResponse authenticateRequest(JwtRequest authenticationRequest)
    throws InvalidCredentialsException {
    authenticate(
      authenticationRequest.getUsername(),
      authenticationRequest.getPassword()
    );
    // get user from user service
    final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(
      authenticationRequest.getUsername()
    );

    //generate token
    final String token = jwtTokenUtil.generateToken(userDetails);

    return new JwtResponse(token);
  }

  /**
   * Attempts to authenticate the passed parameters,
   * throws exception if invalid credentials are found invalid
   *
   * @param username
   * @param password
   * @throws InvalidCredentialsException
   */
  private void authenticate(String username, String password)
    throws InvalidCredentialsException {
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
      );
    } catch (BadCredentialsException e) {
      throw new InvalidCredentialsException(
        "Invalid Credentials",
        HttpStatus.UNAUTHORIZED
      );
    } catch (AuthenticationException e) {
      throw new InvalidCredentialsException(
        "Authentication Failed",
        HttpStatus.UNAUTHORIZED
      );
    }
  }
}
