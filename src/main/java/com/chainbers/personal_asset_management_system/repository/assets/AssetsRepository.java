package com.chainbers.personal_asset_management_system.repository.assets;

import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface AssetsRepository extends JpaRepository<Assets, UUID>, JpaSpecificationExecutor<Assets> {
    List<Assets> findByUserId(UUID userId);
}
