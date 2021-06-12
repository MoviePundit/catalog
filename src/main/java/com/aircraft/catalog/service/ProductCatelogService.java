package com.aircraft.catalog.service;

import com.aircraft.catalog.exceptions.ProductNotFoundException;
import com.aircraft.catalog.model.ProductCatelog;
import com.aircraft.catalog.repository.ProductCatelogRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Product catelog service
 * @author Ashutosh Tomar
 *
 */
@Log4j2
@Service
public class ProductCatelogService {
  private static final String NO_PRODUCT_FOUND_FOR_GIVEN_ID =
    "no product found for given Id";
  private static final String PRODUCTS_NOT_FOUND_FOR_GIVEN_CATEGORY =
    "products not found for given category";
  private static final String NO_PRODUCTS_FOUND = "no products found";

  @Autowired
  private ProductCatelogRepository productCatelogRepository;

  /**
   * Retrive all products
   * @return List<ProductCatelog>
   */
  public List<ProductCatelog> retrieveAllProducts() {
    log.debug("retrieve records from db");
    List<ProductCatelog> productCatelogList = productCatelogRepository.findAll();
    if (Objects.isNull(productCatelogList) || productCatelogList.isEmpty()) {
      log.error("no records found in db");
      throw new ProductNotFoundException(NO_PRODUCTS_FOUND, HttpStatus.NOT_FOUND);
    }
    return productCatelogList;
  }

  /**
   * Retrive product by id
   * @param productId
   * @return ProductCatelog
   */
  public ProductCatelog retrieveProductById(Long productId) {
    Optional<ProductCatelog> product = productCatelogRepository.findById(productId);
    if (Objects.isNull(product) || product.isEmpty()) {
      log.error("no records found in db");
      throw new ProductNotFoundException(
        NO_PRODUCT_FOUND_FOR_GIVEN_ID,
        HttpStatus.NOT_FOUND
      );
    }
    return product.get();
  }

  /**
   * Retrive product by category
   * @param category
   * @return List<ProductCatelog>
   */
  public List<ProductCatelog> retrieveAllProductsByCategory(String category) {
    Optional<List<ProductCatelog>> productCatelogListByCategory = productCatelogRepository.findAllProductCatelogByProductCategoryContainingIgnoreCase(
      category
    );

    if (
      Objects.isNull(productCatelogListByCategory) ||
      productCatelogListByCategory.isEmpty() ||
      productCatelogListByCategory.get().isEmpty()
    ) {
      throw new ProductNotFoundException(
        PRODUCTS_NOT_FOUND_FOR_GIVEN_CATEGORY,
        HttpStatus.NOT_FOUND
      );
    }
    return productCatelogListByCategory.get();
  }

  /**
   * Create a new product
   * @param product
   * @return ProductCatelog
   */
  public ProductCatelog createProduct(ProductCatelog product) {
    return productCatelogRepository.save(product);
  }

  /**
   * update existing product
   * @param productId
   * @param product
   * @return ProductCatelog
   */
  public ProductCatelog updateProduct(Long productId, ProductCatelog product) {
    Optional<ProductCatelog> productCatelog = productCatelogRepository.findById(
      productId
    );

    if (productCatelog.isEmpty()) {
      throw new ProductNotFoundException(
        NO_PRODUCT_FOUND_FOR_GIVEN_ID,
        HttpStatus.NOT_FOUND
      );
    }
    ProductCatelog productToBeSaved = productCatelog.get();
    productToBeSaved.setProductId(productId);
    if (StringUtils.isNotBlank(product.getProductCategory())) {
      productToBeSaved.setProductCategory(product.getProductCategory());
    }

    if (StringUtils.isNotBlank(product.getProductDescription())) {
      productToBeSaved.setProductDescription(product.getProductDescription());
    }

    if (StringUtils.isNotBlank(product.getProductName())) {
      productToBeSaved.setProductName(product.getProductName());
    }

    if (Objects.nonNull(product.getUnits()) && product.getUnits() > 0) {
      productToBeSaved.setUnits(product.getUnits());
    }

    return productCatelogRepository.save(productToBeSaved);
  }
}
