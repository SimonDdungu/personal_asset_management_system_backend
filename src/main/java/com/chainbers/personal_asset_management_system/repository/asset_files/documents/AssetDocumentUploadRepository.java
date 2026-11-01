package com.chainbers.personal_asset_management_system.repository.asset_files.documents;

import com.chainbers.personal_asset_management_system.entity.asset_files.documents.AssetDocumentUpload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetDocumentUploadRepository extends JpaRepository<AssetDocumentUpload, UUID> {
    List<AssetDocumentUpload> findByIdAndAssetsUserId(UUID id, UUID userId);
    Optional<AssetDocumentUpload> findByIdAndAssetsUserIdAndAssetsDeletedAtIsNull(UUID uploadId, UUID userId);
    List<AssetDocumentUpload> findAllByAssetsId(UUID assetId);
}
