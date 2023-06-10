package ly.qubit.inventory.model.purchase;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "purchase_order_lines", schema = "purchase")
public class PurchaseOrderLine {
    public PurchaseOrderLine(Integer id, PurchaseOrder order, String sku, Integer quantity, BigDecimal price) {
        this.id = id;
        this.order = order;
        this.sku = sku;
        this.quantity = quantity;
        this.price = price;

    }
    public PurchaseOrderLine() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private PurchaseOrder order;

    @Column(name = "sku", length = 7)
    private String sku;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price", precision = 5, scale = 2)
    private BigDecimal price;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;
}