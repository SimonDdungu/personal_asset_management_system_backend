package com.chainbers.personal_asset_management_system.repository.asset_files.documents;

import com.chainbers.personal_asset_management_system.entity.asset_files.documents.AssetDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetDocumentRepository extends JpaRepository<AssetDocument, UUID> {
    List<AssetDocument> findAllByAssetDocumentUploadIdAndAssetDocumentUploadAssetsUserIdAndAssetDocumentUploadAssetsDeletedAtIsNull(UUID uploadId, UUID userId);

    List<AssetDocument> findAllByAssetDocumentUploadId(UUID uploadId);

    Optional<AssetDocument> findByIdAndAssetDocumentUploadId(UUID documentId, UUID uploadId);

    void deleteAllByAssetDocumentUploadId(UUID uploadId);
}
