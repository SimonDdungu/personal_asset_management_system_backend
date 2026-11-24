package com.chainbers.personal_asset_management_system.repository.assets;

import com.chainbers.personal_asset_management_system.entity.assets.AssetValutionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssetValutionHistoryRepository extends JpaRepository<AssetValutionHistory, UUID> {
}
