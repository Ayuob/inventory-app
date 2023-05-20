package ly.qubit.inventory.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "orders", schema = "sales", catalog = "inventory_db")
public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    @Basic
    @Column(name = "order_date", nullable = true)
    private Date orderDate;

    @OneToMany(mappedBy = "ordersByOrderId")
    private Collection<OrderLines> orderLinesByOrderId;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customers customersByCustomerId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return Objects.equals(orderId, orders.orderId) && Objects.equals(orderDate, orders.orderDate) && Objects.equals(customersByCustomerId, orders.customersByCustomerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderDate, customersByCustomerId);
    }

    public Collection<OrderLines> getOrderLinesByOrderId() {
        return orderLinesByOrderId;
    }

    public void setOrderLinesByOrderId(Collection<OrderLines> orderLinesByOrderId) {
        this.orderLinesByOrderId = orderLinesByOrderId;
    }

    public Customers getCustomersByCustomerId() {
        return customersByCustomerId;
    }

    public void setCustomersByCustomerId(Customers customersByCustomerId) {
        this.customersByCustomerId = customersByCustomerId;
    }
}
