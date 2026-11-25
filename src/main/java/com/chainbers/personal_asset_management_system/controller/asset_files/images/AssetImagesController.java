package com.chainbers.personal_asset_management_system.controller.asset_files.images;

import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageUploadResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.CreateAssetImageRequest;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.UpdateAssetImageRequest;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.repository.assets.AssetsRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.images.AssetImageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("assets/files/images")
@RequiredArgsConstructor
public class AssetImagesController {

    private final AssetImageService assetImageService;

    @PostMapping(value = "/{uploadId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<AssetImageResponse> addImages(@PathVariable UUID uploadId, @AuthenticationPrincipal User user, @ModelAttribute CreateAssetImageRequest request) {
        return assetImageService.addImages(request, uploadId, user.getId());
    }

    @PatchMapping("/{id}")
    public AssetImageUploadResponse updateImage(@PathVariable UUID id, @RequestBody UpdateAssetImageRequest request, @AuthenticationPrincipal User user){
        return assetImageService.update(id, request, user.getId());
    }

    @DeleteMapping("/{uploadId}/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable UUID uploadId, @PathVariable UUID imageId, @AuthenticationPrincipal User user) {
        assetImageService.deleteImage(uploadId, imageId, user.getId());
        return ResponseEntity.noContent().build();
    }

}
