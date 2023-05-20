package ly.qubit.inventory.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Categories {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;
    @Basic
    @Column(name = "category_description", nullable = true, length = 50)
    private String categoryDescription;
    @Basic
    @Column(name = "product_line", nullable = true, length = 25)
    private String productLine;
    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private Collection<Product> productByCategoryId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categories that = (Categories) o;
        return Objects.equals(categoryId, that.categoryId) && Objects.equals(categoryDescription, that.categoryDescription) && Objects.equals(productLine, that.productLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryDescription, productLine);
    }

    public Collection<Product> getProductsByCategoryId() {
        return productByCategoryId;
    }

    public void setProductsByCategoryId(Collection<Product> productByCategoryId) {
        this.productByCategoryId = productByCategoryId;
    }
}
