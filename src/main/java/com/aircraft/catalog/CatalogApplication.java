package com.aircraft.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Product Catalog Application
 *
 * @author Ashutosh, Tomar
 */
@SpringBootApplication
@CrossOrigin
public class CatalogApplication {

  public static void main(String[] args) {
    SpringApplication.run(CatalogApplication.class, args);
  }
}
