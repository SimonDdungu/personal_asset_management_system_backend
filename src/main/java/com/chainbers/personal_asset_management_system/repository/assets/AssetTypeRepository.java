package com.chainbers.personal_asset_management_system.repository.assets;

import com.chainbers.personal_asset_management_system.entity.assets.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssetTypeRepository extends JpaRepository<AssetType, UUID> {
    List<AssetType> findByNameContainingIgnoreCase(String name);
}
