package com.devicehub.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "inventory_item")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryItem {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(length = 20)
    private String batchNumber;

    private Integer availableQuantity = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
