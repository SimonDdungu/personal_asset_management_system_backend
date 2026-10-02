package com.chainbers.personal_asset_management_system.entity.asset_files;

import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "asset_images")
@Getter
@Setter
@NoArgsConstructor
public class AssetImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Assets assets;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column
    private String caption;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary;

    @CreationTimestamp
    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

}
