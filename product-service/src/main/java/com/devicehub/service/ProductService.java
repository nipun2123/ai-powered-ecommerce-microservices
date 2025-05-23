package com.devicehub.service;

import com.devicehub.dto.request.PhoneRequestDto;
import com.devicehub.dto.request.TabletRequestDto;
import com.devicehub.dto.response.PhoneResponseDto;
import com.devicehub.dto.response.ProductListItemResponseDto;
import com.devicehub.dto.response.TabletResponseDto;
import com.devicehub.entity.Product;
import com.devicehub.expection.ResourceNotFoundException;
import com.devicehub.expection.SkuAlreadyExistsException;
import com.devicehub.mapper.ProductMapper;
import com.devicehub.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ProductService implements IProductService{

    private ProductRepository productRepository;

    @Override
    public void savePhone(PhoneRequestDto phoneRequestDto) {
        Optional<Product> productBySku = productRepository.findBySkuIgnoreCase(phoneRequestDto.getSku());
        if(productBySku.isPresent()){
            throw new SkuAlreadyExistsException("The product of "+productBySku.get().getName() +" already saved with this SKU: "+productBySku.get().getSku());
        }

        Optional<Product> productByBarcode = productRepository.findByBarcode(phoneRequestDto.getBarcode());
        if(productByBarcode.isPresent()){
            throw new SkuAlreadyExistsException("The product of "+productByBarcode.get().getName() +" already saved with this barcode: "+productByBarcode.get().getBarcode());
        }
        productRepository.save(ProductMapper.phoneRequestDtoToProduct(phoneRequestDto));
    }

    @Override
    public void saveTablet(TabletRequestDto tabletRequestDto) {
        Optional<Product> productBySku = productRepository.findBySkuIgnoreCase(tabletRequestDto.getSku());
        if(productBySku.isPresent()){
            throw new SkuAlreadyExistsException("The product of "+productBySku.get().getName() +" already saved with this SKU: "+productBySku.get().getSku());
        }

        Optional<Product> productByBarcode = productRepository.findByBarcode(tabletRequestDto.getBarcode());
        if(productByBarcode.isPresent()){
            throw new SkuAlreadyExistsException("The product of "+productByBarcode.get().getName() +" already saved with this barcode: "+productByBarcode.get().getBarcode());
        }
        productRepository.save(ProductMapper.tabletRequestDtoToProduct(tabletRequestDto));
    }

    @Override
    public void updatePhone(PhoneRequestDto phoneRequestDto) {
        Product oldProduct = productRepository.findBySkuIgnoreCase(phoneRequestDto.getCurrentSku()).orElseThrow(() -> new ResourceNotFoundException("Product", "SKU", phoneRequestDto.getCurrentSku()));;

        Optional<Product> productBySku = productRepository.findBySkuIgnoreCase(phoneRequestDto.getSku());
        if(productBySku.isPresent() && !oldProduct.getSku().equals(phoneRequestDto.getSku())){
            throw new SkuAlreadyExistsException("The product of "+productBySku.get().getName() +" already saved with this SKU: "+productBySku.get().getSku());
        }

        Optional<Product> productByBarcode = productRepository.findByBarcode(phoneRequestDto.getBarcode());
        if(productByBarcode.isPresent() && !oldProduct.getBarcode().equals(phoneRequestDto.getBarcode())){
            throw new SkuAlreadyExistsException("The product of "+productByBarcode.get().getName() +" already saved with this barcode: "+productByBarcode.get().getBarcode());
        }
        Product product = ProductMapper.phoneRequestDtoToProduct(phoneRequestDto);
        product.setId(oldProduct.getId());
        product.getPhoneSpec().setId(oldProduct.getPhoneSpec().getId());
        productRepository.save(product);
    }

    @Override
    public void updateTablet(TabletRequestDto tabletRequestDto) {

        Product oldProduct = productRepository.findBySkuIgnoreCase(tabletRequestDto.getCurrentSku()).orElseThrow(() -> new ResourceNotFoundException("Product", "SKU", tabletRequestDto.getCurrentSku()));;

        Optional<Product> productBySku = productRepository.findBySkuIgnoreCase(tabletRequestDto.getSku());
        if(productBySku.isPresent() && !oldProduct.getSku().equals(tabletRequestDto.getSku())){
            throw new SkuAlreadyExistsException("The product of "+productBySku.get().getName() +" already saved with this SKU: "+productBySku.get().getSku());
        }

        Optional<Product> productByBarcode = productRepository.findByBarcode(tabletRequestDto.getBarcode());
        if(productByBarcode.isPresent()  && !oldProduct.getBarcode().equals(tabletRequestDto.getBarcode())){
            throw new SkuAlreadyExistsException("The product of "+productByBarcode.get().getName() +" already saved with this barcode: "+productByBarcode.get().getBarcode());
        }

        Product product = ProductMapper.tabletRequestDtoToProduct(tabletRequestDto);
        product.setId(oldProduct.getId());
        product.getTabletSpec().setId(oldProduct.getTabletSpec().getId());
        productRepository.save(product);
    }

    @Override
    public void updateAvailability(String sku, boolean isAvailable) {
        Product productbySku = productRepository.findBySkuIgnoreCase(sku).orElseThrow(() -> new ResourceNotFoundException("Product", "SKU", sku));
        productbySku.setActive(isAvailable);
        productRepository.save(productbySku);
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
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "Id", String.valueOf(id)));
        return ProductMapper.productToProductListItemResponseDto(product);
    }

    @Override
    public ProductListItemResponseDto fetchProductBySku(String sku) {
        Product product = productRepository.findBySkuIgnoreCase(sku).orElseThrow(() -> new ResourceNotFoundException("Product", "SKU", sku));
        return ProductMapper.productToProductListItemResponseDto(product);
    }

    @Override
    public ProductListItemResponseDto fetchProductByName(String name) {
        Product product = productRepository.findByNameIgnoreCase(name).orElseThrow(() -> new ResourceNotFoundException("Product", "Name", name));
        return ProductMapper.productToProductListItemResponseDto(product);
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
