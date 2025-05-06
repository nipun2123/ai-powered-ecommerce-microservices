package com.devicehub.dto.response;

import lombok.Data;

@Data
public class InventoryItemResponseDto {

    private String sku;
    private String batchNumber;
    private Integer availableQuantity = 0;
}
