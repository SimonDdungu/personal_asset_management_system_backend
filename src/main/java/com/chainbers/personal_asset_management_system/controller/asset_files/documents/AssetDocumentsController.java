package com.chainbers.personal_asset_management_system.controller.asset_files.documents;

import com.chainbers.personal_asset_management_system.dto.asset_files.documents.AssetDocumentUploadResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.documents.UpdateAssetDocumentRequest;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("assets/files/documents")
@RequiredArgsConstructor
public class AssetDocumentsController {
    private final AssetDocumentService assetDocumentService;

    @PatchMapping("/{id}")
    public AssetDocumentUploadResponse updateDocument(@PathVariable UUID id, @RequestBody UpdateAssetDocumentRequest request, @RequestParam UUID userId){
        return assetDocumentService.update(id, request, userId);
    }
}
