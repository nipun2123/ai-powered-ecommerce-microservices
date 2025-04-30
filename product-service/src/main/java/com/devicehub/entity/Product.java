package com.devicehub.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    public enum ProductCategory {
        PHONE, TABLET
    }

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 32)
    private String sku;
    @Column(nullable = false, unique = true, length = 32)
    private String barcode;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 32)
    private String brand;

    @Column(length = 1000)
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductCategory category;

    @Column(nullable = false, length = 10)
    private String os;

    @Column(length = 50)
    private String chipset;

    private int ramGb;
    private int storageGb;

    @Column(precision = 3, scale = 1)
    private BigDecimal screenSizeInches;

    private int batteryMah;
    private int refreshRateHz;

    @Column(precision = 4, scale = 1)
    private BigDecimal mainCamMp;
    @Column(precision = 4, scale = 1)
    private BigDecimal frontCamMp;

    @Column(nullable = false)
    private boolean isActive;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
