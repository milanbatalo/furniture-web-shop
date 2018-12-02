package webshop.service;

import java.util.List;

import webshop.model.Category;

public interface CategoryService {

	// TODO add javadoc
	List<Category> findAll();

	Category findOne(Long id);

	void save(Category category);
}
