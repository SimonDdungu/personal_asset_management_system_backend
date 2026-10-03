package com.chainbers.personal_asset_management_system.repository.asset_properties.real_estate;

import com.chainbers.personal_asset_management_system.entity.asset_properties.real_estate.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface RealEstateRepository extends JpaRepository<RealEstate, UUID>, JpaSpecificationExecutor<RealEstate> {
    List<RealEstate> findByAssetsUserId(UUID userId);
}
