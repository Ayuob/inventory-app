package ly.qubit.inventory.repository;

import ly.qubit.inventory.model.Categories;
import ly.qubit.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, String> {
     @Transactional
     @Modifying
     @Query("""
             update Product p set p.price = ?1, p.productName = ?2, p.size = ?3, p.category = ?4
             where upper(p.sku) = upper(?5)""")
     int updatePriceAndProductNameAndSizeAndCategoryBySkuIgnoreCase(BigDecimal price, String productName, Integer size, Categories category, @NonNull String sku);
     List<Product> findByCategory_CategoryId(Integer categoriesByCategoryId);

     List<Product> findBySku(String sku);

}