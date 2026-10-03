package com.chainbers.personal_asset_management_system.repository.asset_properties.stock_asset;

import com.chainbers.personal_asset_management_system.entity.asset_properties.stock_asset.StockAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface StockAssetRepository extends JpaRepository<StockAsset, UUID>, JpaSpecificationExecutor<StockAsset> {
    List<StockAsset> findByAssetsUserId(UUID userId);
}
