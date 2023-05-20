package ly.qubit.inventory.model.maper;

import ly.qubit.inventory.model.Categories;
import ly.qubit.inventory.model.Product;
import ly.qubit.inventory.model.ProductDto;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getSku(),
                product.getProductName(),
                product.getSize(),
                product.getPrice(),
                product.getCategory().getCategoryId()
        );
    }

    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setSku(productDto.sku());
        product.setProductName(productDto.productName());
        product.setSize(productDto.size());
        product.setPrice(productDto.price());

        Categories categories = new Categories();
        categories.setCategoryId(productDto.categoryCId());
        product.setCategory(categories);

        return product;
    }
}
