package ly.qubit.inventory.model.purchase;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link PurchaseOrder}
 */
public record PurchaseOrderDto(Integer id, LocalDate orderDate, Integer supplierId, String status,
                               Set<PurchaseOrderLineDto> purchaseOrderLines) implements Serializable {
    /**
     * DTO for {@link PurchaseOrderLine}
     */
    public record PurchaseOrderLineDto(Integer id, String sku, Integer quantity,
                                       BigDecimal price) implements Serializable {
    }
}