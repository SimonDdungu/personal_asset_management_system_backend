package com.chainbers.personal_asset_management_system.repository.asset_properties.liability;

import com.chainbers.personal_asset_management_system.entity.asset_properties.liability.Liability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LiabilityRepository extends JpaRepository<Liability, UUID>, JpaSpecificationExecutor<Liability> {
    List<Liability> findByAssetsUserId(UUID userId);
}
