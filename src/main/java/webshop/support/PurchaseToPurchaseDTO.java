package webshop.support;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import webshop.model.Purchase;
import webshop.web.dto.PurchaseDTO;

@Component
public class PurchaseToPurchaseDTO implements Converter<Purchase, PurchaseDTO> {

	@Override
	public PurchaseDTO convert(Purchase purchase) {
		PurchaseDTO purchaseDTO = new PurchaseDTO();
		
		purchaseDTO.setId(purchase.getId());
		purchaseDTO.setItemId(purchase.getItem().getId());
		purchaseDTO.setItemName(purchase.getItem().getItemName());
		purchaseDTO.setQuantity(purchase.getQuantity());
		purchaseDTO.setTotalPrice(purchase.getItem().getPrice() * purchase.getQuantity());
		
		return purchaseDTO;
	}

}
