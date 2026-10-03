package com.chainbers.personal_asset_management_system.repository.asset_properties.machinery;

import com.chainbers.personal_asset_management_system.entity.asset_properties.machinery.Machinery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface MachineryRepository extends JpaRepository<Machinery, UUID>, JpaSpecificationExecutor<Machinery> {
    List<Machinery> findByAssetsUserId(UUID userId);
}
