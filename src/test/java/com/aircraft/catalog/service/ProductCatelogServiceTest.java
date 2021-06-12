package com.aircraft.catalog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.aircraft.catalog.exceptions.ProductNotFoundException;
import com.aircraft.catalog.model.ProductCatelog;
import com.aircraft.catalog.repository.ProductCatelogRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductCatelogServiceTest {
  @InjectMocks
  private ProductCatelogService service;

  @Mock
  private ProductCatelogRepository repository;

  final List<ProductCatelog> mockData = List.of(
    ProductCatelog
      .builder()
      .productId(1L)
      .productCategory("Consumer")
      .productName("A380")
      .build()
  );

  @DisplayName(value = "Testing if able to Retrieve All Products")
  @Test
  public void testIfAbleToRetrieveAllProducts() {
    when(repository.findAll()).thenReturn(mockData);
    List<ProductCatelog> products = service.retrieveAllProducts();
    assertFalse((products.isEmpty()));
    assertEquals(1, products.size());
  }

  @DisplayName(value = "Testing if able to Retrieve Product By Id")
  @Test
  public void testIfAbleToRetrieveProductById() {
    when(repository.findById(1L)).thenReturn(Optional.of(mockData.get(0)));
    ProductCatelog products = service.retrieveProductById(1L);
    assertTrue((Objects.nonNull(products)));
    assertEquals("A380", products.getProductName());
  }

  @DisplayName(value = "Testing if able to Retrieve Product By Category")
  @Test
  public void testIfAbleToRetrieveProductsByCategory() {
    when(
        repository.findAllProductCatelogByProductCategoryContainingIgnoreCase("Consumer")
      )
      .thenReturn(Optional.of(mockData));

    List<ProductCatelog> products = service.retrieveAllProductsByCategory("Consumer");

    assertTrue((Objects.nonNull(products)));
    assertFalse((products.isEmpty()));
    assertEquals("A380", products.get(0).getProductName());
  }

  @DisplayName(value = "Testing if able to Create product")
  @Test
  public void testIfAbleToCreateProduct() {
    when(repository.save(mockData.get(0))).thenReturn(mockData.get(0));

    service.createProduct(mockData.get(0));

    verify(repository, times(1)).save(mockData.get(0));
  }

  @DisplayName(value = "Testing if able to Update product")
  @Test
  public void testIfAbleToUpdateProduct() {
    ProductCatelog product = ProductCatelog
      .builder()
      .productCategory("private")
      .productDescription("description")
      .productName("A320")
      .productId(5L)
      .build();

    when(repository.findById(5L)).thenReturn(Optional.of(product));
    when(repository.save(product)).thenReturn(product);

    service.updateProduct(5L, product);

    verify(repository, times(1)).findById(5L);
    verify(repository, times(1)).save(product);
  }

  @DisplayName(value = "Testing if able to throw exception on Update product")
  @Test
  public void testIfAbleToThrowExceptionOnUpdateProduct() {
    ProductCatelog product = ProductCatelog
      .builder()
      .productCategory("private")
      .productDescription("description")
      .productName("A320")
      .productId(5L)
      .build();

    assertThrows(
      ProductNotFoundException.class,
      () -> service.updateProduct(5L, product)
    );
  }

  @DisplayName(
    value = "Testing if able to throw exception on Retrieve Product By Category"
  )
  @Test
  public void testIfAbleToThrowExceptionOnRetrieveProductByCategory() {
    assertThrows(
      ProductNotFoundException.class,
      () -> service.retrieveAllProductsByCategory("")
    );
  }

  @DisplayName(value = "Testing if able to throw exception on Retrieve Product By Id")
  @Test
  public void testIfAbleToThrowExceptionOnRetrieveProductById() {
    assertThrows(ProductNotFoundException.class, () -> service.retrieveProductById(1L));
  }
}
