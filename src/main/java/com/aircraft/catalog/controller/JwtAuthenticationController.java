package com.aircraft.catalog.controller;

import com.aircraft.catalog.model.JwtRequest;
import com.aircraft.catalog.model.JwtResponse;
import com.aircraft.catalog.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
  @Autowired
  private AuthenticationService service;

  @PostMapping(value = "/authenticate")
  @Operation(description = "authenticate and provide token", method = "POST")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Authenticate user and provide access token",
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = JwtResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      )
    }
  )
  public ResponseEntity<JwtResponse> generateAuthenticationToken(
    @Valid @RequestBody JwtRequest authenticationRequest
  ) {
    return ResponseEntity.ok(service.authenticateRequest(authenticationRequest));
  }
}
