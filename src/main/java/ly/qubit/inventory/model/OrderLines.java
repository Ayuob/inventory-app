package ly.qubit.inventory.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "order_lines", schema = "sales", catalog = "inventory_db")
public class OrderLines {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "line_id", nullable = false)
    private Integer lineId;
    @Basic
    @Column(name = "sku", nullable = true, length = 7)
    private String sku;
    @Basic
    @Column(name = "quantity", nullable = true)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Orders ordersByOrderId;

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLines that = (OrderLines) o;
        return Objects.equals(ordersByOrderId, that.ordersByOrderId) && Objects.equals(lineId, that.lineId) && Objects.equals(sku, that.sku) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ordersByOrderId, lineId, sku, quantity);
    }

    public Orders getOrdersByOrderId() {
        return ordersByOrderId;
    }

    public void setOrdersByOrderId(Orders ordersByOrderId) {
        this.ordersByOrderId = ordersByOrderId;
    }
}
