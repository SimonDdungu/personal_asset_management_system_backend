package com.chainbers.personal_asset_management_system.repository.assets;

import com.chainbers.personal_asset_management_system.entity.assets.AssetCategory;
import com.chainbers.personal_asset_management_system.entity.assets.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetCategoryRepository extends JpaRepository<AssetCategory, UUID>, JpaSpecificationExecutor<AssetCategory> {
    Optional<AssetCategory> findByNameAndAssetType(String name, AssetType assetType);
}
