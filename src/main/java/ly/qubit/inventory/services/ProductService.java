package ly.qubit.inventory.services;

import ly.qubit.inventory.model.Product;
import ly.qubit.inventory.model.SKUGenerator;
import ly.qubit.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        if(product.getSku() == null || product.getSku().isEmpty()){
            String sku= SKUGenerator.generateSKU(product.getProductName(), product.getSize());
            product.setSku(sku);
        }

        return productRepository.save(product);

    }
}
