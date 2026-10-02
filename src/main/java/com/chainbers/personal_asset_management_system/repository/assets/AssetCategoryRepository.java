package com.chainbers.personal_asset_management_system.repository.assets;

import com.chainbers.personal_asset_management_system.entity.assets.AssetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssetCategoryRepository extends JpaRepository<AssetCategory, UUID> {
    List<AssetCategory> findByNameContainingIgnoreCase(String name);
    List<AssetCategory> findByAssetTypeNameIgnoreCase(String assetTypeName);
}
