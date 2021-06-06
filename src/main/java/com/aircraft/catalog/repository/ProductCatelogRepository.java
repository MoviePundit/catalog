package com.aircraft.catalog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aircraft.catalog.model.ProductCatelog;

@Repository
public interface ProductCatelogRepository extends JpaRepository<ProductCatelog, Long>{

	 Optional<List<ProductCatelog>> findAllProductCatelogByProductCategory(String category);
}
