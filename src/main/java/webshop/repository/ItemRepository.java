package webshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webshop.model.Item;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	Page<Item> findByCategoryId(Long categoryId, Pageable pageRequest);
	
	@Query("SELECT i FROM Item i WHERE "
		+ "(:itemName IS NULL or i.itemName like :itemName ) AND "
		+ "(:categoryId IS NULL OR i.category.id like :categoryId  ) AND "
		+ "(:maxPrice IS NULL OR i.price <= :maxPrice)"
		)
	Page<Item> search(
		@Param("itemName") String itemName, 
		@Param("categoryId") Long categoryId, 
		@Param("maxPrice") Float maxPrice,
		Pageable pageRequest);
}
