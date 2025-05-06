package com.devicehub.dto.response;

import com.devicehub.entity.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PhoneResponseDto extends ProductResponseDto {

    private Product product;
    private BigDecimal ultraWideCamMp;
    private String ipRating;
    private boolean hasEstim = false;
}
