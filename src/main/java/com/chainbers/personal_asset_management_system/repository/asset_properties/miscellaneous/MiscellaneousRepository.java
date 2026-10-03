package com.chainbers.personal_asset_management_system.repository.asset_properties.miscellaneous;

import com.chainbers.personal_asset_management_system.entity.asset_properties.miscellaneous.Miscellaneous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface MiscellaneousRepository extends JpaRepository<Miscellaneous, UUID>, JpaSpecificationExecutor<Miscellaneous> {
    List<Miscellaneous> findByAssetsUserId(UUID userId);
}
