package com.chainbers.personal_asset_management_system.controller.assets;

import com.chainbers.personal_asset_management_system.dto.assets.AssetSearchFilter;
import com.chainbers.personal_asset_management_system.dto.assets.AssetsResponse;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import com.chainbers.personal_asset_management_system.dto.assets.UpdateAssetsRequest;
import com.chainbers.personal_asset_management_system.dto.metrics.CategoryCount;
import com.chainbers.personal_asset_management_system.dto.metrics.CategoryTotal;
import com.chainbers.personal_asset_management_system.dto.metrics.CurrencyAverage;
import com.chainbers.personal_asset_management_system.dto.metrics.LargestAsset;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

    @GetMapping("/total/count")
    public long totalAssetsCount(@AuthenticationPrincipal User user){
        return assetsService.getTotalAssets(user.getId());
    }

    @GetMapping("/total/physical/count")
    public long totalPhysicalCount(@AuthenticationPrincipal User user){
        return assetsService.getPhysicalAssetsCount(user.getId());
    }

    @GetMapping("/total/financial/count")
    public long totalFinancialCount(@AuthenticationPrincipal User user){
        return assetsService.getFinancialAssetsCount(user.getId());
    }

    @GetMapping("/top-categories")
    public Map<String, List<CategoryTotal>> topCategories(@AuthenticationPrincipal User user) {
        return assetsService.getTopCategories(user.getId());
    }

    @GetMapping("/physical/top-categories")
    public Map<String, List<CategoryTotal>> getTopPhysicalCategories(@AuthenticationPrincipal User user) {

        return assetsService.getTopPhysicalCategories(user.getId());
    }

    @GetMapping("/financial/top-categories")
    public Map<String, List<CategoryTotal>> getTopFinancialCategories(@AuthenticationPrincipal User user) {
        return assetsService.getTopFinancialCategories(user.getId());
    }

    @GetMapping("/categories/count")
    public Map<String, List<CategoryCount>> getTopCategoriesByCount(@AuthenticationPrincipal User user) {
        return assetsService.getTopCategoriesByCount(user.getId());
    }

    @GetMapping("/physical/categories/count")
    public Map<String, List<CategoryCount>> getTopPhysicalCategoriesByCount(@AuthenticationPrincipal User user) {
        return assetsService.getTopPhysicalCategoriesByCount(user.getId());
    }

    @GetMapping("/financial/categories/count")
    public Map<String, List<CategoryCount>> getTopFinancialCategoriesByCount(@AuthenticationPrincipal User user) {

        return assetsService.getTopFinancialCategoriesByCount(user.getId());
    }

    @GetMapping("/average")
    public List<CurrencyAverage> getAverageAssetValue(@AuthenticationPrincipal User user) {
        return assetsService.getAverageAssetValue(user.getId());
    }

    @GetMapping("/physical/average")
    public List<CurrencyAverage> getAveragePhysicalAssets(@AuthenticationPrincipal User user) {
        return assetsService.getAveragePhysicalAssets(user.getId());
    }

    @GetMapping("/financial/average")
    public List<CurrencyAverage> getAverageFinancialAssets(@AuthenticationPrincipal User user) {
        return assetsService.getAverageFinancialAssets(user.getId());
    }

    @GetMapping("/largest")
    public Map<String, List<LargestAsset>> getLargestAssets(@AuthenticationPrincipal User user) {
        return assetsService.getLargestAssets(user.getId());
    }

    @GetMapping("/physical/largest")
    public Map<String, List<LargestAsset>> getLargestPhysicalAssets(@AuthenticationPrincipal User user) {
        return assetsService.getLargestPhysicalAssets(user.getId());
    }

    @GetMapping("/financial/largest")
    public Map<String, List<LargestAsset>> getLargestFinancialAssets(@AuthenticationPrincipal User user) {
        return assetsService.getLargestFinancialAssets(user.getId());
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
