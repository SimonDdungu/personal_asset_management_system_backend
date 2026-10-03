package com.chainbers.personal_asset_management_system.repository.asset_properties.savings;

import com.chainbers.personal_asset_management_system.entity.asset_properties.savings.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface SavingsRepository extends JpaRepository<Savings, UUID>, JpaSpecificationExecutor<Savings> {
    List<Savings> findByAssetsUserId(UUID userId);
}
