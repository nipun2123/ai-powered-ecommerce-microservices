package com.devicehub.entity;


import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    private Product product;

    @Column(length = 20)
    private String batchNumber;

    private Integer availableQuantity = 0;
}
