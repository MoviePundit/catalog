package com.aircraft.catalog.config;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class JwtAuthenticationEntryPoint
  implements AuthenticationEntryPoint, Serializable {
  private static final long serialVersionUID = -7858869558953243875L;

  /**
   * Intercept when
   *  AuthenticationException occurs
   *
   *@param HttpServletRequest request
   *@param HttpServletResponse response
   *@param AuthenticationException authException
   */
  @Override
  public void commence(
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException authException
  )
    throws IOException {
    log.error(
      "Responding with unauthorized error. Message - {}",
      authException.getMessage()
    );
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }
}
