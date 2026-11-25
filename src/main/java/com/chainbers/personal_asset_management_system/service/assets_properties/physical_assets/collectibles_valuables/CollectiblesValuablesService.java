package com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.collectibles_valuables;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.collectibles_valuables.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.collectibles_valuables.CollectiblesValuables;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.physical_assets.collectibles_valuables.CollectiblesValuablesRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.asset_files.images.AssetImageService;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.collectibles_valuables.CollectiblesValuablesSpecification;
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
public class CollectiblesValuablesService {
    private final CollectiblesValuablesRepository collectiblesValuablesRepository;
    private final AssetsService assetsService;
    private final AssetImageService assetImageService;
    private final AssetDocumentService assetDocumentService;

    private CollectiblesValuables findCollectible(UUID id, UUID userId) {
        return collectiblesValuablesRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Collectible or valuable not found: " + id));
    }

    @Transactional(readOnly = true)
    public CollectiblesValuablesResponse getById(UUID id, UUID userId) {
        return toResponse(findCollectible(id, userId));
    }

    @Transactional
    public CollectiblesValuablesResponse create(CreateCollectiblesValuablesRequest request, UUID userId) {
        Assets asset = assetsService.createEntity(request.asset(), userId);
        CollectiblesValuables collectible = new CollectiblesValuables();
        collectible.setAssets(asset);
        collectible.setCreator(request.creator());
        collectible.setSerialNumber(request.serialNumber());
        collectible.setMaterial(request.material());
        if (request.year() == null) {
            collectible.setYear(null);
        } else if (request.year() >= 0) {
            collectible.setYear(request.year());
        }
        CollectiblesValuables saved = collectiblesValuablesRepository.save(collectible);

        if (request.images() != null && !request.images().images().isEmpty()) {
            assetImageService.create(request.images(), asset);
        }
        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public CollectiblesValuablesResponse update(UUID id, UpdateCollectiblesValuablesRequest request, UUID userId) {
        CollectiblesValuables collectible = findCollectible(id, userId);
        collectible.setCreator(request.creator());
        collectible.setSerialNumber(request.serialNumber());
        collectible.setMaterial(request.material());
        collectible.setYear(request.year());
        return toResponse(collectible);
    }

    @Transactional(readOnly = true)
    public Page<CollectiblesValuablesSearchResponse> search(
            CollectiblesValuablesSearchFilter filters, UUID userId, Pageable pageable) {
        Specification<CollectiblesValuables> specification = Specification.allOf(
                CollectiblesValuablesSpecification.belongsTo(userId),
                CollectiblesValuablesSpecification.nameContainsAsset(filters.assetName()),
                CollectiblesValuablesSpecification.nameContainsCreator(filters.creator()),
                CollectiblesValuablesSpecification.nameContainsSerialNumber(filters.serialNumber()),
                CollectiblesValuablesSpecification.nameContainsMaterial(filters.material()),
                CollectiblesValuablesSpecification.yearFrom(filters.minYear()),
                CollectiblesValuablesSpecification.yearTo(filters.maxYear()),
                CollectiblesValuablesSpecification.currencyIs(filters.currency()),
                CollectiblesValuablesSpecification.currentValueFrom(filters.minValue()),
                CollectiblesValuablesSpecification.currentValueTo(filters.maxValue()),
                CollectiblesValuablesSpecification.isNotDeleted()
        );
        return collectiblesValuablesRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private CollectiblesValuablesResponse toResponse(CollectiblesValuables collectible) {
        Assets asset = collectible.getAssets();
        return new CollectiblesValuablesResponse(
                collectible.getId(), asset.getId(), asset.getName(), asset.getAcquisition().getName(),
                asset.getAssetType().getName(), asset.getAssetCategory().getName(),
                collectible.getCreator(), collectible.getSerialNumber(), collectible.getMaterial(),
                collectible.getYear(), asset.getCurrency(), asset.getCurrentValue(),
                asset.getCurrentValueUpdatedAt(), assetImageService.getImagesByAssetId(asset.getId()),
                assetDocumentService.getDocumentByAssetId(asset.getId()),
                collectible.getCreatedAt(), collectible.getUpdatedAt()
        );
    }

    private CollectiblesValuablesSearchResponse toSearchResponse(CollectiblesValuables collectible) {
        Assets asset = collectible.getAssets();
        return new CollectiblesValuablesSearchResponse(
                collectible.getId(), asset.getId(), asset.getName(), collectible.getCreator(),
                collectible.getSerialNumber(), collectible.getMaterial(), collectible.getYear(),
                assetImageService.getPrimaryImageByAssetId(asset.getId()),
                asset.getCurrency(), asset.getCurrentValue()
        );
    }
}
