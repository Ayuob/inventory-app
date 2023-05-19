package ly.qubit.inventory.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Products {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "sku", nullable = false, length = 7)
    private String sku;
    @Basic
    @Column(name = "product_name", nullable = false, length = 50)
    private String productName;
    @Basic
    @Column(name = "size", nullable = true)
    private Integer size;
    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Categories categoriesByCategoryId;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return Objects.equals(sku, products.sku) && Objects.equals(productName, products.productName) && Objects.equals(categoriesByCategoryId, products.categoriesByCategoryId) && Objects.equals(size, products.size) && Objects.equals(price, products.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, productName, categoriesByCategoryId, size, price);
    }

    public Categories getCategoriesByCategoryId() {
        return categoriesByCategoryId;
    }

    public void setCategoriesByCategoryId(Categories categoriesByCategoryId) {
        this.categoriesByCategoryId = categoriesByCategoryId;
    }
}
