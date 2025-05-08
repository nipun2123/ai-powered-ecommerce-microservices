package com.devicehub.controller;


import com.devicehub.constants.ProductConstants;
import com.devicehub.dto.request.PhoneRequestDto;
import com.devicehub.dto.request.TabletRequestDto;
import com.devicehub.dto.response.ResponseDto;
import com.devicehub.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
