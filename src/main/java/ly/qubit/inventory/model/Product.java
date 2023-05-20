package ly.qubit.inventory.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "products", schema = "inventory", catalog = "inventory_db")
public class Product {
    @Id
    @Column(name = "sku", nullable = false, length = 7)
    @JsonProperty("sku")
    private String sku;
    @Basic
    @Column(name = "product_name", nullable = false, length = 50)
    @JsonProperty("product_name")
    private String productName;

    @Basic
    @Column(name = "size", nullable = true)
    @JsonProperty("size")
    private Integer size;
    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    @JsonProperty("price")
    private BigDecimal price;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Categories category;


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
        Product product = (Product) o;
        return Objects.equals(sku, product.sku) && Objects.equals(productName, product.productName) && Objects.equals(category, product.category) && Objects.equals(size, product.size) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, productName, category, size, price);
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }
}
