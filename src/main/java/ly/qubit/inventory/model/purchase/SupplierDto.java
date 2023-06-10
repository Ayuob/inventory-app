package ly.qubit.inventory.model.purchase;

import java.io.Serializable;

/**
 * DTO for {@link ly.qubit.inventory.model.purchase.Supplier}
 */
public record SupplierDto(Integer id, String supplierName, String address, String city,
                          String contactEmail, String contactPhone) implements Serializable {
}