package com.devicehub.dto.response;

import com.devicehub.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabletResponseDto extends ProductResponseDto {

    private Product product;
    private boolean hasCellular = false;
    private boolean stylusSupported;
    private boolean keyboardSupport;

    @Builder
    public TabletResponseDto(String sku, String barcode, String name, String brand, String description, BigDecimal price, Product.Category category, String os, String chipset, int ramGb, int storageGb, BigDecimal screenSizeInches, int batteryMah, int refreshRateHz, BigDecimal mainCamMp, BigDecimal frontCamMp, boolean isActive, Product product, boolean hasCellular, boolean stylusSupported, boolean keyboardSupport) {
        super(sku, barcode, name, brand, description, price, category, os, chipset, ramGb, storageGb, screenSizeInches, batteryMah, refreshRateHz, mainCamMp, frontCamMp, isActive);
        this.product = product;
        this.hasCellular = hasCellular;
        this.stylusSupported = stylusSupported;
        this.keyboardSupport = keyboardSupport;
    }

}
