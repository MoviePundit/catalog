package com.aircraft.catalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_catelog")
public class ProductCatelog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Long productId;

  @NotBlank
  @Column(name = "product_category")
  private String productCategory;

  @NotBlank
  @Column(name = "product_name")
  private String productName;

  @NotBlank
  @Column(name = "product_description")
  private String productDescription;

  @Positive
  @Column(name = "units")
  @NotNull
  private Integer units;
}
