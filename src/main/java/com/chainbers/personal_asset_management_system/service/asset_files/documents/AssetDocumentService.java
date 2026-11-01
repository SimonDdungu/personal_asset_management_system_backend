package com.chainbers.personal_asset_management_system.service.asset_files.documents;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.AssetDocumentResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.documents.AssetDocumentUploadResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.documents.CreateAssetDocumentRequest;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.UpdateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageUploadResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.UpdateAssetImageRequest;
import com.chainbers.personal_asset_management_system.entity.asset_files.documents.AssetDocument;
import com.chainbers.personal_asset_management_system.entity.asset_files.documents.AssetDocumentType;
import com.chainbers.personal_asset_management_system.entity.asset_files.documents.AssetDocumentUpload;

import com.chainbers.personal_asset_management_system.entity.asset_files.images.AssetImage;
import com.chainbers.personal_asset_management_system.entity.asset_files.images.AssetImageUpload;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_files.documents.AssetDocumentRepository;
import com.chainbers.personal_asset_management_system.repository.asset_files.documents.AssetDocumentTypeRepository;
import com.chainbers.personal_asset_management_system.repository.asset_files.documents.AssetDocumentUploadRepository;

import com.chainbers.personal_asset_management_system.storage.SupabaseFileStorage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetDocumentService {
    private AssetDocumentUploadRepository assetDocumentUploadRepository;
    private AssetDocumentRepository assetDocumentRepository;
    private AssetDocumentTypeRepository assetDocumentTypeRepository;
    private final SupabaseFileStorage fileStorage;

    @Transactional
    public AssetDocumentUploadResponse create(CreateAssetDocumentRequest request, Assets assets){
        if (request.documents() == null || request.documents().isEmpty()) {
            throw new IllegalArgumentException("No documents available");
        }

        AssetDocumentType assetDocumentType = assetDocumentTypeRepository.findById(request.assetDocumentTypeId())
                                                        .orElseThrow(() -> new EntityNotFoundException("Document type not found"));

        List<MultipartFile> documentFiles = request.documents();

        AssetDocumentUpload upload = new AssetDocumentUpload();
        upload.setAssets(assets);
        upload.setCaption(request.caption());
        upload.setAssetDocumentType(assetDocumentType);
        upload = assetDocumentUploadRepository.save(upload);

        List<AssetDocument> documents = new ArrayList<>();

        for (int i = 0; i < documentFiles.size(); i++) {
            MultipartFile file = documentFiles.get(i);

            String documentPath = fileStorage.store("documents", file);

            AssetDocument document = new AssetDocument();
            document.setAssetDocumentUpload(upload);
            document.setDocumentPath(documentPath);

            documents.add(document);
        }

        List<AssetDocumentResponse> documentResponse = assetDocumentRepository.saveAll(documents)
                                                                        .stream()
                                                                        .map(document -> new AssetDocumentResponse(
                                                                                document.getId(),
                                                                                fileStorage.signedUrl(document.getDocumentPath())
                                                                        ))
                                                                        .toList();

        return new AssetDocumentUploadResponse(
                upload.getId(),
                assets.getId(),
                upload.getAssetDocumentType().getId(),
                upload.getAssetDocumentType().getName(),
                upload.getCaption(),
                upload.getUploadedAt(),
                upload.getUpdatedAt(),
                documentResponse
        );
    }


    @Transactional
    public AssetDocumentUploadResponse update(UUID uploadId, UpdateAssetDocumentRequest request, UUID userId){
        AssetDocumentUpload upload = assetDocumentUploadRepository.findByIdAndAssetsUserIdAndAssetsDeletedAtIsNull(uploadId, userId)
                                                .orElseThrow(() -> new EntityNotFoundException("Document upload not found"));

        List<AssetDocument> documents = assetDocumentRepository.findAllByAssetDocumentUploadIdAndAssetDocumentUploadAssetsUserIdAndAssetDocumentUploadAssetsDeletedAtIsNull(uploadId, userId);

        if (documents.isEmpty()) {
            throw new EntityNotFoundException("Document upload not found");
        }

        upload.setCaption(request.caption());

        upload.setUpdatedAt(Instant.now());

        assetDocumentUploadRepository.save(upload);

        return new AssetDocumentUploadResponse(
                upload.getId(),
                upload.getAssets().getId(),
                upload.getAssetDocumentType().getId(),
                upload.getAssetDocumentType().getName(),
                upload.getCaption(),
                upload.getUploadedAt(),
                upload.getUpdatedAt(),
                documents.stream()
                        .map(document -> new AssetDocumentResponse(
                                document.getId(),
                                fileStorage.signedUrl(document.getDocumentPath())
                        ))
                        .toList()
        );

    }

    public List<AssetDocumentUploadResponse> getDocumentByAssetId(UUID assetId) {
        List<AssetDocumentUpload> uploads = assetDocumentUploadRepository.findAllByAssetsId(assetId);

        return uploads.stream()
                .map(upload -> {
                    List<AssetDocumentResponse> documents = assetDocumentRepository.findAllByAssetDocumentUploadId(upload.getId())
                            .stream()
                            .map(document -> new AssetDocumentResponse(
                                    document.getId(),
                                    fileStorage.signedUrl(document.getDocumentPath())
                            ))
                            .toList();

                    return new AssetDocumentUploadResponse(
                            upload.getId(),
                            upload.getAssets().getId(),
                            upload.getAssetDocumentType().getId(),
                            upload.getAssetDocumentType().getName(),
                            upload.getCaption(),
                            upload.getUploadedAt(),
                            upload.getUpdatedAt(),
                            documents
                    );
                })
                .toList();
    }


    @Transactional
    public void deleteDocument(UUID uploadId, UUID documentId, UUID userId) {
        AssetDocumentUpload upload = assetDocumentUploadRepository.findByIdAndAssetsUserIdAndAssetsDeletedAtIsNull(uploadId, userId)
                                                            .orElseThrow(() -> new EntityNotFoundException("Document upload not found"));

        AssetDocument document = assetDocumentRepository.findByIdAndAssetDocumentUploadId(documentId, upload.getId())
                                                    .orElseThrow(() -> new EntityNotFoundException("Document not found"));

        // Delete the stored file
        fileStorage.delete(document.getDocumentPath());

        assetDocumentRepository.delete(document);

        // Remove the upload if no documents remain
        if (assetDocumentRepository.findAllByAssetDocumentUploadId(upload.getId()).isEmpty()) {
            assetDocumentUploadRepository.delete(upload);
        }
    }

    @Transactional
    public void deleteUpload(UUID uploadId, UUID userId) {
        AssetDocumentUpload upload = assetDocumentUploadRepository.findByIdAndAssetsUserIdAndAssetsDeletedAtIsNull(uploadId, userId)
                                                            .orElseThrow(() -> new EntityNotFoundException("Document upload not found"));

        List<AssetDocument> documents = assetDocumentRepository.findAllByAssetDocumentUploadId(upload.getId());

        // Delete the stored files
        for (AssetDocument document : documents) {
            fileStorage.delete(document.getDocumentPath());
        }

        // Delete database records
        assetDocumentRepository.deleteAll(documents);

        assetDocumentUploadRepository.delete(upload);
    }
}
