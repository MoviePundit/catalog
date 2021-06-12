package com.aircraft.catalog.repository;

import com.aircraft.catalog.model.ProductCatelog;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCatelogRepository extends JpaRepository<ProductCatelog, Long> {
  /**
   * Repository method to fetch all products with parameter
   * @param category
   * @return Optional<List<ProductCatelog>>
   */
  Optional<List<ProductCatelog>> findAllProductCatelogByProductCategoryContainingIgnoreCase(
    String category
  );
}
