package webshop.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import webshop.model.Item;
import webshop.web.dto.ItemDTO;

@Component
public class ItemToItemDTO implements Converter<Item, ItemDTO> {

	@Override
	public ItemDTO convert(Item item) {
		ItemDTO itemDTO = new ItemDTO();

		itemDTO.setId(item.getId());
		itemDTO.setItemName(item.getItemName());
		itemDTO.setQuantity(item.getQuantity());
		itemDTO.setPrice(item.getPrice());
		itemDTO.setCategoryId(item.getCategory().getId());
		itemDTO.setCategoryName(item.getCategory().getCategoryName());

		return itemDTO;
	}

	public List<ItemDTO> convert(List<Item> items) {
		List<ItemDTO> itemDTOs = new ArrayList<>();
		for (Item item : items) {
			itemDTOs.add(convert(item));
		}
		return itemDTOs;
	}

}
