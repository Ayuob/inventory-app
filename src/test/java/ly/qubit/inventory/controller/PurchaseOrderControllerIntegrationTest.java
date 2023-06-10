package ly.qubit.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ly.qubit.inventory.model.purchase.PurchaseOrder;
import ly.qubit.inventory.model.purchase.PurchaseOrderDto;
import ly.qubit.inventory.model.purchase.PurchaseOrderLine;
import ly.qubit.inventory.model.purchase.Supplier;
import ly.qubit.inventory.repository.PurchaseOrderRepository;
import ly.qubit.inventory.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PurchaseOrderController.class)
public class PurchaseOrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PurchaseOrderRepository purchaseOrderRepository;

    @MockBean
    private SupplierRepository supplierRepository;




    @Test
    void getOrderByITest() throws Exception {
        // Create a supplier
        Supplier supplier = new Supplier();
        supplier.setId(100);
        supplier.setSupplierName("Supplier 1");
        supplier.setAddress("Address 1");
        supplier.setCity("City 1");
        supplier.setContactEmail("Email 1");
        supplier.setContactPhone("Phone 1");
        when(supplierRepository.findById(any())).thenReturn(Optional.of(supplier));

        PurchaseOrder order = new PurchaseOrder();

        order.setId(1);
        order.setOrderDate(LocalDate.now());
        order.setSupplier(supplier);
        order.setStatus("Pending");
        order.setTotalQuantity(15);
        order.setTotalAmount(BigDecimal.valueOf(10*10.99+5*5.99));
        order.setPurchaseOrderLines(
                Set.of(
                        new PurchaseOrderLine(null, order, "SKU1", 10, BigDecimal.valueOf(10.99)),
                        new PurchaseOrderLine(null, order, "SKU2", 5, BigDecimal.valueOf(5.99))
                ));

        when(purchaseOrderRepository.findById(any())).thenReturn(Optional.of(order));

        //test mvc Get request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/purchases/orders/100"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.supplier.id").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.supplier.supplierName").value("Supplier 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.supplier.address").value("Address 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.supplier.city").value("City 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.supplier.contactEmail").value("Email 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.supplier.contactPhone").value("Phone 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Pending"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalQuantity").value(15))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalAmount").value(139.85000000000002));

        //Test in another end point Get purchaseOrderLines with the same
//                .andExpect(MockMvcResultMatchers.jsonPath("$.purchaseOrderLines", hasSize(2)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.purchaseOrderLines[0].id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.purchaseOrderLines[0].sku").value("SKU1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.purchaseOrderLines[0].quantity").value(10))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.purchaseOrderLines[0].unitPrice").value(10.99))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.purchaseOrderLines[1].id").value(2))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.purchaseOrderLines[1].sku").value("SKU2"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.purchaseOrderLines[1].quantity").value(5))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.purchaseOrderLines[1].unitPrice").value(5.99));



    }
    @Test
    void shouldREturnNotFound404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/purchases/orders/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOrder() throws Exception {
        // Create a supplier
        Supplier supplier = new Supplier();
        supplier.setId(100);
        supplier.setSupplierName("Supplier 1");
        supplier.setAddress("Address 1");
        supplier.setCity("City 1");
        supplier.setContactEmail("Email 1");
        supplier.setContactPhone("Phone 1");

        when(supplierRepository.findById(any())).thenReturn(Optional.of(supplier));


        // Create a purchase order DTO
        PurchaseOrderDto orderDto = new PurchaseOrderDto(null , LocalDate.now(), supplier.getId(), "Pending",

                Set.of(
                        new PurchaseOrderDto.PurchaseOrderLineDto(null,"SKU1", 10, BigDecimal.valueOf(10.99)),
                        new PurchaseOrderDto.PurchaseOrderLineDto(null,"SKU2", 5, BigDecimal.valueOf(5.99))
                ));

        PurchaseOrder order = new PurchaseOrder();

        order.setId(1);
        order.setOrderDate(LocalDate.now());
        order.setSupplier(supplier);
        order.setStatus("Pending");
        order.setPurchaseOrderLines(
                Set.of(
                        new PurchaseOrderLine(null, order, "SKU1", 10, BigDecimal.valueOf(10.99)),
                        new PurchaseOrderLine(null, order, "SKU2", 5, BigDecimal.valueOf(5.99))
                ));


        when(purchaseOrderRepository.save(any())).thenReturn(order);


        // Send POST request to create the order
        String orderDtoJson = objectMapper.writeValueAsString(orderDto);
        MvcResult result = mockMvc.perform(post("/api/purchases/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderDtoJson))
                .andExpect(status().isCreated())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
    @Test
    void shouldNotCreatOrderThatHaveID() throws Exception {
        // Create Order with id
        PurchaseOrderDto orderDto = new PurchaseOrderDto(1, LocalDate.now(), 100, "Pending",

                Set.of(
                        new PurchaseOrderDto.PurchaseOrderLineDto(null, "SKU1", 10, BigDecimal.valueOf(10.99)),
                        new PurchaseOrderDto.PurchaseOrderLineDto(null, "SKU2", 5, BigDecimal.valueOf(5.99))
                ));


        // Send POST request to create the order
        String orderDtoJson = objectMapper.writeValueAsString(orderDto);
        MvcResult result = mockMvc.perform(post("/api/purchases/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderDtoJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void updateOrderTest() throws Exception {
        // Create a supplier
        Supplier supplier = new Supplier();
        supplier.setId(100);
        supplier.setSupplierName("Supplier 1");
        supplier.setAddress("Address 1");
        supplier.setCity("City 1");
        supplier.setContactEmail("Email 1");
        supplier.setContactPhone("Phone 1");
        when(supplierRepository.findById(any())).thenReturn(Optional.of(supplier));

        PurchaseOrder order = new PurchaseOrder();
        order.setId(1);
        order.setOrderDate(LocalDate.now());
        order.setSupplier(supplier);
        order.setStatus("Pending");
        order.setTotalQuantity(10);
        order.setTotalAmount(BigDecimal.valueOf(100.99));
        when(purchaseOrderRepository.findById(any())).thenReturn(Optional.of(order));

        PurchaseOrderDto orderDto = new PurchaseOrderDto(1 , LocalDate.now(), 100, "Pending",

                Set.of(
                        new PurchaseOrderDto.PurchaseOrderLineDto(null,"SKU1", 10, BigDecimal.valueOf(10.99)),
                        new PurchaseOrderDto.PurchaseOrderLineDto(null,"SKU2", 5, BigDecimal.valueOf(5.99))
                ));

        PurchaseOrder updatedOrder = new PurchaseOrder();

        updatedOrder.setId(1);
        updatedOrder.setOrderDate(LocalDate.now());
        updatedOrder.setSupplier(supplier);
        updatedOrder.setTotalAmount(BigDecimal.valueOf( 10*10.99 + 5*5.99));
        updatedOrder.setTotalQuantity(15);
        updatedOrder.setStatus("Pending");
        updatedOrder.setPurchaseOrderLines(
                Set.of(
                        new PurchaseOrderLine(null, updatedOrder, "SKU1", 10, BigDecimal.valueOf(10.99)),
                        new PurchaseOrderLine(null, updatedOrder, "SKU2", 5, BigDecimal.valueOf(5.99))
                ));

        when(purchaseOrderRepository.save(any())).thenReturn(updatedOrder);

        ResponseEntity<PurchaseOrderDto> response = new ResponseEntity<>(orderDto, HttpStatus.OK);

        String orderDtoJson = objectMapper.writeValueAsString(orderDto);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/purchases/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderDtoJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalQuantity").value(15))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalAmount").value(10*10.99 + 5*5.99))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Pending"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.supplier.id").value(100))
                .andReturn();

    }

    @Test
    void sholdDeleteOrderById() throws Exception {
         mockMvc.perform(MockMvcRequestBuilders.delete("/api/purchases/orders/1"))
              .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order deleted successfully"));

         verify(purchaseOrderRepository, times(1)).deletePurchaseOrderByID(any());

    }



}
