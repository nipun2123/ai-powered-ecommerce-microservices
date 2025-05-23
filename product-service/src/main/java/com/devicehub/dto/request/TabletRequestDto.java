package com.devicehub.dto.request;

import com.devicehub.entity.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TabletRequestDto {

    // common product fields
    @NotNull(message = "SKU cannot be empty")
    @Size(min = 3, max = 30, message = "SKU must be between 3 and 30 characters")
    private String sku;

    private String currentSku;

    @NotNull(message = "Barcode cannot be empty")
    @Size(min = 5, max = 50, message = "Barcode must be between 5 and 50 characters")
    private String barcode;

    @NotNull(message = "Name cannot be empty")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;

    @NotNull(message = "Brand cannot be empty")
    @Size(min = 2, max = 30, message = "Brand must be between 2 and 30 characters")
    private String brand;

    private String description;

    @NotNull(message = "Price cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Category cannot be empty")
    private Product.Category category;

    @NotNull(message = "OS cannot be empty")
    private String os;

    @NotNull(message = "Chipset cannot be empty")
    private String chipset;

    @NotNull(message = "Ram GB cannot be empty")
    @Min(value = 1, message = "RAM must be at least 1 GB")
    private int ramGb;

    @NotNull(message = "Storage GB cannot be empty")
    @Min(value = 1, message = "Storage must be at least 1 GB")
    private int storageGb;

    @NotNull(message = "Screen size cannot be empty")
    @DecimalMin(value = "0.1", message = "Screen size must be greater than 0")
    private BigDecimal screenSizeInches;

    @NotNull(message = "Battery MAH cannot be empty")
    @Min(value = 100, message = "Battery must be at least 100 mAh")
    private int batteryMah;

    @NotNull(message = "Refresh rate cannot be empty")
    @Min(value = 1, message = "Refresh rate must be at least 1 Hz")
    private int refreshRateHz;

    @NotNull(message = "Main cam MP cannot be empty")
    @DecimalMin(value = "0.1", message = "Main camera must be greater than 0")
    private BigDecimal mainCamMp;

    @NotNull(message = "Front cam MP cannot be empty")
    @DecimalMin(value = "0.1", message = "Front camera must be greater than 0")
    private BigDecimal frontCamMp;

    private boolean isActive;

    // tablet specific fields
    private boolean hasCellular = false;
    private boolean stylusSupported;
    private boolean keyboardSupport;
}
