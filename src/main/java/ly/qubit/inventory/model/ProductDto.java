package ly.qubit.inventory.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link ly.qubit.inventory.model.Product} entity
 */
public record ProductDto(String sku, String productName, Integer size, BigDecimal price, Integer categoryCId) implements Serializable {
}