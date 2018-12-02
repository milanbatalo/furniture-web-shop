package webshop.service;

import webshop.model.Item;
import webshop.model.Purchase;

public interface PurchaseService {

	Purchase purchase(Item item, Integer quantity);

	void save(Purchase purchase);
}
