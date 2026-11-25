package com.chainbers.personal_asset_management_system.repository.asset_properties.physical_assets.machinery;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.machinery.Machinery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface MachineryRepository extends JpaRepository<Machinery, UUID>, JpaSpecificationExecutor<Machinery> {
    Optional<Machinery> findByIdAndAssetsUserId(UUID id, UUID userId);
}
