package com.chainbers.personal_asset_management_system.repository.assets;

import com.chainbers.personal_asset_management_system.entity.assets.Acquisition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AcquisitionRepository extends JpaRepository<Acquisition, UUID> {
}
