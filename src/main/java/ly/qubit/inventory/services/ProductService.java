package ly.qubit.inventory.services;

import ly.qubit.inventory.model.Product;
import ly.qubit.inventory.model.ProductDto;
import ly.qubit.inventory.model.SKUGenerator;
import ly.qubit.inventory.model.maper.ProductMapper;
import ly.qubit.inventory.repository.CategoriesRepository;
import ly.qubit.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    CategoriesRepository categoriesRepository ;


    @Autowired
    public ProductService(ProductRepository productRepository, CategoriesRepository categoriesRepository) {
        this.productRepository = productRepository;
        this.categoriesRepository = categoriesRepository;
    }

    public Optional<Product> save(Product product) {
        if(product.getSku() == null || product.getSku().isEmpty()) {
            String sku= SKUGenerator.generateSKU(product.getProductName(), product.getSize());
            product.setSku(sku);
        }
        if (productRepository.findBySku(product.getSku()).isPresent()) {
            throw new RuntimeException("Product with SKU " + product.getSku() + " already exists");
        }

        return Optional.of(productRepository.save(product));

    }

    public ProductDto update(ProductDto product) {
        if(product.sku() == null) {
            throw new RuntimeException("SKU is required");
        }

        Optional<Product> existingProduct = productRepository.findBySku(product.sku());

        if(existingProduct.isPresent()) {
            Product productToUpdate = existingProduct.get();
            productToUpdate.setProductName(product.productName());
            productToUpdate.setPrice(product.price());
            productToUpdate.setSize(product.size());

            categoriesRepository.findByCategoryId(product.categoryCId()).ifPresent(productToUpdate::setCategory);

            return ProductMapper.toDto(productRepository.save(productToUpdate));

        } else {
            throw new RuntimeException("Product with SKU " + product.sku() + " does not exist");
        }
    }
}
