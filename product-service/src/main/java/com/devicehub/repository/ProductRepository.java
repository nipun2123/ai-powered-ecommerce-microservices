package com.devicehub.repository;


import com.devicehub.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {


    @Query("SELECT p FROM Product p WHERE p.isActive = true")
    List<Product> findAvailableProducts();

    @Query("SELECT p FROM Product p WHERE p.category = phone")
    List<Product> findAllPhones();
    @Query("SELECT p FROM Product p WHERE p.category = phone AND p.isActive = true")
    List<Product> findAvailablePhones();
    @Query("SELECT p FROM Product p WHERE p.category = tablet")
    List<Product> findAllTablet();
    @Query("SELECT p FROM Product p WHERE p.category = tablet AND p.isActive = true")
    List<Product> findAvailableTablet();

    List<Product> findByBrand(String brand);
    Optional<Product> findBySku(String sku);
    Optional<Product> findByName(String name);

    List<Product> findByBrandAndCategory(String brand, Product.Category category);


    List<Product> findByNameContainingIgnoreCase(String keyword);
    boolean existsBySku(String sku);

}
