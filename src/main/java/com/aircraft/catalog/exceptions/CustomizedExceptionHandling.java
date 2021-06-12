package com.aircraft.catalog.exceptions;

import com.aircraft.catalog.model.ExceptionResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {

  /**
   * Exception handler for ProductNotFoundException
   * @param exception
   * @param webRequest
   * @return
   */
  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<Object> handleExceptions(
    ProductNotFoundException exception,
    WebRequest webRequest
  ) {
    ExceptionResponse response = ExceptionResponse
      .builder()
      .dateTime(LocalDateTime.now())
      .message(exception.getErrorMessage())
      .build();

    return ResponseEntity.status(exception.getErrorCode()).body(response);
  }

  /**
   * Exception handler for InvalidCredentialsException
   * @param exception
   * @param webRequest
   * @return ResponseEntity
   */
  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<Object> handleExceptions(
    InvalidCredentialsException exception,
    WebRequest webRequest
  ) {
    ExceptionResponse response = ExceptionResponse
      .builder()
      .dateTime(LocalDateTime.now())
      .message(exception.getErrorMessage())
      .build();

    return ResponseEntity.status(exception.getErrorCode()).body(response);
  }

  /**
   * Exception handler for InvalidCredentialsException
   * @param exception
   * @param webRequest
   * @return ResponseEntity
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    LocalDateTime now = LocalDateTime.now();
    List<ExceptionResponse> validationList = ex
      .getBindingResult()
      .getFieldErrors()
      .stream()
      .map(
        fieldError ->
          ExceptionResponse
            .builder()
            .field(fieldError.getField())
            .message(fieldError.getDefaultMessage())
            .dateTime(now)
            .build()
      )
      .collect(Collectors.toList());
    log.error("List of errors: {}", validationList);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationList);
  }
}
