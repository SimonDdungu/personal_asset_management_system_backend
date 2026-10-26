package com.chainbers.personal_asset_management_system.repository.asset_properties.physical_assets.furniture;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.furniture.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface FurnitureRepository extends JpaRepository<Furniture, UUID>, JpaSpecificationExecutor<Furniture> {
    List<Furniture> findByAssetsUserId(UUID userId);
}
