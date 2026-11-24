package com.chainbers.personal_asset_management_system.entity.assets;

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
@Table(name = "assets_valution_history", indexes = {@Index(name = "idx_asset_valuation_history_asset_date", columnList = "asset_id, recorded_at")})
@Getter
@Setter
@NoArgsConstructor
public class AssetValutionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Assets asset;


    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal value;

    @CreationTimestamp
    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

}
