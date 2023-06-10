package ly.qubit.inventory.repository;

import ly.qubit.inventory.model.purchase.PurchaseOrder;
import ly.qubit.inventory.model.purchase.PurchaseOrderLine;
import ly.qubit.inventory.model.purchase.Supplier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Testcontainers
class PurchaseOrderLineRepositoryIT {
    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres")
                    .withInitScript("schema_with_purchases.sql");
    @Autowired
    PurchaseOrderLineRepository purchaseOrderLineRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Test
    void shouldGetPurchaseOrderLinesByOrderId(){
        // given


        Supplier supplier = new Supplier();
        supplier.setSupplierName("Supplier 1");
        supplier.setAddress("Address 1");
        supplier.setCity("City 1");
        supplier.setContactEmail("Email 1");
        supplier.setContactPhone("Phone 1");

        Supplier savedSupplier = supplierRepository.save(supplier);

        PurchaseOrder order = new PurchaseOrder();
        order.setOrderDate(LocalDate.now());
        order.setSupplier(savedSupplier);
        order.setStatus("Pending");
        order.setTotalQuantity(10);
        order.setTotalAmount(BigDecimal.valueOf(100.99));

        PurchaseOrderLine line1 = new PurchaseOrderLine();
        line1.setOrder(order);
        line1.setPrice(BigDecimal.valueOf(10.99));
        line1.setQuantity(1);
        line1.setSku("SKU1");

        PurchaseOrderLine line2 = new PurchaseOrderLine();
        line2.setOrder(order);
        line2.setPrice(BigDecimal.valueOf(20.99));
        line2.setQuantity(2);
        line2.setSku("SKU2");

        order.setPurchaseOrderLines(Set.of(line1, line2));
        PurchaseOrder savedOrder = purchaseOrderRepository.save(order);

        var setOfPOLS = purchaseOrderLineRepository.findPurchaseOrderLinesByOrderId(savedOrder.getId());
        assertThat(savedOrder.getId()).isEqualTo(setOfPOLS.iterator().next().getOrder().getId());
        assertThat(setOfPOLS.size()).isEqualTo(2);



    }


}