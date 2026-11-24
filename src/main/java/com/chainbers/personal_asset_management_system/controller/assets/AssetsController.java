package com.chainbers.personal_asset_management_system.controller.assets;

import com.chainbers.personal_asset_management_system.dto.assets.AssetSearchFilter;
import com.chainbers.personal_asset_management_system.dto.assets.AssetsResponse;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import com.chainbers.personal_asset_management_system.dto.assets.UpdateAssetsRequest;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetsController {
    private final AssetsService assetsService;

    @GetMapping("/{id}")
    public AssetsResponse getAsset(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return assetsService.getById(id, user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssetsResponse createAsset(@Valid @RequestBody CreateAssetsRequest request, @AuthenticationPrincipal User user) {
        return assetsService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public AssetsResponse updateAsset(@PathVariable UUID id, @RequestBody UpdateAssetsRequest request, @AuthenticationPrincipal User user) {
        return assetsService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<AssetsResponse> searchAssets(AssetSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return assetsService.search(filters, user.getId(), pageable);
    }



    // Deleted Assets
    @DeleteMapping("/{id}")
    public void deleteAsset(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        assetsService.softDeleteAsset(id, user.getId());
    }

    @GetMapping("/deleted")
    public Page<AssetsResponse> getDeletedAssets(@AuthenticationPrincipal User user, Pageable pageable) {
        return assetsService.getDeletedAssets(user.getId(), pageable);
    }

    @PatchMapping("/{id}/restore")
    public void restoreAsset(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        assetsService.restoreAsset(id, user.getId());
    }

    @DeleteMapping("/{id}/permanent")
    public void permanentlyDeleteAsset(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        assetsService.hardDeleteAsset(id, user.getId());
    }
}
