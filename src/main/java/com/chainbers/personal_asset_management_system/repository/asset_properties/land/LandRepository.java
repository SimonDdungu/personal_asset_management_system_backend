package com.chainbers.personal_asset_management_system.repository.asset_properties.land;

import com.chainbers.personal_asset_management_system.entity.asset_properties.land.Land;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LandRepository extends JpaRepository<Land, UUID>, JpaSpecificationExecutor<Land> {
    List<Land> findByAssetsUserId(UUID userId);
}
