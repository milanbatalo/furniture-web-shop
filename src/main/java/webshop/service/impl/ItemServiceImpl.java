package webshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import webshop.model.Item;
import webshop.repository.ItemRepository;
import webshop.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Override
	public Page<Item> findAll(int pageNum) {
		return itemRepository.findAll(new PageRequest(pageNum, 5));
	}

	@Override
	public Item findOne(Long id) {
		return itemRepository.findOne(id);
	}

	@Override
	public void save(Item item) {
		itemRepository.save(item);
	}

	@Override
	public void remove(Long id) {
		itemRepository.delete(id);
	}

	@Override
	public Page<Item> findByCategoryId(int pageNum, Long categoryId) {
		return itemRepository.findByCategoryId(categoryId, new PageRequest(pageNum, 5));
	}

	@Override
	public Page<Item> search(String itemName, Long categoryId, Float maxPrice, int page) {
		if (itemName != null) {
			itemName = "%" + itemName + "%";
		}
		return itemRepository.search(itemName, categoryId, maxPrice, new PageRequest(page, 5));
	}

}
