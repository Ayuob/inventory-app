package ly.qubit.inventory.model.maper;

import ly.qubit.inventory.model.DTOs.ProductDTO;
import ly.qubit.inventory.model.Product;
import ly.qubit.inventory.model.SKUGenerator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductMapper {

    public Product dtoToEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        Product product = new Product();
        product.setSku(SKUGenerator.generateSKU(productDTO.getName(), productDTO.getSize()));
        product.setProductName(productDTO.getName());
        product.setSize(productDTO.getSize());
        product.setPrice(BigDecimal.valueOf(productDTO.getPrice()));

        return product;
    }

    public ProductDTO entityToDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getProductName());
        productDTO.setSize(product.getSize());
        productDTO.setPrice(product.getPrice().doubleValue());

        return productDTO;
    }
}
