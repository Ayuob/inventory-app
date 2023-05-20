package ly.qubit.inventory.services;

import ly.qubit.inventory.model.Product;
import ly.qubit.inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setSku("FCP128");
        product.setProductName("First Cold Press");
        product.setSize(128);
        product.setPrice(BigDecimal.valueOf(24.99));

        productService.save(product);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testSave5LenthProduct() {
        Product product = new Product();
        product.setSku("TSS128");
        product.setProductName("Test Save Service");
        product.setSize(128);
        product.setPrice(BigDecimal.valueOf(24.99));

       Product productAfterSave=  productService.save(product);

        verify(productRepository, times(1)).save(product);
        assertThat(productAfterSave.getSku()).isEqualTo(product.getSku());
    }
    @Test
    public void testSaveProductWithNullSKU() {
        Product product = new Product();
        product.setSku(null);
        product.setProductName("First Cold Press");
        product.setSize(128);
        product.setPrice(BigDecimal.valueOf(24.99));

        assertThat(product.getSku()).isNull();

        Product afterSave = productService.save(product);

        verify(productRepository, times(1)).save(product);
        assertThat(afterSave.getSku()).isNotNull();
    }
}
