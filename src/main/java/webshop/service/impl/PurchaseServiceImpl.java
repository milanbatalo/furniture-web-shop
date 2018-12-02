package webshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webshop.model.Item;
import webshop.model.Purchase;
import webshop.repository.PurchaseRepository;
import webshop.service.ItemService;
import webshop.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseRepository purchaseRepository;

	@Autowired
	private ItemService itemService;

	@Override
	public Purchase purchase(Item item, Integer quantity) {
		if (item != null && item.getQuantity() >= quantity) {
			Purchase purchase = new Purchase();
			purchase.setQuantity(quantity);
			purchase.setItem(item);
			item.setQuantity(item.getQuantity() - quantity);
			itemService.save(item);
			return purchase;
		}
		return null;
	}

	@Override
	public void save(Purchase purchase) {
		purchaseRepository.save(purchase);
	}

}
