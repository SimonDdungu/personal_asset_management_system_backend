package com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.land;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.land.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.land.Land;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.physical_assets.land.LandRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.asset_files.images.AssetImageService;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.land.LandSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LandService {
    private final LandRepository landRepository;
    private final AssetsService assetsService;
    private final AssetImageService assetImageService;
    private final AssetDocumentService assetDocumentService;

    private Land findLand(UUID id, UUID userId) {
        return landRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Land not found: " + id));
    }

    @Transactional(readOnly = true)
    public LandResponse getById(UUID id, UUID userId) {
        return toResponse(findLand(id, userId));
    }

    @Transactional
    public LandResponse create(CreateLandRequest request, UUID userId) {
        Assets asset = assetsService.createEntity(request.asset(), userId);
        Land land = new Land();
        land.setAssets(asset);
        land.setAddress(request.address());
        land.setDescription(request.description());
        land.setPlotNumber(request.plotNumber());
        land.setLandSize(request.landSize());
        land.setLandSizeUnit(request.landSizeUnit());

        Land saved = landRepository.save(land);
        if (request.images() != null && !request.images().images().isEmpty()) {
            assetImageService.create(request.images(), asset);
        }
        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public LandResponse update(UUID id, UpdateLandRequest request, UUID userId) {
        Land land = findLand(id, userId);
        if (request.address() != null && !request.address().isBlank()) land.setAddress(request.address());
        land.setDescription(request.description());
        if (request.plotNumber() != null && !request.plotNumber().isBlank()) land.setPlotNumber(request.plotNumber());
        if (request.landSize() != null && request.landSize().compareTo(BigDecimal.ZERO) >= 0) {
            land.setLandSize(request.landSize());
        }
        if (request.landSizeUnit() != null) land.setLandSizeUnit(request.landSizeUnit());
        return toResponse(land);
    }

    @Transactional(readOnly = true)
    public Page<LandSearchResponse> search(LandSearchFilter filters, UUID userId, Pageable pageable) {
        Specification<Land> specification = Specification.allOf(
                LandSpecification.belongsTo(userId),
                LandSpecification.nameContainsAsset(filters.assetName()),
                LandSpecification.nameContainsAddress(filters.address()),
                LandSpecification.nameContainsDescription(filters.description()),
                LandSpecification.nameContainsPlotNumber(filters.plotNumber()),
                LandSpecification.landSizeFrom(filters.minLandSize()),
                LandSpecification.landSizeTo(filters.maxLandSize()),
                LandSpecification.landSizeUnitIs(filters.landSizeUnit()),
                LandSpecification.currencyIs(filters.currency()),
                LandSpecification.currentValueFrom(filters.minValue()),
                LandSpecification.currentValueTo(filters.maxValue()),
                LandSpecification.isNotDeleted()
        );
        return landRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private LandResponse toResponse(Land land) {
        Assets asset = land.getAssets();
        return new LandResponse(
                land.getId(), asset.getId(), asset.getName(), asset.getAcquisition().getName(),
                asset.getAssetType().getName(), asset.getAssetCategory().getName(),
                land.getAddress(), land.getDescription(), land.getPlotNumber(),
                land.getLandSize(), land.getLandSizeUnit(), asset.getCurrency(),
                asset.getCurrentValue(), asset.getCurrentValueUpdatedAt(),
                assetImageService.getImagesByAssetId(asset.getId()),
                assetDocumentService.getDocumentByAssetId(asset.getId()),
                land.getCreatedAt(), land.getUpdatedAt()
        );
    }

    private LandSearchResponse toSearchResponse(Land land) {
        Assets asset = land.getAssets();
        return new LandSearchResponse(
                land.getId(), asset.getId(), asset.getName(), land.getAddress(),
                land.getPlotNumber(), land.getLandSize(), land.getLandSizeUnit(),
                assetImageService.getPrimaryImageByAssetId(asset.getId()),
                asset.getCurrency(), asset.getCurrentValue()
        );
    }
}
