package ly.qubit.inventory.controller;

import ly.qubit.inventory.model.Product;
import ly.qubit.inventory.model.ProductDTO;
import ly.qubit.inventory.repository.ProductRepository;
import ly.qubit.inventory.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/api/products/{sku}") public Product getProductBySku(@PathVariable String sku) {
        return productRepository.findBySku(sku).get();
    }

    //POST /api/products: Creates a new product. The product data should be included in the request body.
    @PostMapping("/api/products")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try{
            Optional<Product> createdProduct= productService.save(product);
            return createdProduct.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        }catch (RuntimeException e){
            log.error("Error creating product", e);
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
         }

    //PUT /api/products/{sku}: Updates a specific product by SKU. The updated product data should be included in the request body.
    @PutMapping("/api/products")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO product) {

        if(product.sku() == null) {
            return ResponseEntity.badRequest().body("SKU is required");
        }
        try{
          ProductDTO updatedProduct =   productService.update(product);
            return ResponseEntity.created(URI.create("/api/products/"+updatedProduct.sku())).body(updatedProduct);
        }catch (RuntimeException e){
            log.error("Error updating product", e);
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    //DELETE /api/products/{sku}: Deletes a specific product by SKU.


    //GET /api/products?min_price=x&max_price=y: Retrieves a list of products within a specific price range.

    //GET /api/products/{sku}/orders: Retrieves a list of all orders for a specific product.
    //GET /api/products/{sku}/order-lines: Retrieves a list of all order lines for a specific product.

    //GET /api/products?category=z: Retrieves a list of products within a specific category.
}
