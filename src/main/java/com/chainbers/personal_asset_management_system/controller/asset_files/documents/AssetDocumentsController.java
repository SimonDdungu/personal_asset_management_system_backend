package com.chainbers.personal_asset_management_system.controller.asset_files.documents;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.AssetDocumentResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.documents.AssetDocumentUploadResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.documents.CreateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.dto.asset_files.documents.UpdateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("assets/files/documents")
@RequiredArgsConstructor
public class AssetDocumentsController {
    private final AssetDocumentService assetDocumentService;

    @PostMapping(value = "/{uploadId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<AssetDocumentResponse> add(@PathVariable UUID uploadId, @AuthenticationPrincipal User user, @ModelAttribute CreateAssetDocumentRequest request) {
        return assetDocumentService.addDocs(request, uploadId, user.getId());
    }

    @PatchMapping("/{id}")
    public AssetDocumentUploadResponse updateDocument(@PathVariable UUID id, @RequestBody UpdateAssetDocumentRequest request, @RequestParam UUID userId){
        return assetDocumentService.update(id, request, userId);
    }

    @DeleteMapping("/{uploadId}/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable UUID uploadId, @PathVariable UUID imageId, @AuthenticationPrincipal User user) {
        assetDocumentService.deleteDocument(uploadId, imageId, user.getId());
        return ResponseEntity.noContent().build();
    }
}
