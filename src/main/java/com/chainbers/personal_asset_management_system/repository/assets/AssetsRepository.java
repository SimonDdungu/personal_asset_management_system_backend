package com.chainbers.personal_asset_management_system.repository.assets;

import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssetsRepository extends JpaRepository<Assets, UUID> {
    List<Assets> findByNameContainingIgnoreCase(String name);
    List<Assets> findByUserId(UUID userId);
    List<Assets> findByAssetCategoryNameIgnoreCase(String categoryName);
    List<Assets> findByAcquisitionNameIgnoreCase(String acquisitionName);
}
