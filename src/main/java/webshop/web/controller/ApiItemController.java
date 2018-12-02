package webshop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webshop.model.Item;
import webshop.model.Purchase;
import webshop.service.ItemService;
import webshop.service.PurchaseService;
import webshop.support.ItemDTOToItem;
import webshop.support.ItemToItemDTO;
import webshop.support.PurchaseToPurchaseDTO;
import webshop.web.dto.ItemDTO;
import webshop.web.dto.PurchaseDTO;

@RestController
@RequestMapping("/api/items")
public class ApiItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemToItemDTO toItemDTO;
	
	@Autowired
	private ItemDTOToItem toItem;
	
	@Autowired
	private PurchaseToPurchaseDTO toPurchaseDTO;

	@Autowired
	private PurchaseService purchaseService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ItemDTO>> get(
			@RequestParam(required = false) String itemName,
			@RequestParam(required = false) Long categoryId, 
			@RequestParam(required = false) Float maxPrice,
			@RequestParam(defaultValue = "0") int pageNum) {

		Page<Item> items;

		if (itemName != null || categoryId != null || maxPrice != null) {
			items = itemService.search(itemName, categoryId, maxPrice, pageNum);
		} else {
			items = itemService.findAll(pageNum);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("totalPages", Integer.toString(items.getTotalPages()));
		
		return new ResponseEntity<>(toItemDTO.convert(items.getContent()),headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<ItemDTO> get(@PathVariable Long id) {
		Item item = itemService.findOne(id);
		
		if (item == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(toItemDTO.convert(item),HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<ItemDTO> add(@RequestBody ItemDTO newItem) {

		Item item = toItem.convert(newItem);  
		itemService.save(item);

		return new ResponseEntity<>(toItemDTO.convert(item),HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
	public ResponseEntity<ItemDTO> edit(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {

		if (!id.equals(itemDTO.getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Item item = toItem.convert(itemDTO);
		itemService.save(item);

		return new ResponseEntity<>(toItemDTO.convert(item),HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<ItemDTO> delete(@PathVariable Long id) {
		itemService.remove(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/purchase", consumes = "application/json")
	public ResponseEntity<PurchaseDTO> purchaseItem(@RequestBody PurchaseDTO purchaseDto) {
		Purchase purchase = purchaseService.purchase(itemService.findOne(purchaseDto.getItemId()), purchaseDto.getQuantity());
		
		if (purchase == null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		purchaseService.save(purchase);
		
		return new ResponseEntity<>(toPurchaseDTO.convert(purchase), HttpStatus.OK);
	}
}
