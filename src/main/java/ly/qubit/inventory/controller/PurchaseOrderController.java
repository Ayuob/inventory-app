package ly.qubit.inventory.controller;

import ly.qubit.inventory.model.purchase.PurchaseOrder;
import ly.qubit.inventory.model.purchase.PurchaseOrderDto;
import ly.qubit.inventory.model.purchase.PurchaseOrderLine;
import ly.qubit.inventory.model.purchase.Supplier;
import ly.qubit.inventory.repository.PurchaseOrderLineRepository;
import ly.qubit.inventory.repository.PurchaseOrderRepository;
import ly.qubit.inventory.repository.SupplierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
* Based on the extended schema that includes the purchase sub-schema, here are some API endpoints that reflect daily operations in an inventory app related to purchasing:

1. Get all purchase orders:
   - Endpoint: GET /purchase/orders
   - Description: Retrieves a list of all purchase orders.

2. Get purchase order by ID:
   - Endpoint: GET /purchase/orders/{orderId}
   - Description: Retrieves a specific purchase order based on its ID.

3. Create a new purchase order:
   - Endpoint: POST /purchase/orders
   - Description: Creates a new purchase order.
   - Request body: Purchase order details (e.g., supplier ID, order date).

4. Update purchase order details:
   - Endpoint: PUT /purchase/orders/{orderId}
   - Description: Updates the details of a specific purchase order.
   - Request body: Updated purchase order details.

5. Delete a purchase order:
   - Endpoint: DELETE /purchase/orders/{orderId}
   - Description: Deletes a specific purchase order.

6. Get all purchase order lines:
   - Endpoint: GET /purchase/orders/{orderId}/lines
   - Description: Retrieves all purchase order lines for a specific purchase order.

7. Get purchase order line by ID:
   - Endpoint: GET /purchase/orders/{orderId}/lines/{lineId}
   - Description: Retrieves a specific purchase order line based on its ID.

8. Add a new purchase order line:
   - Endpoint: POST /purchase/orders/{orderId}/lines
   - Description: Adds a new purchase order line to a specific purchase order.
   - Request body: Purchase order line details (e.g., product SKU, quantity).

9. Update purchase order line details:
   - Endpoint: PUT /purchase/orders/{orderId}/lines/{lineId}
   - Description: Updates the details of a specific purchase order line.
   - Request body: Updated purchase order line details.

10. Delete a purchase order line:
    - Endpoint: DELETE /purchase/orders/{orderId}/lines/{lineId}
    - Description: Deletes a specific purchase order line.

These endpoints allow you to manage purchase orders and their associated lines, facilitating the process of purchasing products from suppliers and tracking the quantities ordered.
* */
@RestController()
@RequestMapping("/api/purchases")
public class PurchaseOrderController {
    PurchaseOrderRepository purchaseOrderRepository;
    PurchaseOrderLineRepository purchaseOrderLineRepository;
    SupplierRepository supplierRepository;
    //constructor
    public PurchaseOrderController(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderLineRepository purchaseOrderLineRepository, SupplierRepository supplierRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderLineRepository = purchaseOrderLineRepository;
        this.supplierRepository = supplierRepository;
    }


    //Get all purchase orders:
    //Description: Retrieves a list of all purchase orders.
    @GetMapping("/orders")
    ResponseEntity<?> getOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderRepository.findAll());

    }



    //Get purchase order by ID:
    //Description: Retrieves a specific purchase order based on its ID.
    @GetMapping("/orders/{orderId}")
    ResponseEntity<?> getOrderById(@PathVariable Integer orderId){
        if(purchaseOrderRepository.findById(orderId).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderRepository.findById(orderId));
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
    }


    // post purchase order
    //Description: Creates a new purchase order.
    //Request body: Purchase order details (e.g., supplier ID, order date).
    @PostMapping("/orders")
    ResponseEntity<?> createOrder(@RequestBody PurchaseOrderDto orderDto){
        //id should be null
        if(orderDto.id() != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id should be null");
        }

        PurchaseOrder order = new PurchaseOrder();
        Set<PurchaseOrderLine> purchaseOrderLines = new LinkedHashSet<>();

        Supplier supplier = supplierRepository.findById(orderDto.supplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found for ID: " + orderDto.supplierId()));


        order.setSupplier(supplier);
        order.setOrderDate(orderDto.orderDate());
        order.setStatus(orderDto.status());

        orderDto.purchaseOrderLines().stream()
                    .map(line -> new PurchaseOrderLine(line.id(), order, line.sku(), line.quantity(), line.price()))
                    .forEach(oline -> purchaseOrderLines.add(oline));

        order.setPurchaseOrderLines(purchaseOrderLines);



        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseOrderRepository.save(order));

    }



    //Update purchase order details:
    //Description: Updates the details of a specific purchase order.
    //Request body: Updated purchase order details.
    @PutMapping ("/orders/{orderId}")
    ResponseEntity<?> updateOrder(@PathVariable Integer orderId, @RequestBody PurchaseOrderDto orderDto){
        if(orderDto.id() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id should NOT  be null");
        }

        PurchaseOrder order = new PurchaseOrder();

        Supplier supplier = supplierRepository.findById(orderDto.supplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found for ID: " + orderDto.supplierId()));

        order.setSupplier(supplier);
        order.setOrderDate(orderDto.orderDate());
        order.setStatus(orderDto.status());


        Set<PurchaseOrderLine> purchaseOrderLines = orderDto.purchaseOrderLines().stream()
                .map(line -> {
                    PurchaseOrderLine purchaseOrderLine = new PurchaseOrderLine();
                    purchaseOrderLine.setId(line.id());
                    purchaseOrderLine.setOrder(order);
                    purchaseOrderLine.setSku(line.sku());
                    purchaseOrderLine.setQuantity(line.quantity());
                    purchaseOrderLine.setPrice(line.price());
                    return purchaseOrderLine;
                })
                .collect(Collectors.toSet());

        Double orderTotalAmount = purchaseOrderLines.stream().map(line -> line.getPrice().multiply(BigDecimal.valueOf(line.getQuantity())).doubleValue()).reduce(0.0,Double::sum);
        Integer orderTotalQuantity = purchaseOrderLines.stream().map(line -> line.getQuantity()).reduce(0,Integer::sum);
        order.setTotalAmount(BigDecimal.valueOf(orderTotalAmount));
        order.setTotalQuantity(orderTotalQuantity);
        order.setPurchaseOrderLines(purchaseOrderLines);

        return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderRepository.save(order));
    }

    //  Delete a purchase order:
    //Description: Deletes a specific purchase order.
    @DeleteMapping("/orders/{orderId}")
    ResponseEntity<?> deleteOrder(@PathVariable Integer orderId){
        purchaseOrderRepository.deletePurchaseOrderByID(orderId);
        return  ResponseEntity.status(HttpStatus.OK).body("Order deleted successfully" );
    }


   // Get all purchase order lines:
   // -  Endpoint: GET /purchase/orders/{orderId}/lines
  //  -  Description: Retrieves all purchase order lines for a specific purchase order.
    @GetMapping("/orders/{orderId}/lines") ResponseEntity<?> getPurchaseOrderLinesByOrderId(@PathVariable Integer orderId){
        return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderLineRepository.findPurchaseOrderLinesByOrderId(orderId));
    }





}
