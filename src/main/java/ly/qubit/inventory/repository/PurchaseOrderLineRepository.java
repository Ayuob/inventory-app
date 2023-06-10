package ly.qubit.inventory.repository;

import ly.qubit.inventory.model.purchase.PurchaseOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PurchaseOrderLineRepository extends JpaRepository<PurchaseOrderLine, Integer> {
    Set<PurchaseOrderLine> findPurchaseOrderLinesByOrderId(Integer orderId);
}