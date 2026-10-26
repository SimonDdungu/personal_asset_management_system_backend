package com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.physical_cash;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.physical_cash.PhysicalCash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface PhysicalCashRepository extends JpaRepository<PhysicalCash, UUID>, JpaSpecificationExecutor<PhysicalCash> {
    List<PhysicalCash> findByAssetsUserId(UUID userId);
}
