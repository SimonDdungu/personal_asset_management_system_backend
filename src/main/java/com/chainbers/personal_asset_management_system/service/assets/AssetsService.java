package com.chainbers.personal_asset_management_system.service.assets;

import com.chainbers.personal_asset_management_system.dto.assets.AssetSearchFilter;
import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import com.chainbers.personal_asset_management_system.dto.assets.AssetsResponse;
import com.chainbers.personal_asset_management_system.dto.assets.UpdateAssetsRequest;
import com.chainbers.personal_asset_management_system.dto.metrics.*;
import com.chainbers.personal_asset_management_system.entity.asset_files.documents.AssetDocumentUpload;
import com.chainbers.personal_asset_management_system.entity.asset_files.images.AssetImageUpload;
import com.chainbers.personal_asset_management_system.entity.assets.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.repository.asset_files.documents.AssetDocumentUploadRepository;
import com.chainbers.personal_asset_management_system.repository.asset_files.images.AssetImageUploadRepository;
import com.chainbers.personal_asset_management_system.repository.assets.*;
import com.chainbers.personal_asset_management_system.repository.user.UserRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.asset_files.images.AssetImageService;
import com.chainbers.personal_asset_management_system.specifications.assets.AssetSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetsService {
    private final AssetsRepository assetsRepository;
    private final AssetCategoryRepository assetCategoryRepository;
    private final AssetTypeRepository assetTypeRepository;
    private final UserRepository userRepository;
    private final AcquisitionRepository acquisitionRepository;
    private final AssetImageUploadRepository assetImageUploadRepository;
    private final AssetDocumentUploadRepository assetDocumentUploadRepository;
    private final AssetImageService assetImageService;
    private final AssetDocumentService assetDocumentService;
    private final AssetValutionHistoryRepository assetValutionHistoryRepository;

    private AssetCategory findCategory(String category) {
        return assetCategoryRepository.findByName(category)
                .orElseThrow(() -> new EntityNotFoundException("Asset category not found: " + category));
    }

    private AssetType findAssetType(String assetType) {
        return assetTypeRepository.findByName(assetType)
                .orElseThrow(() -> new EntityNotFoundException("Asset type not found: " + assetType));
    }

    private Acquisition findAcquisition(String acquisition){
        return acquisitionRepository.findByName(acquisition)
                .orElseThrow(() -> new EntityNotFoundException(("Acquisition not found: " + acquisition)));
    }

    private User findUser(UUID id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(("User not found: " + id)));
    }

    private Assets findAsset(UUID id, UUID userId){
        return assetsRepository.findByIdAndUserIdAndDeletedAtIsNull(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found: " + id));
    }

    private Assets findDeletedAsset(UUID id, UUID userId){
        return assetsRepository.findByIdAndUserIdAndDeletedAtIsNotNull(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found: " + id));
    }

    @Transactional(readOnly = true)
    public AssetsResponse getById(UUID id, UUID userId) {
        return toResponse(findAsset(id, userId));
    }

    @Transactional
    public AssetsResponse create(CreateAssetsRequest request, UUID userId) {
        return toResponse(createEntity(request, userId));
    }

    @Transactional
    public Assets createEntity(CreateAssetsRequest request, UUID userId){
        Assets assets = new Assets();
        assets.setUser(findUser(userId));
        assets.setName(request.name());
        assets.setAssetType(findAssetType(request.assetType()));
        assets.setAcquisition(findAcquisition(request.acquisition()));
        assets.setAssetCategory(findCategory(request.assetCategory()));
        assets.setDescription(request.description());
        assets.setCurrency(request.currency());
        assets.setCurrentValue(request.currentValue());
        assets.setCurrentValueUpdatedAt(Instant.now());

        Assets savedAsset = assetsRepository.save(assets);

        AssetValutionHistory history = new AssetValutionHistory();
        history.setAsset(savedAsset);
        history.setValue(savedAsset.getCurrentValue());

        assetValutionHistoryRepository.save(history);

        return savedAsset;
    }

    @Transactional
    public AssetsResponse update(UUID id, UpdateAssetsRequest request, UUID userId){
        Assets assets = findAsset(id, userId);


        assets.setDescription(request.description());

        if(request.name() != null && !request.name().isBlank()){
            assets.setName(request.name());
        }

        if(request.acquisition() != null){
            assets.setAcquisition(findAcquisition(request.acquisition()));
        }

        if(request.assetCategory() != null){
            assets.setAssetCategory(findCategory(request.assetCategory()));
        }

        if(request.currency() != null){
            assets.setCurrency(request.currency());
        }

        if(request.currentValue() != null  && request.currentValue().compareTo(BigDecimal.ZERO) >= 0 && (assets.getCurrentValue() == null || request.currentValue().compareTo(assets.getCurrentValue()) != 0) ) {
            assets.setCurrentValue(request.currentValue());
            assets.setCurrentValueUpdatedAt(Instant.now());

            AssetValutionHistory history = new AssetValutionHistory();
            history.setAsset(assets);
            history.setValue(assets.getCurrentValue());

            assetValutionHistoryRepository.save(history);
        }

        assets.setUpdatedAt(Instant.now());

        return toResponse(assets);
    }

    @Transactional
    public void softDeleteAsset(UUID id, UUID userId) {
        Assets assets = findAsset(id, userId);

        assets.setDeletedAt(Instant.now());
    }

    @Transactional
    public void restoreAsset(UUID id, UUID userId) {
        Assets asset = findDeletedAsset(id, userId);
        asset.setDeletedAt(null);
    }

    @Transactional
    public void hardDeleteAsset(UUID id, UUID userId) {
        Assets asset = assetsRepository.findByIdAndUserId(id, userId)
                            .orElseThrow(() -> new EntityNotFoundException("Asset not found"));

        List<AssetImageUpload> imageUploads = assetImageUploadRepository.findAllByAssetsId(asset.getId());
        List<AssetDocumentUpload> documentUploads = assetDocumentUploadRepository.findAllByAssetsId(asset.getId());

        for (AssetImageUpload upload : imageUploads) {
            assetImageService.deleteUpload(upload.getId(), userId);
        }

        for (AssetDocumentUpload upload : documentUploads) {
            assetDocumentService.deleteUpload(upload.getId(), userId);
        }

        assetsRepository.delete(asset);
    }

    @Transactional(readOnly = true)
    public Page<AssetsResponse> search(AssetSearchFilter filter, UUID userId, Pageable pageable){
        Specification<Assets> specification = Specification.allOf(
                AssetSpecification.belongsTo(userId),
                AssetSpecification.hasAcquisition(filter.acquisitionId()),
                AssetSpecification.hasCategory(filter.assetCategoryId()),
                AssetSpecification.nameContains(filter.name()),
                AssetSpecification.currencyIs(filter.currency()),
                AssetSpecification.currentValueFrom(filter.minValue()),
                AssetSpecification.currentValueTo(filter.maxValue()),
                AssetSpecification.isNotDeleted()
        );

        return assetsRepository.findAll(specification, pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public long getTotalAssets(UUID userId) {
        return assetsRepository.totalAssetsCount(userId);
    }

    @Transactional(readOnly = true)
    public long getPhysicalAssetsCount(UUID userId) {
        return assetsRepository.totalPhysicalAssetsCount(userId);
    }

    @Transactional(readOnly = true)
    public long getFinancialAssetsCount(UUID userId) {
        return assetsRepository.totalFinancialAssetsCount(userId);
    }

    @Transactional(readOnly = true)
    public List<CurrencyAverage> getAverageAssetValue(UUID userId) {
        return assetsRepository.averageAssetValueByCurrency(userId);
    }

    @Transactional(readOnly = true)
    public List<CurrencyAverage> getAveragePhysicalAssets(UUID userId) {
        return assetsRepository.averagePhysicalAssetsByCurrency(userId);
    }

    @Transactional(readOnly = true)
    public List<CurrencyAverage> getAverageFinancialAssets(UUID userId) {
        return assetsRepository.averageFinancialAssetsByCurrency(userId);
    }

    @Transactional(readOnly = true)
    public Page<AssetsResponse> getDeletedAssets(UUID userId, Pageable pageable) {
        Specification<Assets> specification = Specification.allOf(
                AssetSpecification.belongsTo(userId),
                AssetSpecification.isDeleted()
        );

        return assetsRepository.findAll(specification, pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Map<String, List<LargestAsset>> getLargestAssets(UUID userId) {
        Map<String, List<LargestAsset>> result = new LinkedHashMap<>();

        for (CurrencyTotal ct : assetsRepository.sumByCurrency(userId)) {
            List<LargestAsset> top = assetsRepository.findLargestAssets(
                            userId, ct.currency())
                    .stream()
                    .map(this::toLargestAsset)
                    .toList();

            result.put(ct.currency().getCurrencyCode(), top);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, List<LargestAsset>> getLargestPhysicalAssets(UUID userId) {
        Map<String, List<LargestAsset>> result = new LinkedHashMap<>();

        for (CurrencyTotal ct : assetsRepository.sumPhysicalAssetsByCurrency(userId)) {
            List<LargestAsset> top = assetsRepository.findLargestPhysicalAssets(
                            userId, ct.currency())
                    .stream()
                    .map(this::toLargestAsset)
                    .toList();

            result.put(ct.currency().getCurrencyCode(), top);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, List<LargestAsset>> getLargestFinancialAssets(UUID userId) {
        Map<String, List<LargestAsset>> result = new LinkedHashMap<>();

        for (CurrencyTotal ct : assetsRepository.sumFinancialAssetsByCurrency(userId)) {
            List<LargestAsset> top = assetsRepository.findLargestFinancialAssets(
                            userId, ct.currency())
                    .stream()
                    .map(this::toLargestAsset)
                    .toList();

            result.put(ct.currency().getCurrencyCode(), top);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, List<CategoryTotal>> getTopCategories(UUID userId) {
        Map<String, List<CategoryTotal>> result = new LinkedHashMap<>();

        for (CurrencyTotal ct : assetsRepository.sumByCurrency(userId)) {
            List<CategoryTotal> top = assetsRepository.topCategories(userId, ct.currency());
            result.put(ct.currency().getCurrencyCode(), top);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, List<CategoryTotal>> getTopPhysicalCategories(UUID userId) {
        Map<String, List<CategoryTotal>> result = new LinkedHashMap<>();

        for (CurrencyTotal ct : assetsRepository.sumPhysicalAssetsByCurrency(userId)) {
            List<CategoryTotal> top = assetsRepository.topPhysicalCategories(
                    userId, ct.currency());

            result.put(ct.currency().getCurrencyCode(), top);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, List<CategoryTotal>> getTopFinancialCategories(UUID userId) {
        Map<String, List<CategoryTotal>> result = new LinkedHashMap<>();

        for (CurrencyTotal ct : assetsRepository.sumFinancialAssetsByCurrency(userId)) {
            List<CategoryTotal> top = assetsRepository.topFinancialCategories(
                    userId, ct.currency());

            result.put(ct.currency().getCurrencyCode(), top);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, List<CategoryCount>> getTopCategoriesByCount(UUID userId) {
        Map<String, List<CategoryCount>> result = new LinkedHashMap<>();

        for (CurrencyTotal ct : assetsRepository.sumByCurrency(userId)) {
            List<CategoryCount> top = assetsRepository.topCategoriesByCount(
                    userId, ct.currency());

            result.put(ct.currency().getCurrencyCode(), top);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, List<CategoryCount>> getTopPhysicalCategoriesByCount(UUID userId) {
        Map<String, List<CategoryCount>> result = new LinkedHashMap<>();

        for (CurrencyTotal ct : assetsRepository.sumPhysicalAssetsByCurrency(userId)) {
            List<CategoryCount> top = assetsRepository.topPhysicalCategoriesByCount(
                    userId, ct.currency());

            result.put(ct.currency().getCurrencyCode(), top);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, List<CategoryCount>> getTopFinancialCategoriesByCount(UUID userId) {
        Map<String, List<CategoryCount>> result = new LinkedHashMap<>();

        for (CurrencyTotal ct : assetsRepository.sumFinancialAssetsByCurrency(userId)) {
            List<CategoryCount> top = assetsRepository.topFinancialCategoriesByCount(
                    userId, ct.currency());

            result.put(ct.currency().getCurrencyCode(), top);
        }

        return result;
    }



    private AssetsResponse toResponse(Assets a) {
        return new AssetsResponse(
                a.getId(),
                a.getName(),
                a.getDescription(),
                a.getAssetType().getName(),
                a.getAssetCategory().getName(),
                a.getAcquisition().getName(),
                a.getCurrency(),
                a.getCurrentValue(),
                a.getCurrentValueUpdatedAt(),
                a.getCreatedAt(),
                a.getUpdatedAt());
    }

    private LargestAsset toLargestAsset(Assets asset) {
        return new LargestAsset(
                asset.getId(),
                asset.getName(),
                asset.getAssetCategory().getName(),
                asset.getCurrency(),
                asset.getCurrentValue()
        );
    }
}
