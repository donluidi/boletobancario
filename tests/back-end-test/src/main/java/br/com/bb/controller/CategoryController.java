package br.com.bb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.bb.entity.Category;
import br.com.bb.exception.ResourceNotFoundException;
import br.com.bb.repository.CategoryRepository;

@RestController
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping("/category/listAll")
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") Integer id)
			throws ResourceNotFoundException {
		Category category = categoryRepository.findOne(id);
		if (category == null) {
			throw new ResourceNotFoundException("Category not found for this id " + id);
		}
		return ResponseEntity.ok().body(category);
	}
	
	@GetMapping("/category/getByCharacter/{name}")
	public ResponseEntity<Category> getByCharacter(@PathVariable(value = "name") String name)
			throws ResourceNotFoundException {
		List<Category> listOfcategory = categoryRepository.findByCharacter(name);
		if (listOfcategory == null || listOfcategory.isEmpty()) {
			throw new ResourceNotFoundException("Category not found for this name " + name);
		}
		Category greatCategory = null;
		int max = 0;
		for (Category category : listOfcategory) {
			int count = StringUtils.countOccurrencesOf(category.getName(), name);
			System.out.println("count occurences of: " + name + " = " + count + " of category " + category.getName());
			if (count > max) {
				greatCategory = category;
				max = count;
			}
		}
		return ResponseEntity.ok().body(greatCategory);
	}

	@PostMapping("/category/save")
	public Category createCategory(@Valid @RequestBody Category category) {
		return categoryRepository.save(category);
	}

	@PutMapping("/category/update/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Integer id,
			@Valid @RequestBody Category newCategory) throws ResourceNotFoundException {
		Category category = categoryRepository.findOne(id);
		if (category == null) {
			throw new ResourceNotFoundException("Category not found for this id " + id);
		}
		category.setName(newCategory.getName());
		Category updatedCategory = categoryRepository.save(category);
		return ResponseEntity.ok(updatedCategory);
	}

	@DeleteMapping("/category/delete/{id}")
	public Map<String, Boolean> deleteCategory(@PathVariable(value = "id") Integer id)
			throws ResourceNotFoundException {
		Category category = categoryRepository.findOne(id);
		if (category == null) {
			throw new ResourceNotFoundException("Category not found for this id " + id);
		}
		categoryRepository.delete(category);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
