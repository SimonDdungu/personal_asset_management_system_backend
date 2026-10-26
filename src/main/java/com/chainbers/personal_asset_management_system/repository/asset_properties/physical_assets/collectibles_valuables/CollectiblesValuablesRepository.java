package com.chainbers.personal_asset_management_system.repository.asset_properties.physical_assets.collectibles_valuables;

import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.collectibles_valuables.CollectiblesValuables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface CollectiblesValuablesRepository extends JpaRepository<CollectiblesValuables, UUID>, JpaSpecificationExecutor<CollectiblesValuables> {
    List<CollectiblesValuables> findByAssetsUserId(UUID userId);
}
