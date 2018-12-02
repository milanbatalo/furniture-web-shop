package webshop.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import webshop.model.Category;
import webshop.web.dto.CategoryDTO;

@Component
public class CategoryToCategoryDTO implements Converter<Category, CategoryDTO> {

	@Override
	public CategoryDTO convert(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setCategoryName(category.getCategoryName());
		return categoryDTO;
	}

	public List<CategoryDTO> convertAll(List<Category> categories) {
		List<CategoryDTO> categoryDTOs = new ArrayList<>();
		for (Category category : categories) {
			categoryDTOs.add(convert(category));
		}
		return categoryDTOs;
	}

}
