package com.chainbers.personal_asset_management_system.repository.asset_properties.insurance;

import com.chainbers.personal_asset_management_system.entity.asset_properties.insurance.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface InsuranceRepository extends JpaRepository<Insurance, UUID>, JpaSpecificationExecutor<Insurance> {
    List<Insurance> findByAssetsUserId(UUID userId);

}
