package com.chainbers.personal_asset_management_system.entity.asset_files.images;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JoinColumn(name = "uploader_id", nullable = false)
    private AssetImageUpload assetImageUpload;

    @Column(name = "image_path", nullable = false,  length = 2048)
    private String imagePath;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary;

}
