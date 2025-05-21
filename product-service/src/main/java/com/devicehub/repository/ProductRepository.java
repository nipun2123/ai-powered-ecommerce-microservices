package com.devicehub.repository;


import com.devicehub.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, PagingAndSortingRepository<Product, UUID> {

    @Query("SELECT p FROM Product p WHERE p.isActive = true")
    Page<Product> findAvailableProducts(PageRequest pageRequest);

    @Query("SELECT p FROM Product p WHERE p.category = phone")
    Page<Product> findAllPhones(PageRequest pageRequest);
    @Query("SELECT p FROM Product p WHERE p.category = phone AND p.isActive = true")
    Page<Product> findAvailablePhones(PageRequest pageRequest);
    @Query("SELECT p FROM Product p WHERE p.category = tablet")
    Page<Product> findAllTablet(PageRequest pageRequest);
    @Query("SELECT p FROM Product p WHERE p.category = tablet AND p.isActive = true")
    Page<Product> findAvailableTablet(PageRequest pageRequest);

    Page<Product> findByBrandIgnoreCase(String brand, PageRequest pageRequest);

    Page<Product> findByBrandIgnoreCaseAndCategory(String brand, Product.Category category, PageRequest pageRequest);

    Optional<Product> findBySku(String sku);
    Optional<Product> findByNameIgnoreCase(String name);

    Page<Product> findByNameContainingIgnoreCase(String keyword, PageRequest pageRequest);
    boolean existsBySku(String sku);

}
