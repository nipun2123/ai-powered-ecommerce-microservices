package com.devicehub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tablet_spec")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TabletSpec {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    private Product product;

    private boolean hasCellular = false;
    private boolean stylusSupported;
    private boolean keyboardSupport;

}