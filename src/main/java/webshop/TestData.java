package webshop;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import webshop.model.Category;
import webshop.model.Item;
import webshop.service.CategoryService;
import webshop.service.ItemService;

@Component
public class TestData {

	@Autowired
	private ItemService itemService;

	@Autowired
	private CategoryService categoryService;

	@PostConstruct
	public void init() {

		Category cat1 = new Category();
		cat1.setCategoryName("Tables");
		categoryService.save(cat1);

		Category cat2 = new Category();
		cat2.setCategoryName("Chairs");
		categoryService.save(cat2);

		Category cat3 = new Category();
		cat3.setCategoryName("Sofas");
		categoryService.save(cat3);

		Item item1 = new Item();
		item1.setItemName("Steady Tavolino Large");
		item1.setPrice(338.08f);
		item1.setQuantity(8);
		item1.setCategory(cat1);
		itemService.save(item1);

		Item item2 = new Item();
		item2.setItemName("Pine Table Large");
		item2.setPrice(731.65f);
		item2.setQuantity(4);
		item2.setCategory(cat1);
		itemService.save(item2);

		Item item3 = new Item();
		item3.setItemName("Nebula Nine Sofa");
		item3.setPrice(4299.10f);
		item3.setQuantity(2);
		item3.setCategory(cat3);
		itemService.save(item3);

		Item item4 = new Item();
		item4.setItemName("Mingle Sofa Sheepskin");
		item4.setPrice(2521.94f);
		item4.setQuantity(3);
		item4.setCategory(cat3);
		itemService.save(item4);

		Item item5 = new Item();
		item5.setItemName("Synnes Chair Full Upholstered");
		item5.setPrice(655.92f);
		item5.setQuantity(10);
		item5.setCategory(cat2);
		itemService.save(item5);

		Item item6 = new Item();
		item6.setItemName("Harbour Chair Shell/Steel Base");
		item6.setPrice(280f);
		item6.setQuantity(15);
		item6.setCategory(cat2);
		itemService.save(item6);
	}
}
