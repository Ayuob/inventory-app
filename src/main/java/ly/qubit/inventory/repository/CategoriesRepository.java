package ly.qubit.inventory.repository;

import ly.qubit.inventory.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    Optional<Categories> findByCategoryId(Integer categoryId);
}