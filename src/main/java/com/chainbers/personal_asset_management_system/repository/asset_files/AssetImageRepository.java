package com.chainbers.personal_asset_management_system.repository.asset_files;

import com.chainbers.personal_asset_management_system.entity.asset_files.AssetImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssetImageRepository extends JpaRepository<AssetImage, UUID> {
    List<AssetImage> findByAssetId(UUID assetId);
}
