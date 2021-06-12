package com.aircraft.catalog.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Product Not Found Exception class
 * @author Ashutosh Tomar
 */
@Getter
@Setter
@AllArgsConstructor
public class ProductNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1;
  private final String errorMessage;
  private final HttpStatus errorCode;
}
