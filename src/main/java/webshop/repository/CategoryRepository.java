package webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webshop.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
