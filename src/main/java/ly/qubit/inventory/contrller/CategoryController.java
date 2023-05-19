package ly.qubit.inventory.contrller;

import jdk.jfr.Category;
import ly.qubit.inventory.model.Categories;
import ly.qubit.inventory.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {


    CategoriesRepository categoriesRepository;

    public CategoryController() {
    }

    @Autowired
    public CategoryController(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    // GET /api/categories: Retrieves a list of all categories.
    @GetMapping ("/api/categories")
    public List<Categories> getCategories() {
        return categoriesRepository.findAll();
    }
    // POST /api/categories: Creates a new category. The category data should be included in the request body.
    @PostMapping("/api/categories")
    public Categories createCategory(Categories category) {
        return categoriesRepository.save(category);
    }

    // GET /api/categories/{id}: Retrieves a specific category by ID.
    @GetMapping("/api/categories/{id}")
    public Categories getCategoryById(@PathVariable int id) {
        return categoriesRepository.findById(id).get();
    }


    // PUT /api/categories/{id}: Updates a specific category by ID. The updated category data should be included in the request body.
    @PutMapping("/api/categories/{id}")
    public Categories updateCategoryById(@PathVariable int id,@RequestBody Categories category) {
        Categories categoryToUpdate = categoriesRepository.findById(id).get();
        categoryToUpdate.setCategoryDescription(category.getCategoryDescription());
        return categoriesRepository.save(categoryToUpdate);
    }

    // DELETE /api/categories/{id}: Deletes a specific category by ID.
    @DeleteMapping("/api/categories/{id}")
    public void deleteCategoryById(@PathVariable int id) {
        categoriesRepository.deleteById(id);
    }

}
