package ly.qubit.inventory.repository;

import ly.qubit.inventory.model.Categories;
import ly.qubit.inventory.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProductsRepository extends JpaRepository<Products, String> {
     List<Products> findByCategory_CategoryId(Integer categoriesByCategoryId);

}