package com.devicehub.controller;


import com.devicehub.constants.ProductConstants;
import com.devicehub.dto.request.PhoneRequestDto;
import com.devicehub.dto.request.TabletRequestDto;
import com.devicehub.dto.response.*;
import com.devicehub.entity.Product;
import com.devicehub.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/product", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class ProductController {


    private IProductService productService;

    @PostMapping("/phone/save")
    public ResponseEntity<ResponseDto> savePhone(@RequestBody PhoneRequestDto phoneRequestDto){

        productService.savePhone(phoneRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ProductConstants.STATUS_201, ProductConstants.PHONE_SAVED_MSG));
    }

    @PostMapping("/tablet/save")
    public ResponseEntity<ResponseDto> saveTablet(@RequestBody TabletRequestDto tabletRequestDto){

        productService.saveTablet(tabletRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ProductConstants.STATUS_201, ProductConstants.TABLET_SAVED_MSG));
    }

    @PutMapping("/phone/update")
    public ResponseEntity<ResponseDto> updatePhone(@RequestBody PhoneRequestDto phoneRequestDto){

        boolean isUpdated = productService.updatePhone(phoneRequestDto);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(ProductConstants.STATUS_200, ProductConstants.PHONE_UPDATE_MSG));
        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductConstants.STATUS_500, ProductConstants.MESSAGE_500));
        }
    }

    @PutMapping("/tablet/update")
    public ResponseEntity<ResponseDto> updateTablet(@RequestBody TabletRequestDto tabletRequestDto){

        boolean isUpdated = productService.updateTablet(tabletRequestDto);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(ProductConstants.STATUS_200, ProductConstants.TABLET_UPDATE_MSG));
        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductConstants.STATUS_500, ProductConstants.MESSAGE_500));
        }
    }

    @PutMapping("/update/availability")
    public ResponseEntity<ResponseDto> updateAvailability(@RequestParam String sku, @RequestParam boolean isAvailable){

        boolean isUpdated = productService.updateAvailability(sku,isAvailable);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(ProductConstants.STATUS_200, ProductConstants.UPDATE_AVAILABILITY_MSG));
        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductConstants.STATUS_500, ProductConstants.MESSAGE_500));
        }
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
    public ResponseEntity<Page<ProductListItemResponseDto>> fetchProductByBrand(@PathVariable String brand, @RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "price") String field){
        Page<ProductListItemResponseDto> products = productService.fetchProductByBrand(brand,page,size,field);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/fetch/brand/{brand}/category/{category}")
    public ResponseEntity<Page<ProductListItemResponseDto>> fetchProductByBrandAndCat(@PathVariable String brand, @PathVariable Product.Category category, @RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "price") String field){
        Page<ProductListItemResponseDto> products = productService.fetchProductByBrandAndCat(brand, category,page,size,field);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/fetch/id/{id}")
    public ResponseEntity<ProductListItemResponseDto> fetchProductById(@PathVariable UUID id){
        ProductListItemResponseDto product = productService.fetchProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/fetch/sku/{sku}")
    public ResponseEntity<ProductListItemResponseDto> fetchProductBySku(@PathVariable String sku){
        ProductListItemResponseDto product = productService.fetchProductBySku(sku);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/fetch/name/{name}")
    public ResponseEntity<ProductListItemResponseDto> fetchProductByName(@PathVariable String name){
        ProductListItemResponseDto product = productService.fetchProductByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/fetch/{keyword}")
    public ResponseEntity<Page<ProductListItemResponseDto>> fetchProductByKeyword(@PathVariable String keyword, @RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "price") String field){
        Page<ProductListItemResponseDto> products = productService.fetchProductByKeyword(keyword,page,size,field);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/exists/{sku}")
    public ResponseEntity<Boolean> existsBySku(@PathVariable String sku){
        Boolean products = productService.existBySku(sku);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

}

