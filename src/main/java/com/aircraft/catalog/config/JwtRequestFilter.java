package com.aircraft.catalog.config;

import com.aircraft.catalog.service.JwtUserDetailsService;
import com.aircraft.catalog.utility.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Class is used to intercept on every
 * request for validation of jwt token
 * @author Ashutosh Tomar
 */
@Log4j2
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  private static final String BEARER = "Bearer ";

  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  /**
   * Validate token by doFilterInternal guaranteed to be just invoked once per request
   *
   *@param HttpServletRequest request
   *@param HttpServletResponse response
   *@param FilterChain chain
   */
  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain chain
  )
    throws ServletException, IOException {
    final String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    String username = null;
    String jwtToken = null;
    // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
    if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith(BEARER)) {
      jwtToken = requestTokenHeader.substring(7);
      try {
        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        log.error("Unable to get JWT Token");
      } catch (ExpiredJwtException e) {
        log.error("JWT Token has expired");
      }
    } else {
      log.debug("JWT Token does not begin with Bearer String");
    }

    //Once we get the token validate it.
    if (
      Objects.nonNull(username) &&
      Objects.isNull(SecurityContextHolder.getContext().getAuthentication())
    ) {
      UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

      // if token is valid configure Spring Security to manually set authentication
      if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );
        usernamePasswordAuthenticationToken.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request)
        );
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
        SecurityContextHolder
          .getContext()
          .setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    chain.doFilter(request, response);
  }
}
