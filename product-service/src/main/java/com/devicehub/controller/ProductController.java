package com.devicehub.controller;


import com.devicehub.constants.ProductConstants;
import com.devicehub.dto.request.PhoneRequestDto;
import com.devicehub.dto.request.TabletRequestDto;
import com.devicehub.dto.response.*;
import com.devicehub.entity.Product;
import com.devicehub.service.IProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/product", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class ProductController {


    private IProductService productService;

    @PostMapping("/phone/save")
    public ResponseEntity<ResponseDto> savePhone(@Valid @RequestBody PhoneRequestDto phoneRequestDto){

        productService.savePhone(phoneRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ProductConstants.STATUS_201, ProductConstants.PHONE_SAVED_MSG));
    }

    @PostMapping("/tablet/save")
    public ResponseEntity<ResponseDto> saveTablet(@Valid @RequestBody TabletRequestDto tabletRequestDto){

        productService.saveTablet(tabletRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ProductConstants.STATUS_201, ProductConstants.TABLET_SAVED_MSG));
    }

    @PutMapping("/phone/update")
    public ResponseEntity<ResponseDto> updatePhone(@Valid @RequestBody PhoneRequestDto phoneRequestDto){

        productService.updatePhone(phoneRequestDto);
        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(ProductConstants.STATUS_200, ProductConstants.PHONE_UPDATE_MSG));

    }

    @PutMapping("/tablet/update")
    public ResponseEntity<ResponseDto> updateTablet(@Valid @RequestBody TabletRequestDto tabletRequestDto){

        productService.updateTablet(tabletRequestDto);
        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(ProductConstants.STATUS_200, ProductConstants.TABLET_UPDATE_MSG));
    }

    @PutMapping("/update/availability")
    public ResponseEntity<ResponseDto> updateAvailability(@RequestParam
                                                              @Size(min = 3, max = 30, message = "SKU must be between 3 and 30 characters")
                                                              String sku, @RequestParam boolean isAvailable){

        productService.updateAvailability(sku,isAvailable);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(ProductConstants.STATUS_200, ProductConstants.UPDATE_AVAILABILITY_MSG));

    }

    @GetMapping("/all/fetch")
    public ResponseEntity<Page<ProductListItemResponseDto>> fetchAllProducts(@RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "category") String field){
        Page<ProductListItemResponseDto> allProduct = productService.fetchAllProducts(page,size,field);
        return ResponseEntity.status(HttpStatus.OK).body(allProduct);
    }

    @GetMapping("/available/fetch")
    public ResponseEntity<Page<ProductListItemResponseDto>> fetchAvailableProducts(@RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "category") String field){
        Page<ProductListItemResponseDto> availableProduct = productService.fetchAvailableProducts(page,size,field);
        return ResponseEntity.status(HttpStatus.OK).body(availableProduct);
    }

    @GetMapping("/phone/all/fetch")
    public ResponseEntity<Page<PhoneResponseDto>> fetchAllPhones(@RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "price") String field){
        Page<PhoneResponseDto> phoneResponseDtos = productService.fetchAllPhones(page,size,field);
        return ResponseEntity.status(HttpStatus.OK).body(phoneResponseDtos);
    }

    @GetMapping("/phone/available/fetch")
    public ResponseEntity<Page<PhoneResponseDto>> fetchAvailablePhones(@RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "price") String field){
        Page<PhoneResponseDto> phoneResponseDtos = productService.fetchAvailablePhones(page,size,field);
        return ResponseEntity.status(HttpStatus.OK).body(phoneResponseDtos);
    }

    @GetMapping("/tablet/all/fetch")
    public ResponseEntity<Page<TabletResponseDto>> fetchAllTablets(@RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "price") String field){
        Page<TabletResponseDto> tabletResponseDtos = productService.fetchAllTablet(page,size,field);
        return ResponseEntity.status(HttpStatus.OK).body(tabletResponseDtos);
    }

    @GetMapping("/tablet/available/fetch")
    public ResponseEntity<Page<TabletResponseDto>> fetchAvailableTablets(@RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "price") String field){
        Page<TabletResponseDto> tabletResponseDtos = productService.fetchAvailableTablet(page,size,field);
        return ResponseEntity.status(HttpStatus.OK).body(tabletResponseDtos);
    }

    @GetMapping("/fetch/brand/{brand}")
    public ResponseEntity<Page<ProductListItemResponseDto>> fetchProductByBrand(@PathVariable
                                                                                    @Size(min = 2, max = 30, message = "Brand must be between 2 and 30 characters")
                                                                                    String brand, @RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "price") String field){
        Page<ProductListItemResponseDto> products = productService.fetchProductByBrand(brand,page,size,field);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/fetch/brand/{brand}/category/{category}")
    public ResponseEntity<Page<ProductListItemResponseDto>> fetchProductByBrandAndCat(@PathVariable
                                                                                          @Size(min = 2, max = 30, message = "Brand must be between 2 and 30 characters")
                                                                                          String brand, @PathVariable Product.Category category, @RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "price") String field){
        Page<ProductListItemResponseDto> products = productService.fetchProductByBrandAndCat(brand, category,page,size,field);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/fetch/id/{id}")
    public ResponseEntity<ProductListItemResponseDto> fetchProductById(@PathVariable UUID id){
        ProductListItemResponseDto product = productService.fetchProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/fetch/sku/{sku}")
    public ResponseEntity<ProductListItemResponseDto> fetchProductBySku(@PathVariable
                                                                            @Size(min = 3, max = 30, message = "SKU must be between 3 and 30 characters")
                                                                            String sku){
        ProductListItemResponseDto product = productService.fetchProductBySku(sku);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/fetch/name/{name}")
    public ResponseEntity<ProductListItemResponseDto> fetchProductByName(@PathVariable
                                                                             @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
                                                                             String name){
        ProductListItemResponseDto product = productService.fetchProductByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/fetch/{keyword}")
    public ResponseEntity<Page<ProductListItemResponseDto>> fetchProductByKeyword(@PathVariable String keyword, @RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "price") String field){
        Page<ProductListItemResponseDto> products = productService.fetchProductByKeyword(keyword,page,size,field);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/exists/{sku}")
    public ResponseEntity<Boolean> existsBySku(@PathVariable
                                                   @Size(min = 3, max = 30, message = "SKU must be between 3 and 30 characters")
                                                   String sku){
        Boolean products = productService.existBySku(sku);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

}

