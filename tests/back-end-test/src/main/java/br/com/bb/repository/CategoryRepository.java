package br.com.bb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.bb.entity.Category;
import br.com.bb.exception.ResourceNotFoundException;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryRepositoryCustom {

	@Query("select c from Category c where c.name like %?1%")
	List<Category> findByCharacter(String name) throws ResourceNotFoundException ;
}
