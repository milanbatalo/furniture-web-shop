package webshop.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import webshop.model.Item;
import webshop.service.CategoryService;
import webshop.service.ItemService;
import webshop.web.dto.ItemDTO;

@Component
public class ItemDTOToItem implements Converter<ItemDTO, Item> {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ItemService itemService;

	@Override
	public Item convert(ItemDTO itemDTO) {
		Item item;

		if (itemDTO.getId() == null) {
			item = new Item();
		} else {
			item = itemService.findOne(itemDTO.getId());
		}

		item.setCategory(categoryService.findOne(itemDTO.getCategoryId()));
		item.setItemName(itemDTO.getItemName());
		item.setPrice(itemDTO.getPrice());
		item.setQuantity(itemDTO.getQuantity());

		return item;
	}

}
