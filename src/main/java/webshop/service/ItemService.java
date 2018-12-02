package webshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import webshop.model.Item;

public interface ItemService {

	Page<Item> findAll(int pageNum);

	Item findOne(Long id);

	void save(Item item);

	void remove(Long id);

	Page<Item> findByCategoryId(int pageNum, Long categoryId);

	Page<Item> search(
			@Param("itemName") String itemName,
			@Param("categoryId") Long categoryId,
			@Param("maxPrice") Float maxPrice, int page);
}
