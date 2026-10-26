package com.chainbers.personal_asset_management_system.repository.asset_properties.physical_assets.electronics;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.electronics.Electronics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ElectronicsRepository extends JpaRepository<Electronics, UUID>, JpaSpecificationExecutor<Electronics> {
    Optional<Electronics> findByIdAndAssetsUserId(UUID id, UUID userId);
}
