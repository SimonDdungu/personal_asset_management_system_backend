package com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.furniture;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.furniture.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.furniture.Furniture;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.physical_assets.furniture.FurnitureRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.asset_files.images.AssetImageService;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.furniture.FurnitureSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FurnitureService {
    private final FurnitureRepository furnitureRepository;
    private final AssetsService assetsService;
    private final AssetImageService assetImageService;
    private final AssetDocumentService assetDocumentService;

    private Furniture findFurniture(UUID id, UUID userId) {
        return furnitureRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Furniture not found: " + id));
    }

    @Transactional(readOnly = true)
    public FurnitureResponse getById(UUID id, UUID userId) {
        return toResponse(findFurniture(id, userId));
    }

    @Transactional
    public FurnitureResponse create(CreateFurnitureRequest request, UUID userId) {
        Assets asset = assetsService.createEntity(request.asset(), userId);
        Furniture furniture = new Furniture();
        furniture.setAssets(asset);
        furniture.setBrand(request.brand());
        furniture.setMaterial(request.material());
        furniture.setDescription(request.description());
        Furniture saved = furnitureRepository.save(furniture);

        if (request.images() != null && !request.images().images().isEmpty()) {
            assetImageService.create(request.images(), asset);
        }
        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public FurnitureResponse update(UUID id, UpdateFurnitureRequest request, UUID userId) {
        Furniture furniture = findFurniture(id, userId);
        furniture.setBrand(request.brand());
        furniture.setMaterial(request.material());
        furniture.setDescription(request.description());
        return toResponse(furniture);
    }

    @Transactional(readOnly = true)
    public Page<FurnitureSearchResponse> search(FurnitureSearchFilter filters, UUID userId, Pageable pageable) {
        Specification<Furniture> specification = Specification.allOf(
                FurnitureSpecification.belongsTo(userId),
                FurnitureSpecification.nameContainsAsset(filters.assetName()),
                FurnitureSpecification.nameContainsBrand(filters.brand()),
                FurnitureSpecification.nameContainsMaterial(filters.material()),
                FurnitureSpecification.nameContainsDescription(filters.description()),
                FurnitureSpecification.currencyIs(filters.currency()),
                FurnitureSpecification.currentValueFrom(filters.minValue()),
                FurnitureSpecification.currentValueTo(filters.maxValue()),
                FurnitureSpecification.isNotDeleted()
        );
        return furnitureRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private FurnitureResponse toResponse(Furniture furniture) {
        Assets asset = furniture.getAssets();
        return new FurnitureResponse(
                furniture.getId(), asset.getId(), asset.getName(), asset.getAcquisition().getName(),
                asset.getAssetType().getName(), asset.getAssetCategory().getName(),
                furniture.getBrand(), furniture.getMaterial(), furniture.getAssets().getDescription(),
                asset.getCurrency(), asset.getCurrentValue(), asset.getCurrentValueUpdatedAt(),
                assetImageService.getImagesByAssetId(asset.getId()),
                assetDocumentService.getDocumentByAssetId(asset.getId()),
                furniture.getCreatedAt(), furniture.getUpdatedAt()
        );
    }

    private FurnitureSearchResponse toSearchResponse(Furniture furniture) {
        Assets asset = furniture.getAssets();
        return new FurnitureSearchResponse(
                furniture.getId(), asset.getId(), asset.getName(), furniture.getBrand(), furniture.getMaterial(),
                assetImageService.getPrimaryImageByAssetId(asset.getId()),
                asset.getCurrency(), asset.getCurrentValue()
        );
    }
}
