package br.com.bb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.bb.entity.Product;
import br.com.bb.exception.ResourceNotFoundException;
import br.com.bb.repository.ProductRepository;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/product/listAll")
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Integer id)
			throws ResourceNotFoundException {
		Product product = productRepository.findOne(id);
		if (product == null) {
			throw new ResourceNotFoundException("Product not found for this id " + id);
		}
		return ResponseEntity.ok().body(product);
	}
	
	@GetMapping("/product/listByCategory/{categoryId}")
	public List<Product> getProductByCategoryId(@PathVariable(value = "categoryId") Integer categoryId)
			throws ResourceNotFoundException {
		List<Product> products = productRepository.findByCategory(categoryId);
		if (products == null) {
			throw new ResourceNotFoundException("Products not found for this category id " + categoryId);
		}
		return products;
	}

	@PostMapping("/product/save")
	public Product createProduct(@Valid @RequestBody Product product) {
		return productRepository.save(product);
	}

	@PutMapping("/product/update/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Integer id,
			@Valid @RequestBody Product newProduct) throws ResourceNotFoundException {
		Product product = productRepository.findOne(id);
		if (product == null) {
			throw new ResourceNotFoundException("Product not found for this id " + id);
		}
		product.setName(newProduct.getName());
		product.setCategoryId(newProduct.getCategoryId());
		Product updatedProduct = productRepository.save(product);
		return ResponseEntity.ok(updatedProduct);
	}

	@DeleteMapping("/product/delete/{id}")
	public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
		Product product = productRepository.findOne(id);
		if (product == null) {
			throw new ResourceNotFoundException("Product not found for this id " + id);
		}
		productRepository.delete(product);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
