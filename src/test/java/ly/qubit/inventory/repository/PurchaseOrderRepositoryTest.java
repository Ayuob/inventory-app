package ly.qubit.inventory.repository;

import ly.qubit.inventory.model.purchase.PurchaseOrder;
import ly.qubit.inventory.model.purchase.PurchaseOrderLine;
import ly.qubit.inventory.model.purchase.Supplier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
class PurchaseOrderRepositoryTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres")
                    .withInitScript("schema_with_purchases.sql");
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    SupplierRepository supplierRepository;


    @Test
    void saveMethodTest(){

        Supplier supplier = new Supplier();
        supplier.setSupplierName("name");
        supplier.setAddress("address");
        supplier.setContactEmail("contactEmail");
        supplier.setContactPhone("contactPhone");
        supplierRepository.save(supplier);
        assertNotNull(supplierRepository.findById(1));

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setStatus("status");
        purchaseOrder.setSupplier(supplier);


        PurchaseOrderLine purchaseOrderLine1 = new PurchaseOrderLine();
        purchaseOrderLine1.setSku("SKU1");
        purchaseOrderLine1.setQuantity(9);
        purchaseOrderLine1.setPrice(BigDecimal.valueOf(10));
        purchaseOrderLine1.setOrder(purchaseOrder);

        PurchaseOrderLine purchaseOrderLine2 = new PurchaseOrderLine();
        purchaseOrderLine2.setSku("SKU2");
        purchaseOrderLine2.setQuantity(9);
        purchaseOrderLine2.setPrice(BigDecimal.valueOf(10));
        purchaseOrderLine2.setOrder(purchaseOrder);

        purchaseOrder.setPurchaseOrderLines(Set.of(purchaseOrderLine1, purchaseOrderLine2));


       var savedOrder=  purchaseOrderRepository.save(purchaseOrder);

         assertNotNull(savedOrder);
         assertEquals(purchaseOrder.getOrderDate(), savedOrder.getOrderDate());
        assertThat(savedOrder).isNotNull();
        assertThat(!savedOrder.getPurchaseOrderLines().isEmpty()).isTrue();
        assertThat(savedOrder.getPurchaseOrderLines().size()).isEqualTo(2);
        assertThat(savedOrder.getPurchaseOrderLines().stream().anyMatch(purchaseOrderLine -> purchaseOrderLine.getSku().equals("SKU1"))).isTrue();
        assertThat(savedOrder.getPurchaseOrderLines().stream().anyMatch(purchaseOrderLine -> purchaseOrderLine.getOrder().getId().equals(savedOrder.getId()))).isTrue();

    }


}