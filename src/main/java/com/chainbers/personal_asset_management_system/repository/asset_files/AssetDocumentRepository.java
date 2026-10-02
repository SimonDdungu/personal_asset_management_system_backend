package com.chainbers.personal_asset_management_system.repository.asset_files;

import com.chainbers.personal_asset_management_system.entity.asset_files.AssetDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssetDocumentRepository extends JpaRepository<AssetDocument, UUID> {
    List<AssetDocument> findByAssetId(UUID assetId);
    List<AssetDocument> findByCaptionContainingIgnoreCase(String caption);
}
