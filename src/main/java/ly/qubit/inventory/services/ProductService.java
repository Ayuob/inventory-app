package ly.qubit.inventory.services;

import ly.qubit.inventory.model.Product;
import ly.qubit.inventory.model.SKUGenerator;
import ly.qubit.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
}
