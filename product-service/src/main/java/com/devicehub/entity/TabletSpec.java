package com.devicehub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

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
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private boolean hasCellular = false;
    private boolean stylusSupported;
    private boolean keyboardSupport;

}