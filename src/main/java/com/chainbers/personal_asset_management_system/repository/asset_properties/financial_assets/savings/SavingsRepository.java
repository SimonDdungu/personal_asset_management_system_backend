package com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.savings;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.savings.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface SavingsRepository extends JpaRepository<Savings, UUID>, JpaSpecificationExecutor<Savings> {
    Optional<Savings> findByIdAndAssetsUserId(UUID id, UUID userId);
}
