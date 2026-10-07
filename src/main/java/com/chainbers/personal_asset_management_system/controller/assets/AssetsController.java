package com.chainbers.personal_asset_management_system.controller.assets;

import com.chainbers.personal_asset_management_system.dto.assets.AssetsResponse;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import com.chainbers.personal_asset_management_system.dto.assets.UpdateAssetsRequest;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetsController {
    private final AssetsService assetsService;

    @GetMapping("/{id}")
    public AssetsResponse getAsset(@PathVariable UUID id, @RequestParam UUID userId) {
        return assetsService.getById(id, userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssetsResponse createAsset(@Valid @RequestBody CreateAssetsRequest request, @RequestParam UUID userId) {
        return assetsService.create(request, userId);
    }

    @PatchMapping("/{id}")
    public AssetsResponse updateAsset(@PathVariable UUID id, @RequestBody UpdateAssetsRequest request, @RequestParam UUID userId) {
        return assetsService.update(id, request, userId);
    }

    @GetMapping
    public Page<AssetsResponse> searchAssets(@RequestParam UUID userId, @RequestParam(required = false) String name,
                                             @RequestParam(required = false) UUID categoryId,
                                             @RequestParam(required = false) UUID acquisitionId, Pageable pageable) {
        return assetsService.search(name, categoryId, acquisitionId, userId, pageable);
    }



    // Deleted Assets
    @DeleteMapping("/{id}")
    public void deleteAsset(@PathVariable UUID id, @RequestParam UUID userId) {
        assetsService.softDeleteAsset(id, userId);
    }

    @GetMapping("/deleted")
    public Page<AssetsResponse> getDeletedAssets(@RequestParam UUID userId, Pageable pageable) {
        return assetsService.getDeletedAssets(userId, pageable);
    }

    @PatchMapping("/{id}/restore")
    public void restoreAsset(@PathVariable UUID id, @RequestParam UUID userId) {
        assetsService.restoreAsset(id, userId);
    }

    @DeleteMapping("/{id}/permanent")
    public void permanentlyDeleteAsset(@PathVariable UUID id, @RequestParam UUID userId) {
        assetsService.hardDeleteAsset(id, userId);
    }
}
