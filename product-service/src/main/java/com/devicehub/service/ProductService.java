package com.devicehub.service;

import com.devicehub.dto.request.PhoneRequestDto;
import com.devicehub.dto.request.TabletRequestDto;
import com.devicehub.dto.response.PhoneResponseDto;
import com.devicehub.dto.response.ProductListItemResponseDto;
import com.devicehub.dto.response.ProductResponseDto;
import com.devicehub.dto.response.TabletResponseDto;
import com.devicehub.entity.Product;
import com.devicehub.mapper.ProductMapper;
import com.devicehub.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ProductService implements IProductService{

    private ProductRepository productRepository;

    @Override
    public void savePhone(PhoneRequestDto phoneRequestDto) {
        productRepository.save(ProductMapper.phoneRequestDtoToProduct(phoneRequestDto));
    }

    @Override
    public void saveTablet(TabletRequestDto tabletRequestDto) {
        productRepository.save(ProductMapper.tabletRequestDtoToProduct(tabletRequestDto));
    }

    @Override
    public boolean updatePhone(PhoneRequestDto phoneRequestDto) {
        boolean isUpdated = false;
        Optional<Product> productbySku = productRepository.findBySku(phoneRequestDto.getCurrentSku());

        if(productbySku.isPresent()){
            Product product = ProductMapper.phoneRequestDtoToProduct(phoneRequestDto);
            product.setId(productbySku.get().getId());
            product.getPhoneSpec().setId(productbySku.get().getPhoneSpec().getId());
            productRepository.save(product);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean updateTablet(TabletRequestDto tabletRequestDto) {
        boolean isUpdated = false;
        Optional<Product> productbySku = productRepository.findBySku(tabletRequestDto.getCurrentSku());

        if(productbySku.isPresent()){
            Product product = ProductMapper.tabletRequestDtoToProduct(tabletRequestDto);
            product.setId(productbySku.get().getId());
            product.getTabletSpec().setId(productbySku.get().getTabletSpec().getId());
            productRepository.save(product);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean updateAvailability(String sku, boolean isAvailable) {
        boolean isUpdated = false;
        Optional<Product> productbySku = productRepository.findBySku(sku);

        if(productbySku.isPresent()){
            productbySku.get().setActive(isAvailable);
            productRepository.save(productbySku.get());
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public Page<ProductListItemResponseDto> fetchAllProducts(int page, int size,String field) {
        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by(field));
        Page<ProductListItemResponseDto> allProducts = productRepository.findAll(pageRequest).map(ProductMapper::productToProductListItemResponseDto);
        return allProducts;
    }

    @Override
    public Page<ProductListItemResponseDto> fetchAvailableProducts(int page, int size,String field) {
        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by(field));
        Page<ProductListItemResponseDto> availableProducts = productRepository.findAvailableProducts(pageRequest).map(ProductMapper::productToProductListItemResponseDto);
        return availableProducts;
    }

    @Override
    public  Page<PhoneResponseDto> fetchAllPhones(int page, int size,String field) {
        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by(field));
        Page<PhoneResponseDto> allPhones = productRepository.findAllPhones(pageRequest).map(ProductMapper::productToPhoneResponseDto);
        return allPhones;
    }

    @Override
    public Page<PhoneResponseDto> fetchAvailablePhones(int page, int size,String field) {
        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by(field));
        Page<PhoneResponseDto> availablePhones = productRepository.findAvailablePhones(pageRequest).map(ProductMapper::productToPhoneResponseDto);
        return availablePhones;
    }

    @Override
    public Page<TabletResponseDto> fetchAllTablet(int page, int size,String field) {
        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by(field));
        Page<TabletResponseDto> allTablets = productRepository.findAllTablet(pageRequest).map(ProductMapper::productToTabletResponseDto);
        return allTablets;
    }

    @Override
    public Page<TabletResponseDto> fetchAvailableTablet(int page, int size,String field) {
        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by(field));
        Page<TabletResponseDto> availableTablets = productRepository.findAvailableTablet(pageRequest).map(ProductMapper::productToTabletResponseDto);
        return availableTablets;
    }

    @Override
    public Page<ProductListItemResponseDto> fetchProductByBrand(String brand, int page, int size,String field) {
        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by(field));
        Page<ProductListItemResponseDto> products = productRepository.findByBrandIgnoreCase(brand,pageRequest).map(ProductMapper::productToProductListItemResponseDto);
        return products;
    }

    @Override
    public Page<ProductListItemResponseDto> fetchProductByBrandAndCat(String brand, Product.Category category, int page, int size,String field) {
        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by(field));
        Page<ProductListItemResponseDto> products = productRepository.findByBrandIgnoreCaseAndCategory(brand, category, pageRequest).map(ProductMapper::productToProductListItemResponseDto);
        return products;
    }

    @Override
    public ProductListItemResponseDto fetchProductById(UUID id) {
        Optional<Product> productById = productRepository.findById(id);
        ProductListItemResponseDto product = null;
        if (productById.isPresent()){
            product = ProductMapper.productToProductListItemResponseDto(productById.get());
        }
        return product;
    }

    @Override
    public ProductListItemResponseDto fetchProductBySku(String sku) {
        Optional<Product> productBySku = productRepository.findBySku(sku);
        ProductListItemResponseDto product = null;
        if (productBySku.isPresent()){
        product = ProductMapper.productToProductListItemResponseDto(productBySku.get());
        }
        return product;
    }

    @Override
    public ProductListItemResponseDto fetchProductByName(String name) {
        Optional<Product> productByName = productRepository.findByNameIgnoreCase(name);
        ProductListItemResponseDto product = null;
        if (productByName.isPresent()){
            product = ProductMapper.productToProductListItemResponseDto(productByName.get());
        }
        return product;
    }

    @Override
    public Page<ProductListItemResponseDto> fetchProductByKeyword(String keyword, int page, int size,String field) {
        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by(field));
        Page<ProductListItemResponseDto> products = productRepository.findByNameContainingIgnoreCase(keyword, pageRequest).map(ProductMapper::productToProductListItemResponseDto);
        return products;
    }

    @Override
    public boolean existBySku(String sku) {
        boolean productExist = productRepository.existsBySku(sku);
        return productExist;
    }

}
