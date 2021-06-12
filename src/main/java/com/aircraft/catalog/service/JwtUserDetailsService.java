package com.aircraft.catalog.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * User details service
 *
 * @author Ashutosh Tomar
 *
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
  private static final String USER_NOT_FOUND_WITH_USERNAME =
    "User not found with username: ";

  /** username from properties */
  @Value("${jwt.username}")
  private String user;

  /** Password from properties */
  @Value("${jwt.password}")
  private String password;

  /**
   * Create a user
   * @param String username
   * @return UserDetails
   */
  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    if (user.equalsIgnoreCase(username)) {
      return new User(user, password, new ArrayList<>());
    } else {
      throw new UsernameNotFoundException(USER_NOT_FOUND_WITH_USERNAME + username);
    }
  }
}
