package com.chainbers.personal_asset_management_system.repository.asset_files.documents;

import com.chainbers.personal_asset_management_system.entity.asset_files.documents.AssetDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssetDocumentTypeRepository extends JpaRepository<AssetDocumentType, UUID> {
    List<AssetDocumentType> findByName(String name);
}
