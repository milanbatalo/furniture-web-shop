package webshop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import webshop.model.Category;
import webshop.service.CategoryService;
import webshop.support.CategoryToCategoryDTO;
import webshop.web.dto.CategoryDTO;

@RestController
@RequestMapping("/api/categories")
public class ApiCategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryToCategoryDTO toCategoryDTO;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoryDTO>> get() {
		return new ResponseEntity<>(toCategoryDTO.convertAll(categoryService.findAll()), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<CategoryDTO> get(@PathVariable Long id) {
		Category category = categoryService.findOne(id);

		if (category == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(toCategoryDTO.convert(category), HttpStatus.OK);
	}

}
