package ly.qubit.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ly.qubit.inventory.model.Product;
import ly.qubit.inventory.model.ProductDTO;
import ly.qubit.inventory.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createProductTest() throws Exception {
        Product product = new Product();
        product.setProductName("New Product");
        product.setSize(128);
        product.setPrice(BigDecimal.valueOf(24.99));

        when(productService.save(any())).thenReturn(Optional.of(product));

        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldNotCreateProduct() throws Exception {
        Product product = new Product();
        product.setProductName("New Product");
        product.setSize(128);
        product.setPrice(BigDecimal.valueOf(24.99));

        when(productService.save(any())).thenReturn(Optional.empty());

        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void shouldReturnConflictResponse() throws Exception {
        Product product = new Product();
        product.setProductName("New Product");
        product.setSize(128);
        product.setPrice(BigDecimal.valueOf(24.99));

        when(productService.save(any())).thenThrow(new RuntimeException("Product already exists"));

        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isConflict());

    }

    @Test
    public void shouldNOtUpdateProductWithoutSkuTest() throws Exception {
        ProductDTO productWithNullSku = new ProductDTO(null, "New Product", 128, BigDecimal.valueOf(24.99), 1);


        when(productService.update(any())).thenReturn(productWithNullSku);


        when(productService.update(any())).thenReturn(productWithNullSku);

        String productJson = objectMapper.writeValueAsString(productWithNullSku);

        mockMvc.perform(put("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateProductTest() throws Exception {
        ProductDTO product = new ProductDTO("NP128", "New Product", 128, BigDecimal.valueOf(24.99), 1);


        when(productService.update(any())).thenReturn(product);

        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(put("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sku").value("NP128"));
    }
}
