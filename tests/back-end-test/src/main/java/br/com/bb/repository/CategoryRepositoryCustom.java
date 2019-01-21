package br.com.bb.repository;

import br.com.bb.entity.Category;
import br.com.bb.exception.ResourceNotFoundException;

public interface CategoryRepositoryCustom {

	Category findCategoryMostCharacters(String characterToFind) throws ResourceNotFoundException;
}
