package com.devicehub.dto.request;

import lombok.Data;

@Data
public class InventoryItemRequestDto {

    private String sku;
    private String batchNumber;
    private Integer availableQuantity = 0;
}
