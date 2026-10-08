package com.chainbers.personal_asset_management_system.service.assets;

import com.chainbers.personal_asset_management_system.dto.assets.CreateAssetsRequest;
import com.chainbers.personal_asset_management_system.dto.assets.AssetsResponse;
import com.chainbers.personal_asset_management_system.dto.assets.UpdateAssetsRequest;
import com.chainbers.personal_asset_management_system.entity.assets.Acquisition;
import com.chainbers.personal_asset_management_system.entity.assets.AssetCategory;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.repository.assets.AcquisitionRepository;
import com.chainbers.personal_asset_management_system.repository.assets.AssetCategoryRepository;
import com.chainbers.personal_asset_management_system.repository.assets.AssetsRepository;
import com.chainbers.personal_asset_management_system.repository.user.UserRepository;
import com.chainbers.personal_asset_management_system.specifications.assets.AssetSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetsService {
    private final AssetsRepository assetsRepository;
    private final AssetCategoryRepository assetCategoryRepository;
    private final UserRepository userRepository;
    private final AcquisitionRepository acquisitionRepository;

    private AssetCategory findCategory(UUID id) {
        return assetCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asset category not found: " + id));
    }

    private Acquisition findAcquisition(UUID id){
        return acquisitionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(("Acquisition not found: " + id)));
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
    public AssetsResponse create(CreateAssetsRequest request, UUID userId){
        Assets assets = new Assets();
        assets.setUser(findUser(userId));
        assets.setName(request.name());
        assets.setAcquisition(findAcquisition(request.acquisitionId()));
        assets.setAssetCategory(findCategory(request.assetCategoryId()));
        assets.setDescription(request.description());

        Assets savedAssets = assetsRepository.save(assets);

        return toResponse(savedAssets);
    }

    @Transactional
    public AssetsResponse update(UUID id, UpdateAssetsRequest request, UUID userId){
        Assets assets = findAsset(id, userId);

        if(request.name() != null && !request.name().isBlank()){
            assets.setName(request.name());
        }

        if(request.description() != null && !request.description().isBlank()){
            assets.setDescription(request.description());
        }

        if(request.acquisitionId() != null){
            assets.setAcquisition(findAcquisition(request.acquisitionId()));
        }

        if(request.assetCategoryId() != null){
            assets.setAssetCategory(findCategory(request.assetCategoryId()));
        }

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
        Assets asset = findDeletedAsset(id, userId);
        assetsRepository.delete(asset);
    }

    @Transactional(readOnly = true)
    public Page<AssetsResponse> search(String name, UUID categoryId, UUID acquisitionId, UUID userId, Pageable pageable){
        Specification<Assets> specification = Specification.allOf(
                AssetSpecification.belongsTo(userId),
                AssetSpecification.hasAcquisition(acquisitionId),
                AssetSpecification.hasCategory(categoryId),
                AssetSpecification.nameContains(name),
                AssetSpecification.isNotDeleted()
        );

        return assetsRepository.findAll(specification, pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<AssetsResponse> getDeletedAssets(UUID userId, Pageable pageable) {
        Specification<Assets> specification = Specification.allOf(
                AssetSpecification.belongsTo(userId),
                AssetSpecification.isDeleted()
        );

        return assetsRepository.findAll(specification, pageable).map(this::toResponse);
    }


    private AssetsResponse toResponse(Assets a) {
        return new AssetsResponse(
                a.getId(),
                a.getName(),
                a.getDescription(),
                a.getAssetCategory().getId(),
                a.getAssetCategory().getName(),
                a.getAcquisition().getId(),
                a.getAcquisition().getName(),
                a.getCreatedAt(),
                a.getUpdatedAt());
    }
}
