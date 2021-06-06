package com.aircraft.catalog.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aircraft.catalog.model.ProductCatelog;
import com.aircraft.catalog.service.ProductCatelogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin
public class ProductCatelogController {

	public final static String APPLICATION_JSON = "application/json";

	@Autowired
	private ProductCatelogService productCatelogService;

	@GetMapping
	@Operation(description = "retrieve all products", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ProductCatelog.class))) })
	public ResponseEntity<List<ProductCatelog>> getAllProducts() {
		return ResponseEntity.ok(productCatelogService.retrieveAllProducts());
	}

	@GetMapping("/{id}")
	@Operation(description = "retrieve all products by id", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ProductCatelog.class))),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<ProductCatelog> getAllProductById(
			@Parameter(in = ParameterIn.PATH, required = true, name = "product id", description = "unique product identifier", example = "10") @PathVariable(value = "id") Long productId) {
		return ResponseEntity.ok(productCatelogService.retrieveProductById(productId));
	}

	@GetMapping("/category/{category}")
	@Operation(description = "retrieve all products by category", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ProductCatelog.class))),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<List<ProductCatelog>> getAllProductsByCategory(
			@Parameter(in = ParameterIn.PATH, required = true, name = "category", description = "product category identifier", example = "Commercial") @PathVariable(value = "category") String category) {
		return ResponseEntity.ok(productCatelogService.retrieveAllProductsByCategory(category));
	}

	@PostMapping("/create")
	@Operation(description = "create product", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ProductCatelog.class))) })
	public ResponseEntity<ProductCatelog> createProduct(@Valid @RequestBody ProductCatelog product) {

		ProductCatelog createdProduct = productCatelogService.createProduct(product);

		return ResponseEntity.created(URI.create("/product/" + createdProduct.getProductId())).body(createdProduct);
	}

	@PutMapping("/update/{id}")
	@Operation(description = "update product", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ProductCatelog.class))),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<ProductCatelog> updateProductCatelog(
			@Parameter(in = ParameterIn.PATH, required = true, name = "product id", description = "unique product identifier", example = "10") @PathVariable(value = "id") Long productId,
			@RequestBody ProductCatelog product) {

		ProductCatelog updateProduct = productCatelogService.updateProduct(productId, product);
		return ResponseEntity.ok(updateProduct);

	}

}
