package ly.qubit.inventory.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Customers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "customer_id", nullable = false, length = 5)
    private String customerId;
    @Basic
    @Column(name = "company", nullable = true, length = 100)
    private String company;
    @Basic
    @Column(name = "address", nullable = true, length = 100)
    private String address;
    @Basic
    @Column(name = "city", nullable = true, length = 50)
    private String city;
    @Basic
    @Column(name = "state", nullable = true, length = 2)
    private String state;
    @Basic
    @Column(name = "zip", nullable = true, length = 5)
    private String zip;
    @Basic
    @Column(name = "newsletter", nullable = true)
    private Boolean newsletter;
    @OneToMany(mappedBy = "customersByCustomerId")
    private Collection<Orders> ordersByCustomerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customers customers = (Customers) o;
        return Objects.equals(customerId, customers.customerId) && Objects.equals(company, customers.company) && Objects.equals(address, customers.address) && Objects.equals(city, customers.city) && Objects.equals(state, customers.state) && Objects.equals(zip, customers.zip) && Objects.equals(newsletter, customers.newsletter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, company, address, city, state, zip, newsletter);
    }

    public Collection<Orders> getOrdersByCustomerId() {
        return ordersByCustomerId;
    }

    public void setOrdersByCustomerId(Collection<Orders> ordersByCustomerId) {
        this.ordersByCustomerId = ordersByCustomerId;
    }
}
