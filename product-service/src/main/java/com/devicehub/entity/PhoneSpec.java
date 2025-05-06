package com.devicehub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "phone_spec")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneSpec {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    private Product product;

    @Column(precision = 4, scale = 1)
    private BigDecimal ultraWideCamMp;

    @Column(length = 5)
    private String ipRating;

    private boolean hasEstim = false;

}
