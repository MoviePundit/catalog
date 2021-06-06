package com.aircraft.catalog.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductNotFoundException extends RuntimeException {
	
	  private static final long serialVersionUID = 1;
	  private final String errorMessage;
	  private final HttpStatus errorCode;
}