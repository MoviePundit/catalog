package com.aircraft.catalog.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aircraft.catalog.exceptions.ProductNotFoundException;
import com.aircraft.catalog.model.ProductCatelog;
import com.aircraft.catalog.repository.ProductCatelogRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ProductCatelogService {

	@Autowired
	private ProductCatelogRepository productCatelogRepository;

	public List<ProductCatelog> retrieveAllProducts() {
		log.debug("retrieve records from db");
		List<ProductCatelog> productCatelogList = productCatelogRepository.findAll();
		if (Objects.isNull(productCatelogList) || productCatelogList.isEmpty()) {
			log.error("no records found in db");
			throw new ProductNotFoundException("no products found", HttpStatus.NOT_FOUND);
		}
		return productCatelogList;
	}

	public ProductCatelog retrieveProductById(Long productId) {
		Optional<ProductCatelog> product = productCatelogRepository.findById(productId);
		if (Objects.isNull(product) || product.isEmpty()) {
			throw new ProductNotFoundException("product not found for given Id", HttpStatus.NOT_FOUND);
		}
		return product.get();
	}

	public List<ProductCatelog> retrieveAllProductsByCategory(String category) {

		Optional<List<ProductCatelog>> productCatelogListByCategory = productCatelogRepository
				.findAllProductCatelogByProductCategory(category);

		if (Objects.isNull(productCatelogListByCategory) || productCatelogListByCategory.isEmpty()
				|| productCatelogListByCategory.get().isEmpty()) {

			throw new ProductNotFoundException("products not found for given category", HttpStatus.NOT_FOUND);
		}
		return productCatelogListByCategory.get();
	}

	public ProductCatelog createProduct(ProductCatelog product) {
		return productCatelogRepository.save(product);
	}

	public ProductCatelog updateProduct(Long productId, ProductCatelog product) {
		Optional<ProductCatelog> productCatelog = productCatelogRepository.findById(productId);

		if (productCatelog.isEmpty()) {
			throw new ProductNotFoundException("no product found for given Id", HttpStatus.NOT_FOUND);
		}
		ProductCatelog productToBeSaved = productCatelog.get();
		productToBeSaved.setProductId(productId);
		if (Objects.nonNull(product.getProductCategory())) {
			productToBeSaved.setProductCategory(product.getProductCategory());
		}

		if (Objects.nonNull(product.getProductDescription())) {
			productToBeSaved.setProductDescription(product.getProductDescription());
		}

		if (Objects.nonNull(product.getProductName())) {
			productToBeSaved.setProductName(product.getProductName());
		}

		if (Objects.nonNull(product.getUnits())) {
			productToBeSaved.setUnits(product.getUnits());
		}

		return productCatelogRepository.save(productToBeSaved);
	}

}
