package ly.qubit.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ly.qubit.inventory.model.Product;
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
}
