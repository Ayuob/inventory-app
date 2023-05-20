package ly.qubit.inventory.services;

import ly.qubit.inventory.model.Product;
import ly.qubit.inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test()
    public void shouldNotSaveExistingProduct() {
        Product product = new Product();
        product.setSku("ALB008");
        product.setProductName("Delicate");
        product.setSize(8);
        product.setPrice(BigDecimal.valueOf(10.99));


        when(productRepository.findBySku("ALB008")).thenReturn(Optional.of(product));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.save(product);
        });
        String expectedMessage = "already exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldNotSaveExistingProductWithNulSKU() {
        Product product = new Product();
        product.setSku(null);
        product.setProductName("Delicate");
        product.setSize(8);
        product.setPrice(BigDecimal.valueOf(10.99));

        when(productRepository.findBySku(any())).thenReturn(Optional.of(product));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.save(product);
        });

        String expectedMessage = "already exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test()
    public void saveNewProductWithNullSKU() {
        Product product = new Product();
        product.setSku(null);
        product.setProductName("New Product Save");
        product.setSize(128);
        product.setPrice(BigDecimal.valueOf(24.99));

        when(productRepository.findBySku(any())).thenReturn(Optional.empty());
        when(productRepository.save(any())).thenReturn(product);

        Product savedProduct = productService.save(product);

        assertNotNull(savedProduct);
    }
    @Test()
    public void saveNewProductWithSKU() {
        Product product = new Product();
        product.setSku("NPS128");
        product.setProductName("New Product Save");
        product.setSize(128);
        product.setPrice(BigDecimal.valueOf(24.99));

        when(productRepository.findBySku(any())).thenReturn(Optional.empty());
        when(productRepository.save(any())).thenReturn(product);

        Product savedProduct = productService.save(product);

        assertNotNull(savedProduct);
    }

}
