package com.devicehub.controller;

import com.devicehub.constants.ProductConstants;
import com.devicehub.dto.request.PhoneRequestDto;
import com.devicehub.dto.request.TabletRequestDto;
import com.devicehub.dto.response.PhoneResponseDto;
import com.devicehub.dto.response.ProductListItemResponseDto;
import com.devicehub.dto.response.TabletResponseDto;
import com.devicehub.entity.Product;
import com.devicehub.service.IProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@RequiredArgsConstructor
class ProductControllerTest {

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
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IProductService productService;


    private PhoneRequestDto phoneRequestDto;
    private TabletRequestDto tabletRequestDto;

    @BeforeEach
    void setup() throws Exception {
        phoneRequestDto = PhoneRequestDto.builder()
                .sku("SKU123")
                .barcode("BAR123")
                .name("Galaxy S25")
                .brand("Samsung")
                .price(new java.math.BigDecimal("999.99"))
                .category(Product.Category.phone)
                .os("Android")
                .chipset("Exynos")
                .ramGb(8)
                .storageGb(128)
                .screenSizeInches(new java.math.BigDecimal("6.5"))
                .batteryMah(5000)
                .refreshRateHz(120)
                .mainCamMp(new java.math.BigDecimal("108"))
                .frontCamMp(new java.math.BigDecimal("32"))
                .ultraWideCamMp(new java.math.BigDecimal("12"))
                .ipRating("IP68")
                .hasEsim(true)
                .isActive(true)
                .build();

        tabletRequestDto = TabletRequestDto.builder()
                .sku("TAB123")
                .currentSku("TAB123-CURR")
                .barcode("0123456789123")
                .name("Galaxy Tab S9")
                .brand("Samsung")
                .description("High-end Samsung tablet")
                .price(new BigDecimal("899.99"))
                .category(Product.Category.tablet)
                .os("Android 13")
                .chipset("Snapdragon 8 Gen 2")
                .ramGb(8)
                .storageGb(256)
                .screenSizeInches(new BigDecimal("11.0"))
                .batteryMah(10090)
                .refreshRateHz(120)
                .mainCamMp(new BigDecimal("13.0"))
                .frontCamMp(new BigDecimal("12.0"))
                .build();

        mockMvc.perform(post("/api/v1/product/phone/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(phoneRequestDto)));

        mockMvc.perform(post("/api/v1/product/tablet/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tabletRequestDto)));
    }


    private TabletRequestDto getSampleTablet() {
        return TabletRequestDto.builder()
                .sku("TAB123")
                .name("Galaxy Tab")
                .brand("Samsung")
                .barcode("123456789")
                .category(Product.Category.tablet)
                .price(BigDecimal.valueOf(299.99))
                .os("Android")
                .chipset("Exynos")
                .ramGb(4)
                .storageGb(64)
                .screenSizeInches(BigDecimal.valueOf(10.5))
                .batteryMah(7040)
                .refreshRateHz(60)
                .mainCamMp(BigDecimal.valueOf(13))
                .frontCamMp(BigDecimal.valueOf(8))
                .build();
    }

    private PhoneRequestDto getSamplePhone() {
        return PhoneRequestDto.builder()
                .sku("PHN123")
                .name("Galaxy S21")
                .brand("Samsung")
                .barcode("987654321")
                .category(Product.Category.phone)
                .price(BigDecimal.valueOf(499.99))
                .os("Android")
                .chipset("Exynos")
                .ramGb(8)
                .storageGb(128)
                .screenSizeInches(BigDecimal.valueOf(6.2))
                .batteryMah(4000)
                .refreshRateHz(120)
                .mainCamMp(BigDecimal.valueOf(64))
                .frontCamMp(BigDecimal.valueOf(10))
                .build();
    }



    @Test
    void savePhone_shouldReturn201() throws Exception {
        mockMvc.perform(post("/api/v1/product/phone/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getSamplePhone())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(ProductConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(ProductConstants.PHONE_SAVED_MSG));
    }

    @Test
    void saveTablet_shouldReturn201() throws Exception {
        mockMvc.perform(post("/api/v1/product/tablet/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getSampleTablet())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(ProductConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(ProductConstants.TABLET_SAVED_MSG));
    }


    @Test
    void testUpdateTablet() throws Exception {
        tabletRequestDto.setCurrentSku(tabletRequestDto.getSku());
        tabletRequestDto.setName("Galaxy Tab S9 A");
        mockMvc.perform(put("/api/v1/product/tablet/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tabletRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(ProductConstants.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(ProductConstants.TABLET_UPDATE_MSG));
    }

    @Test
    void testUpdatePhone() throws Exception {
        phoneRequestDto.setCurrentSku(phoneRequestDto.getSku());
        phoneRequestDto.setName("Galaxy S25 A");
        mockMvc.perform(put("/api/v1/product/phone/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(phoneRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(ProductConstants.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(ProductConstants.PHONE_UPDATE_MSG));
    }

    @Test
    void testUpdateAvailability() throws Exception {
        mockMvc.perform(put("/api/v1/product/update/availability")
                        .param("sku", "SKU123")
                        .param("isAvailable", "true"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(ProductConstants.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(ProductConstants.UPDATE_AVAILABILITY_MSG));
    }

    @Test
    void testFetchProductBySku() throws Exception {
//        ProductListItemResponseDto dto = ProductListItemResponseDto.builder().build();
//        Mockito.when(productService.fetchProductBySku("SKU23")).thenReturn(dto);

        mockMvc.perform(get("/api/v1/product/fetch/sku/SKU123"))
                .andExpect(status().isOk());
    }

    @Test
    void testExistsBySku() throws Exception {
//        Mockito.when(productService.existBySku("SKU123")).thenReturn(true);

        mockMvc.perform(get("/api/v1/product/exists/SKU123"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void testFetchAllPhones() throws Exception {
        PhoneResponseDto phoneDto = new PhoneResponseDto(); // Make sure it has valid mock values
        Page<PhoneResponseDto> page = new PageImpl<>(List.of(phoneDto));

        Mockito.when(productService.fetchAllPhones(0, 10, "price")).thenReturn(page);

        mockMvc.perform(get("/api/v1/product/phone/all/fetch?page=0&size=10&field=price"))
                .andExpect(status().isOk());
    }
    @Test
    void testFetchAllProducts() throws Exception {
        Page<ProductListItemResponseDto> page = new PageImpl<>(Collections.emptyList());
        Mockito.when(productService.fetchAllProducts(0, 2, "category")).thenReturn(page);

        mockMvc.perform(get("/api/v1/product/all/fetch?page=0&size=2&field=category"))
                .andExpect(status().isOk());
    }
    @Test
    void testFetchAvailableProducts() throws Exception {
        Page<ProductListItemResponseDto> page = new PageImpl<>(Collections.emptyList());
        Mockito.when(productService.fetchAvailableProducts(0, 10, "category")).thenReturn(page);

        mockMvc.perform(get("/api/v1/product/available/fetch?page=0&size=10&field=category"))
                .andExpect(status().isOk());
    }

//    @Test
//    void testFetchAllPhones() throws Exception {
//        Page<PhoneResponseDto> page = new PageImpl<>(Collections.emptyList());
//        Mockito.when(productService.fetchAllPhones(0, 10, "price")).thenReturn(page);
//
//        mockMvc.perform(get("/api/v1/product/phone/all/fetch?page=0&size=10&field=price"))
//                .andExpect(status().isOk());
//    }

    @Test
    void testFetchAvailablePhones() throws Exception {
        Page<PhoneResponseDto> page = new PageImpl<>(Collections.emptyList());
        Mockito.when(productService.fetchAvailablePhones(0, 10, "price")).thenReturn(page);

        mockMvc.perform(get("/api/v1/product/phone/available/fetch?page=0&size=10&field=price"))
                .andExpect(status().isOk());
    }

    @Test
    void testFetchAllTablets() throws Exception {
        Page<TabletResponseDto> page = new PageImpl<>(Collections.emptyList());
        Mockito.when(productService.fetchAllTablet(0, 10, "price")).thenReturn(page);

        mockMvc.perform(get("/api/v1/product/tablet/all/fetch?page=0&size=10&field=price"))
                .andExpect(status().isOk());
    }

    @Test
    void testFetchAvailableTablets() throws Exception {
        Page<TabletResponseDto> page = new PageImpl<>(Collections.emptyList());
        Mockito.when(productService.fetchAvailableTablet(0, 10, "price")).thenReturn(page);

        mockMvc.perform(get("/api/v1/product/tablet/available/fetch?page=0&size=10&field=price"))
                .andExpect(status().isOk());
    }

    @Test
    void testFetchProductByBrand() throws Exception {
        Page<ProductListItemResponseDto> page = new PageImpl<>(Collections.emptyList());
        Mockito.when(productService.fetchProductByBrand("Apple", 0, 10, "price")).thenReturn(page);

        mockMvc.perform(get("/api/v1/product/fetch/brand/Apple?page=0&size=10&field=price"))
                .andExpect(status().isOk());
    }

    @Test
    void testFetchProductByBrandAndCat() throws Exception {
        Page<ProductListItemResponseDto> page = new PageImpl<>(Collections.emptyList());
        Mockito.when(productService.fetchProductByBrandAndCat("Apple", Product.Category.phone, 0, 10, "price")).thenReturn(page);

        mockMvc.perform(get("/api/v1/product/fetch/brand/Apple/category/phone?page=0&size=10&field=price"))
                .andExpect(status().isOk());
    }

    @Test
    void testFetchProductById() throws Exception {
        ProductListItemResponseDto dto = ProductListItemResponseDto.builder().build();
        UUID id = UUID.randomUUID();
        Mockito.when(productService.fetchProductById(id)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/product/fetch/id/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void testFetchProductByName() throws Exception {
        ProductListItemResponseDto dto = ProductListItemResponseDto.builder().build();
        Mockito.when(productService.fetchProductByName("iPhone"))
                .thenReturn(dto);

        mockMvc.perform(get("/api/v1/product/fetch/name/iPhone"))
                .andExpect(status().isOk());
    }

    @Test
    void testFetchProductByKeyword() throws Exception {
        Page<ProductListItemResponseDto> page = new PageImpl<>(Collections.emptyList());
        Mockito.when(productService.fetchProductByKeyword("iPhone", 0, 10, "price")).thenReturn(page);

        mockMvc.perform(get("/api/v1/product/fetch/iPhone?page=0&size=10&field=price"))
                .andExpect(status().isOk());
    }
//    @Test
//    void fetchAllProducts() {
//    }
//
//    @Test
//    void fetchAvailableProducts() {
//    }
//
//    @Test
//    void fetchAllPhones() {
//    }
//
//    @Test
//    void fetchAvailablePhones() {
//    }
//
//    @Test
//    void fetchAllTablets() {
//    }
//
//    @Test
//    void fetchAvailableTablets() {
//    }
//
//    @Test
//    void fetchProductByBrand() {
//    }
//
//    @Test
//    void fetchProductByBrandAndCat() {
//    }
//
//    @Test
//    void fetchProductById() {
//    }
//
//    @Test
//    void fetchProductBySku() {
//    }
//
//    @Test
//    void fetchProductByName() {
//    }
//
//    @Test
//    void fetchProductByKeyword() {
//    }
//
//    @Test
//    void existsBySku() {
//    }
}