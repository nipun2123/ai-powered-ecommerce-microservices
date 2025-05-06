package com.devicehub.dto.response;

import com.devicehub.entity.Product;
import lombok.Data;

@Data
public class TabletResponseDto extends ProductResponseDto {

    private Product product;
    private boolean hasCellular = false;
    private boolean stylusSupported;
    private boolean keyboardSupport;
}
