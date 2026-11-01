package com.chainbers.personal_asset_management_system.repository.asset_files.images;

import com.chainbers.personal_asset_management_system.entity.asset_files.images.AssetImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetImageRepository extends JpaRepository<AssetImage, UUID> {
    List<AssetImage> findAllByAssetImageUploadIdAndAssetImageUploadAssetsUserIdAndAssetImageUploadAssetsDeletedAtIsNull(UUID uploadId, UUID userId);

    List<AssetImage> findAllByAssetImageUploadId(UUID uploadId);

    Optional<AssetImage> findByIdAndAssetImageUploadId(UUID imageId, UUID uploadId);

    void deleteAllByAssetImageUploadId(UUID uploadId);

}
