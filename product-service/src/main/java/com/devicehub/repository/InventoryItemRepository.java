package com.devicehub.repository;

import com.devicehub.entity.InventoryItem;
import com.devicehub.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, UUID> {

    List<InventoryItem>  findByProductId(UUID productId);
    List<InventoryItem> findByBatchNumber(String batchNumber);

    @Query("SELECT COALESCE(SUM(i.availableQuantity), 0) FROM InventoryItem i WHERE i.product.id = :productId")
    int findTotalAvailableQuantityByProductId(@Param("productId") UUID productId);


    @Query("SELECT COUNT(i) > 0 FROM InventoryItem i WHERE i.product.id = :productId AND i.availableQuantity > 0")
    boolean existsInStockByProductId(@Param("productId") UUID productId);

    @Query("SELECT i.product FROM InventoryItem i GROUP BY i.product HAVING SUM(i.availableQuantity) < :threshold")
    List<Product> findProductsWithLowStock(@Param("threshold") int threshold);

}
