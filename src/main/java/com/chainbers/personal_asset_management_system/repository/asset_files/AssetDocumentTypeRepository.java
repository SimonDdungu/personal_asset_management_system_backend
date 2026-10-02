package com.chainbers.personal_asset_management_system.repository.asset_files;

import com.chainbers.personal_asset_management_system.entity.asset_files.AssetDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssetDocumentTypeRepository extends JpaRepository<AssetDocumentRepository, UUID> {
    List<AssetDocumentType> findByName(String name);
}
