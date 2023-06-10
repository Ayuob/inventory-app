package ly.qubit.inventory.repository;

import ly.qubit.inventory.model.purchase.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}