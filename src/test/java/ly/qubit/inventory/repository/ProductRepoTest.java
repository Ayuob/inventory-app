package ly.qubit.inventory.repository;

import ly.qubit.inventory.model.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;




@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class ProductRepoTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.1")
            .withDatabaseName("inventory_db")
            .withUsername("inventory")
            .withPassword("inventory");


    @Autowired
    private ProductRepository productRepository;


    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setSku("FCP128");
        product.setProductName("First Cold Press");
        product.setSize(128);
        product.setPrice(BigDecimal.valueOf(24.99));

        Product found = productRepository.findById(product.getSku()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getSku()).isEqualTo(product.getSku());
    }
    @Test
    public void SaveProductWithSixDigitSku() {
        Product product = new Product();
        product.setSku("AlR128");  // Hardcoded SKU
        product.setProductName("Alwzan Rise");
        product.setSize(128);
        product.setPrice(new BigDecimal("24.99"));

        assertThat(product.getSku().length()).isEqualTo(6);
        assertThat(product.getSku()).isNotNull();
        productRepository.save(product);

        Product found = productRepository.findById(product.getSku()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getSku()).isEqualTo(product.getSku());
    }
    @Test
    public void SaveProductWithFiveDigitSku() {
        Product fiveDigit = new Product();
        fiveDigit.setSku("XE123");  // Hardcoded SKU
        fiveDigit.setProductName("Product Name");
        fiveDigit.setSize(128);
        fiveDigit.setPrice(new BigDecimal("4.99"));

        assertThat(fiveDigit.getSku().length()).isEqualTo(5);
        assertThat(fiveDigit.getSku()).isNotNull();
        productRepository.save(fiveDigit);

        Product found = productRepository.findById(fiveDigit.getSku()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getSku()).isEqualTo(fiveDigit.getSku());
    }
    @Test
    public void SaveProductWithThreeDigitSku() {
        Product fiveDigit = new Product();
        fiveDigit.setSku("X00");  // Hardcoded SKU
        fiveDigit.setProductName("Zero Product");
        fiveDigit.setSize(128);
        fiveDigit.setPrice(new BigDecimal("4.99"));

        assertThat(fiveDigit.getSku().length()).isEqualTo(3);
        assertThat(fiveDigit.getSku()).isNotNull();
        productRepository.save(fiveDigit);

        Product found = productRepository.findById(fiveDigit.getSku()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getSku()).isEqualTo(fiveDigit.getSku());
    }

    //test Update Product TODO
}
