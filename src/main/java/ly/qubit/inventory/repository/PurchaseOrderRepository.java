package ly.qubit.inventory.repository;

import ly.qubit.inventory.model.purchase.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
    @Transactional
    @Modifying
    @Query("delete from PurchaseOrder p where p.id = ?1")
    void deletePurchaseOrderByID(@NonNull Integer id);
}