package com.chainbers.personal_asset_management_system.repository.asset_files.images;

import com.chainbers.personal_asset_management_system.entity.asset_files.images.AssetImageUpload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetImageUploadRepository extends JpaRepository<AssetImageUpload, UUID> {
    List<AssetImageUpload> findByIdAndAssetsUserId(UUID id, UUID userId);
    Optional<AssetImageUpload> findByIdAndAssetsUserIdAndAssetsDeletedAtIsNull(UUID uploadId, UUID userId);
    List<AssetImageUpload> findAllByAssetsId(UUID assetId);
}
