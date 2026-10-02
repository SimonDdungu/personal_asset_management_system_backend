package com.chainbers.personal_asset_management_system.repository.asset_properties.cash_asset;

import com.chainbers.personal_asset_management_system.entity.asset_properties.cash_asset.CashAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CashAssetRepository extends JpaRepository<CashAsset, UUID> {
    List<CashAsset> findByAssetsNameContainingIgnoreCaseAndAssetsUserId(String assetsName, UUID userId);
}
