package com.devicehub.dto.response;

import com.devicehub.entity.Product;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneResponseDto extends ProductResponseDto {

    private Product product;
    private BigDecimal ultraWideCamMp;
    private String ipRating;
    private boolean hasEsim = false;

    @Builder
    public PhoneResponseDto(String sku, String barcode, String name, String brand, String description, BigDecimal price, Product.Category category, String os, String chipset, int ramGb, int storageGb, BigDecimal screenSizeInches, int batteryMah, int refreshRateHz, BigDecimal mainCamMp, BigDecimal frontCamMp, boolean isActive, Product product, BigDecimal ultraWideCamMp, String ipRating, boolean hasEsim) {
        super(sku, barcode, name, brand, description, price, category, os, chipset, ramGb, storageGb, screenSizeInches, batteryMah, refreshRateHz, mainCamMp, frontCamMp, isActive);
        this.product = product;
        this.ultraWideCamMp = ultraWideCamMp;
        this.ipRating = ipRating;
        this.hasEsim = hasEsim;
    }


}
