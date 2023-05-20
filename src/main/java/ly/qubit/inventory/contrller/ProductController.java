package ly.qubit.inventory.contrller;

import ly.qubit.inventory.model.DTOs.ProductDTO;
import ly.qubit.inventory.model.Product;
import ly.qubit.inventory.model.SKUGenerator;
import ly.qubit.inventory.model.maper.ProductMapper;
import ly.qubit.inventory.repository.ProductRepository;
import ly.qubit.inventory.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    ProductRepository productRepository;
    ProductService productService;
    Logger log = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }
    //GET /api/products: Retrieves a list of all products.
    @GetMapping("/api/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    //GET /api/products/{sku}: Retrieves a specific product by SKU.
    @GetMapping("/api/products/{sku}") public List<Product> getProductBySku(@PathVariable String sku) {
        return productRepository.findBySku(sku);
    }

    //POST /api/products: Creates a new product. The product data should be included in the request body.
    @PostMapping("/api/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {

        Product createdProduct = productService.save(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    //PUT /api/products/{sku}: Updates a specific product by SKU. The updated product data should be included in the request body.
    //DELETE /api/products/{sku}: Deletes a specific product by SKU.


    //GET /api/products?min_price=x&max_price=y: Retrieves a list of products within a specific price range.
    //GET /api/products/{sku}: Retrieves a specific product by SKU.
    //GET /api/products/{sku}/orders: Retrieves a list of all orders for a specific product.

    //GET /api/products/{sku}/order-lines: Retrieves a list of all order lines for a specific product.
    //GET /api/products?category=z: Retrieves a list of products within a specific category.
}
