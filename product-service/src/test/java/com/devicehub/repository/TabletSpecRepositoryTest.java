package com.devicehub.repository;

import com.devicehub.entity.Product;
import com.devicehub.entity.TabletSpec;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
class TabletSpecRepositoryTest {


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
    private ProductRepository productRepository;

    @Autowired
    private TabletSpecRepository tabletSpecRepository;


    @Test
    void shouldCreateAndRetriveTabletSpec(){

        Product product = Product.builder()
                .sku("WD-FDF-01")
                .name("Samsung A6")
                .brand("Samsung")
                .os("Android")
                .barcode("fefreg3232")
                .batteryMah(3274)
                .category(Product.ProductCategory.TABLET)
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

        TabletSpec tabletSpecModel = TabletSpec.builder()
                .product(savedProduct)
                .hasCellular(true)
                .stylusSupported(true)
                .keyboardSupport(true)
                .build();

        TabletSpec saveTabletSpec = tabletSpecRepository.save(tabletSpecModel);

        Optional<TabletSpec> tabletSpec = tabletSpecRepository.findById(saveTabletSpec.getId());

        assertTrue(tabletSpec.isPresent());
        assertEquals(saveTabletSpec.isHasCellular(),tabletSpec.get().isHasCellular());
        assertEquals(saveTabletSpec.getProduct().getName(),tabletSpec.get().getProduct().getName());
    }

}