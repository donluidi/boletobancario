package br.com.bb.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.bb.entity.Category;
import br.com.bb.exception.ResourceNotFoundException;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Category findCategoryMostCharacters(String characterToFind) throws ResourceNotFoundException {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> category = query.from(Category.class);
 
        List<Predicate> predicates = new ArrayList<>();
               query.select(category)
            .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
 
        return em.createQuery(query)
            .getSingleResult();
	}

}
