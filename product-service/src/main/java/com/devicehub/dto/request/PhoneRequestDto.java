package com.devicehub.dto.request;

import com.devicehub.entity.Product;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class PhoneRequestDto {

    // common product fields
    private String sku;
    private String barcode;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private Product.Category category;
    private String os;
    private String chipset;
    private int ramGb;
    private int storageGb;
    private BigDecimal screenSizeInches;
    private int batteryMah;
    private int refreshRateHz;
    private BigDecimal mainCamMp;
    private BigDecimal frontCamMp;
    private boolean isActive;

    // phone specific fields
    private BigDecimal ultraWideCamMp;
    private String ipRating;
    private boolean hasEsim = false;
}
