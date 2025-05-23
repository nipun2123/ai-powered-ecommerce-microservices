package com.devicehub.service;

import com.devicehub.dto.request.PhoneRequestDto;
import com.devicehub.dto.request.TabletRequestDto;
import com.devicehub.dto.response.PhoneResponseDto;
import com.devicehub.dto.response.ProductListItemResponseDto;
import com.devicehub.dto.response.ProductResponseDto;
import com.devicehub.dto.response.TabletResponseDto;
import com.devicehub.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IProductService {

    /**
     * @Param phoneRequestDto - PhoneRequestDto
     */
    void savePhone(PhoneRequestDto phoneRequestDto);

    /**
     * @Param tabletRequestDto - TabletRequestDto
     */
    void saveTablet(TabletRequestDto tabletRequestDto);

    /**
     * @Param phoneRequestDto - PhoneRequestDto
     */
    void updatePhone(PhoneRequestDto phoneRequestDto);

    /**
     * @Param tabletRequestDto - TabletRequestDto
     */
    void updateTablet(TabletRequestDto tabletRequestDto);

    /**
     * @Param tabletRequestDto - TabletRequestDto
     */
    void updateAvailability(String sku, boolean isAvailable);

    /**
     * @Return List of all products
     */
    Page<ProductListItemResponseDto> fetchAllProducts(int page, int size, String field);

    /**
     * @Return List of all available products
     */
    Page<ProductListItemResponseDto> fetchAvailableProducts(int page, int size, String field);

    /**
     * @Return List of all phones
     */
    Page<PhoneResponseDto> fetchAllPhones(int page, int size, String field);

    /**
     * @Return List of all available the phones
     */
    Page<PhoneResponseDto> fetchAvailablePhones(int page, int size, String field);

    /**
     * @Return List of all tablet
     */
    Page<TabletResponseDto> fetchAllTablet(int page, int size, String field);

    /**
     * @Return List of all available the tablet
     */
    Page<TabletResponseDto> fetchAvailableTablet(int page, int size, String field);

    /**
     * @Param brand - Input brand name
     * @Return List of product based on brand
     */
    Page<ProductListItemResponseDto> fetchProductByBrand(String brand, int page, int size, String field);

    /**
     * @Param brand - Input brand name
     * @Param category - Input category: Phone or Tablet
     * @Return List of product based on brand and category
     */
    Page<ProductListItemResponseDto> fetchProductByBrandAndCat(String brand, Product.Category category, int page, int size, String field);

    /**
     * @Param id - Input id of the product
     * @Return A product based on product id
     */
    ProductListItemResponseDto fetchProductById(UUID id);

    /**
     * @Param sku - Input sku name
     * @Return A product based on sku name
     */
    ProductListItemResponseDto fetchProductBySku(String sku);

    /**
     * @Param name - Input name of the product
     * @Return A product based on product name
     */
    ProductListItemResponseDto fetchProductByName(String name);

    /**
     * @Param keyword - Input search keyword
     * @Return List of product based on keyword
     */
    Page<ProductListItemResponseDto> fetchProductByKeyword(String keyword, int page, int size, String field);

    /**
     * @Param sku - Input sku name
     * @Return product existence based on sku name
     */
    boolean existBySku(String sku);


}
