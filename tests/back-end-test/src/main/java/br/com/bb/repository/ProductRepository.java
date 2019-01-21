package br.com.bb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.bb.entity.Product;
import br.com.bb.exception.ResourceNotFoundException;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("select p from Product p where p.categoryId = ?1")
	List<Product> findByCategory(Integer categoryId) throws ResourceNotFoundException ;

}
