package com.devicehub.repository;


import com.devicehub.entity.PhoneSpec;
import com.devicehub.entity.Product;
import com.devicehub.entity.TabletSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
class ProductRepositoryTest {

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


    @BeforeEach
    void setUp() {
        Product product1 = Product.builder()
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


        Product product2 = Product.builder()
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

        Product product3 = Product.builder()
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

        Product product4 = Product.builder()
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

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
    }


    @Test
    void shouldCreateAndRetrieveProductPhone(){

        PhoneSpec phoneSpecModel = PhoneSpec.builder()
                .hasEsim(true)
                .ipRating("IP68")
                .ultraWideCamMp(BigDecimal.valueOf(68.8))
                .build();

        Product product = Product.builder()
                .sku("PH-APP-01")
                .name("iPhone 15 Pro")
                .brand("Apple")
                .os("IOS")
                .barcode("X00329828329")
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
                .phoneSpec(phoneSpecModel)
                .build();
        phoneSpecModel.setProduct(product);

        productRepository.save(product);

        Optional<Product> savedProduct = productRepository.findBySku(product.getSku());

        assertTrue(savedProduct.isPresent());

        assertEquals(product.getName(),savedProduct.get().getName());
        assertEquals("PHONE",savedProduct.get().getCategory().name());
        assertEquals(product.getPhoneSpec().getIpRating(),savedProduct.get().getPhoneSpec().getIpRating());
    }


    @Test
    void shouldCreateAndRetrieveProductTablet(){

        TabletSpec tabletSpecModel = TabletSpec.builder()
                .hasCellular(true)
                .stylusSupported(true)
                .keyboardSupport(true)
                .build();

        Product product = Product.builder()
                .sku("IP-APPL-15")
                .name("IPad Pro Max")
                .brand("Apple")
                .os("IOS")
                .barcode("X003111111")
                .batteryMah(3274)
                .category(Product.Category.tablet)
                .chipset("A16 Bionic")
                .price(BigDecimal.valueOf(900.99))
                .ramGb(32)
                .description("Sample description")
                .frontCamMp(BigDecimal.valueOf(48.8))
                .mainCamMp(BigDecimal.valueOf(64.8))
                .storageGb(256)
                .refreshRateHz(120)
                .screenSizeInches(BigDecimal.valueOf(6.7))
                .tabletSpec(tabletSpecModel)
                .build();
        tabletSpecModel.setProduct(product);

        productRepository.save(product);

        Optional<Product> savedProduct = productRepository.findBySku(product.getSku());

        assertTrue(savedProduct.isPresent());

        assertEquals(product.getName(),savedProduct.get().getName());
        assertEquals(product.getTabletSpec().isHasCellular(),savedProduct.get().getTabletSpec().isHasCellular());
    }


    @Test
    void shouldRetrieveAllProducts(){

        List<Product> allProducts = productRepository.findAll();
        assertEquals(4, allProducts.size());
    }

    @Test
    void shouldRetrieveAllAvailableProduct(){
        PageRequest pageRequest = PageRequest.of(0, 4);
        List<Product> allProducts = productRepository.findAvailableProducts(pageRequest).toList();
        assertEquals(2, allProducts.size());
        assertEquals("WD-FDF-01", allProducts.get(0).getSku());
        System.out.println("Phone data:................"+allProducts.get(0).getPhoneSpec());
    }

    @Test
    void shouldRetrieveAllPhones(){
        PageRequest pageRequest = PageRequest.of(0, 4);
        List<Product> allPhones = productRepository.findAllPhones(pageRequest).toList();
        assertEquals(2, allPhones.size());
    }

    @Test
    void shouldRetrieveAvailablePhones(){
        PageRequest pageRequest = PageRequest.of(0, 4);
        List<Product> availablePhones = productRepository.findAvailablePhones(pageRequest).toList();
        assertEquals(1, availablePhones.size());
        assertEquals("WD-FDF-01", availablePhones.get(0).getSku());
    }
    @Test
    void shouldRetrieveAllTablets(){
        PageRequest pageRequest = PageRequest.of(0, 4);
        List<Product> allTablet = productRepository.findAllTablet(pageRequest).toList();
        assertEquals(2, allTablet.size());
    }

    @Test
    void shouldRetrieveAvailableTablets(){
        PageRequest pageRequest = PageRequest.of(0, 4);
        List<Product> availableTablet = productRepository.findAvailableTablet(pageRequest).toList();
        assertEquals(1, availableTablet.size());
        assertEquals("TB-SAM-01", availableTablet.get(0).getSku());
    }

    @Test
    void shouldRetrieveAllSamsung(){
        PageRequest pageRequest = PageRequest.of(0, 4);
        List<Product> allSamsung = productRepository.findByBrandIgnoreCase("Samsung",pageRequest).toList();
        assertEquals(2, allSamsung.size());
        assertTrue(allSamsung.stream().allMatch(p->"Samsung".equals(p.getBrand())));
    }

    @Test
    void shouldRetrieveAllApple(){
        PageRequest pageRequest = PageRequest.of(0, 4);
        List<Product> allApple = productRepository.findByBrandIgnoreCase("Apple",pageRequest).toList();
        assertEquals(2, allApple.size());
        assertTrue(allApple.stream().allMatch(p->"Apple".equals(p.getBrand())));
    }

    @Test
    void shouldRetrieveProductBySKU(){
        Optional<Product> product = productRepository.findBySku("AD-APP-01");
        assertTrue(product.isPresent());
        assertEquals("AD-APP-01", product.get().getSku());
    }

    @Test
    void shouldRetrieveProductByName(){
        Optional<Product> product = productRepository.findByNameIgnoreCase("iPad Pro");
        assertTrue(product.isPresent());
        assertEquals("iPad Pro", product.get().getName());
    }

    @Test
    void shouldRetrieveProductsByNameContaining(){
        PageRequest pageRequest = PageRequest.of(0, 4);
        List<Product> productList = productRepository.findByNameContainingIgnoreCase("SamSunG",pageRequest).toList();
        assertEquals(2, productList.size());
        assertTrue(productList.stream().anyMatch(p->"Samsung Galaxy Tab S9".equals(p.getName())));
    }

    @Test
    void shouldRetrieveAllApplePhones(){
        PageRequest pageRequest = PageRequest.of(0, 4);
        List<Product> SamsungPhones = productRepository.findByBrandIgnoreCaseAndCategory("Samsung", Product.Category.phone,pageRequest).toList();
        assertEquals(1, SamsungPhones.size());
        assertTrue(SamsungPhones.stream().allMatch(p->"Samsung".equals(p.getBrand())));
        assertTrue(SamsungPhones.stream().allMatch(p->"PHONE".equals(p.getCategory().name())));
    }

    @Test
    void shouldRetrieveProductById(){
        Product product = Product.builder()
                .sku("IP-APP-99")
                .name("iPhone 16 Pro")
                .brand("Apple")
                .os("IOS")
                .barcode("X003298232322")
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
                .build();

        Product savedProduct = productRepository.save(product);
        Optional<Product> retrievedProduct = productRepository.findById(savedProduct.getId());

        assertTrue(retrievedProduct.isPresent());
        assertEquals(savedProduct.getId(),retrievedProduct.get().getId());
    }

    @Test
    void shouldExistsBySKU(){
        boolean product = productRepository.existsBySku("TB-SAM-01");
        assertTrue(product);
    }
}