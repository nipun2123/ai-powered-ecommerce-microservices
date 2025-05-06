package com.devicehub.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TabletRequestDto {

    public enum ProductCategory {
        PHONE, TABLET
    }
    // common product fields
    private String sku;
    private String barcode;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private ProductCategory category;
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

    // tablet specific fields
    private boolean hasCellular = false;
    private boolean stylusSupported;
    private boolean keyboardSupport;
}
