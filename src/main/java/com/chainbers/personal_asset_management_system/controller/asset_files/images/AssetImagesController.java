package com.chainbers.personal_asset_management_system.controller.asset_files.images;

import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageUploadResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.UpdateAssetImageRequest;
import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics.UpdateElectronicsRequest;
import com.chainbers.personal_asset_management_system.service.asset_files.images.AssetImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("assets/images")
@RequiredArgsConstructor
public class AssetImagesController {

    private final AssetImageService assetImageService;

    @PatchMapping("/{id}")
    public AssetImageUploadResponse updateImage(@PathVariable UUID id, @RequestBody UpdateAssetImageRequest request, @RequestParam UUID userId){
        return assetImageService.update(id, request, userId);
    }

}
