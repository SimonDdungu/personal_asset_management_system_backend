package com.chainbers.personal_asset_management_system.entity.asset_properties.land;

import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "land")
@Getter
@Setter
@NoArgsConstructor
public class Land {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Assets assets;

    @Column(nullable = false)
    private String address;

    @Column
    private String description;

    @Column(name = "plot_number", nullable = false)
    private String plotNumber;

    @Column(name = "land_size", nullable = false)
    private int landSize;

    @Column(name = "land_size_unit", nullable = false, length = 3)
    private String landSizeUnit;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "current_value", nullable = false, precision = 19, scale = 2)
    private BigDecimal currentValue;

    @Column(name = "current_value_updated_at", nullable = false)
    private Instant currentValueUpdatedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}
