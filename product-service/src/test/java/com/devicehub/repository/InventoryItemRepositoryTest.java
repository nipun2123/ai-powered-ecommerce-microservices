package com.devicehub.repository;

import com.devicehub.entity.InventoryItem;
import com.devicehub.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
class InventoryItemRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine")
            .withDatabaseName("product-service")
            .withUsername("postgres")
            .withPassword("admin");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",postgres::getJdbcUrl);
        registry.add("spring.datasource.username",postgres::getUsername);
        registry.add("spring.datasource.password",postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto",()->"create-drop");
    }

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Product product = Product.builder()
                .sku("TB-APP-01")
                .name("iPad Pro")
                .brand("Apple")
                .os("iPadOS")
                .barcode("X003232887482")
                .batteryMah(3274)
                .category(Product.Category.tablet)
                .chipset("M2")
                .price(BigDecimal.valueOf(900.99))
                .ramGb(32)
                .description("Sample description")
                .frontCamMp(BigDecimal.valueOf(48.8))
                .mainCamMp(BigDecimal.valueOf(64.8))
                .storageGb(256)
                .refreshRateHz(120)
                .screenSizeInches(BigDecimal.valueOf(6.7))
                .isActive(false)
                .build();

        Product savedProduct =productRepository.save(product);

        InventoryItem inventoryItem = InventoryItem.builder()
                .product(savedProduct)
                .batchNumber("BATCH-2024-04")
                .availableQuantity(20)
                .build();

        inventoryItemRepository.save(inventoryItem);

    }

    @Test
    void shouldRetrieveInventoryById(){

        Product product = Product.builder()
                .sku("TB-SAM-01")
                .name("Samsung Galaxy Tab S9")
                .brand("Samsung")
                .os("Android")
                .barcode("X003232887421")
                .batteryMah(3274)
                .category(Product.Category.tablet)
                .chipset("Snapdragon 8 Gen 2")
                .price(BigDecimal.valueOf(900.99))
                .ramGb(32)
                .description("Sample description")
                .frontCamMp(BigDecimal.valueOf(48.8))
                .mainCamMp(BigDecimal.valueOf(64.8))
                .storageGb(256)
                .refreshRateHz(120)
                .screenSizeInches(BigDecimal.valueOf(6.7))
                .isActive(true)
                .build();
        
        Product savedProduct =productRepository.save(product);
        
        InventoryItem inventoryItem = InventoryItem.builder()
                .product(savedProduct)
                .batchNumber("BATCH-2024-11")
                .availableQuantity(25)
                .build();
        InventoryItem savedInventoryItem = inventoryItemRepository.save(inventoryItem);

        List<InventoryItem> inventoryItemList = inventoryItemRepository.findByProductId(savedProduct.getId());
        assertEquals(1, inventoryItemList.size());
        assertEquals(savedInventoryItem.getBatchNumber(), inventoryItemList.get(0).getBatchNumber());
    }


    @Test
    void shouldRetrieveInventoryByBatchNumber(){

        List<InventoryItem> inventoryItemList = inventoryItemRepository.findByBatchNumber("BATCH-2024-04");
        assertEquals(1, inventoryItemList.size());
        assertEquals("BATCH-2024-04", inventoryItemList.get(0).getBatchNumber());
    }

    @Test
    void shouldRetrieveTotalAvailableQtyByProductId(){

        Product product = Product.builder()
                .sku("WD-FDF-01")
                .name("Samsung A6")
                .brand("Samsung")
                .os("Android")
                .barcode("fefreg3232")
                .batteryMah(3274)
                .category(Product.Category.phone)
                .chipset("A16 Bionic")
                .price(BigDecimal.valueOf(900.99))
                .ramGb(32)
                .description("Sample description")
                .frontCamMp(BigDecimal.valueOf(48.8))
                .mainCamMp(BigDecimal.valueOf(64.8))
                .storageGb(256)
                .refreshRateHz(120)
                .screenSizeInches(BigDecimal.valueOf(6.7))
                .isActive(true)
                .build();

        Product savedProduct = productRepository.save(product);

        InventoryItem inventoryItem1 = InventoryItem.builder()
                .product(savedProduct)
                .batchNumber("BATCH-2024-06")
                .availableQuantity(10)
                .build();
        InventoryItem inventoryItem2 = InventoryItem.builder()
                .product(savedProduct)
                .batchNumber("BATCH-2024-09")
                .availableQuantity(14)
                .build();

        inventoryItemRepository.save(inventoryItem1);
        inventoryItemRepository.save(inventoryItem2);

        int availableQty = inventoryItemRepository.findTotalAvailableQuantityByProductId(savedProduct.getId());
        assertEquals(24, availableQty);
    }


    @Test
    void shouldRetrieveZeroForNoInventory(){

        Product product = Product.builder()
                .sku("AD-APP-01")
                .name("Iphone 14 Pro")
                .brand("Apple")
                .os("IOS")
                .barcode("X002322323")
                .batteryMah(3274)
                .category(Product.Category.phone)
                .chipset("A16 Bionic")
                .price(BigDecimal.valueOf(900.99))
                .ramGb(32)
                .description("Sample description")
                .frontCamMp(BigDecimal.valueOf(48.8))
                .mainCamMp(BigDecimal.valueOf(64.8))
                .storageGb(256)
                .refreshRateHz(120)
                .screenSizeInches(BigDecimal.valueOf(6.7))
                .isActive(false)
                .build();

        Product savedProduct = productRepository.save(product);

        int availableQty = inventoryItemRepository.findTotalAvailableQuantityByProductId(savedProduct.getId());
        assertEquals(0, availableQty);
    }


    @Test
    void shouldExistsInStockByProductId(){

        Product product = Product.builder()
                .sku("AD-APP-21")
                .name("Iphone 14 Pro")
                .brand("Apple")
                .os("IOS")
                .barcode("X0344422332")
                .batteryMah(3274)
                .category(Product.Category.phone)
                .chipset("A16 Bionic")
                .price(BigDecimal.valueOf(900.99))
                .ramGb(32)
                .description("Sample description")
                .frontCamMp(BigDecimal.valueOf(48.8))
                .mainCamMp(BigDecimal.valueOf(64.8))
                .storageGb(256)
                .refreshRateHz(120)
                .screenSizeInches(BigDecimal.valueOf(6.7))
                .isActive(false)
                .build();

        Product savedProduct = productRepository.save(product);

        InventoryItem inventoryItem = InventoryItem.builder()
                .product(savedProduct)
                .batchNumber("BATCH-2024-01")
                .availableQuantity(1)
                .build();

        inventoryItemRepository.save(inventoryItem);

        boolean isInStock = inventoryItemRepository.existsInStockByProductId(savedProduct.getId());
        assertTrue(isInStock);
    }


    @Test
    void shouldRetrieveProductByAvailableQuantityLessThan(){

        Product product1 = Product.builder()
                .sku("IP-APP-14")
                .name("Iphone 14 Pro")
                .brand("Apple")
                .os("IOS")
                .barcode("X0344422111")
                .batteryMah(3274)
                .category(Product.Category.phone)
                .chipset("A16 Bionic")
                .price(BigDecimal.valueOf(900.99))
                .ramGb(32)
                .description("Sample description")
                .frontCamMp(BigDecimal.valueOf(48.8))
                .mainCamMp(BigDecimal.valueOf(64.8))
                .storageGb(256)
                .refreshRateHz(120)
                .screenSizeInches(BigDecimal.valueOf(6.7))
                .isActive(false)
                .build();

        Product product2 = Product.builder()
                .sku("SM-FDF-06")
                .name("Samsung A6")
                .brand("Samsung")
                .os("Android")
                .barcode("X00329382938293")
                .batteryMah(3274)
                .category(Product.Category.phone)
                .chipset("A16 Bionic")
                .price(BigDecimal.valueOf(900.99))
                .ramGb(32)
                .description("Sample description")
                .frontCamMp(BigDecimal.valueOf(48.8))
                .mainCamMp(BigDecimal.valueOf(64.8))
                .storageGb(256)
                .refreshRateHz(120)
                .screenSizeInches(BigDecimal.valueOf(6.7))
                .isActive(true)
                .build();

        Product product3 = Product.builder()
                .sku("SM-FDF-05")
                .name("Samsung A5")
                .brand("Samsung")
                .os("Android")
                .barcode("X00329382121")
                .batteryMah(3274)
                .category(Product.Category.phone)
                .chipset("A16 Bionic")
                .price(BigDecimal.valueOf(900.99))
                .ramGb(32)
                .description("Sample description")
                .frontCamMp(BigDecimal.valueOf(48.8))
                .mainCamMp(BigDecimal.valueOf(64.8))
                .storageGb(256)
                .refreshRateHz(120)
                .screenSizeInches(BigDecimal.valueOf(6.7))
                .isActive(true)
                .build();

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);
        Product savedProduct3 = productRepository.save(product3);

        InventoryItem inventoryItem1 = InventoryItem.builder()
                .product(savedProduct1)
                .batchNumber("BATCH-2024-01")
                .availableQuantity(5)
                .build();
        InventoryItem inventoryItem2 = InventoryItem.builder()
                .product(savedProduct1)
                .batchNumber("BATCH-2024-02")
                .availableQuantity(8)
                .build();

        InventoryItem inventoryItem3 = InventoryItem.builder()
                .product(savedProduct2)
                .batchNumber("BATCH-2025-02")
                .availableQuantity(25)
                .build();

        InventoryItem inventoryItem4 = InventoryItem.builder()
                .product(savedProduct3)
                .batchNumber("BATCH-2025-03")
                .availableQuantity(20)
                .build();

        inventoryItemRepository.save(inventoryItem1);
        inventoryItemRepository.save(inventoryItem2);
        inventoryItemRepository.save(inventoryItem3);
        inventoryItemRepository.save(inventoryItem4);

        List<Product> productsWithLowStock = inventoryItemRepository.findProductsWithLowStock(15);
        assertEquals(1, productsWithLowStock.size());
        assertEquals(savedProduct1.getId(), productsWithLowStock.get(0).getId());
    }

}