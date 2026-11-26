package com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.machinery;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.machinery.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.machinery.Machinery;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.physical_assets.machinery.MachineryRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.asset_files.images.AssetImageService;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.machinery.MachinerySpecification;
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
public class MachineryService {
    private final MachineryRepository machineryRepository;
    private final AssetsService assetsService;
    private final AssetImageService assetImageService;
    private final AssetDocumentService assetDocumentService;

    private Machinery findMachinery(UUID id, UUID userId) {
        return machineryRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Machinery not found: " + id));
    }

    @Transactional(readOnly = true)
    public MachineryResponse getById(UUID id, UUID userId) {
        return toResponse(findMachinery(id, userId));
    }

    @Transactional
    public MachineryResponse create(CreateMachineryRequest request, UUID userId) {
        Assets asset = assetsService.createEntity(request.asset(), userId);
        Machinery machinery = new Machinery();
        machinery.setAssets(asset);
        machinery.setBrand(request.brand());
        machinery.setModel(request.model());
        machinery.setMaterial(request.material());
        machinery.setYear(request.year());

        Machinery saved = machineryRepository.save(machinery);
        if (request.images() != null && !request.images().images().isEmpty()) {
            assetImageService.create(request.images(), asset);
        }
        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public MachineryResponse update(UUID id, UpdateMachineryRequest request, UUID userId) {
        Machinery machinery = findMachinery(id, userId);
        machinery.setBrand(request.brand());
        machinery.setModel(request.model());
        machinery.setMaterial(request.material());
        if (request.year() == null) {
            machinery.setYear(null);
        } else if (request.year() >= 0) {
            machinery.setYear(request.year());
        }
        return toResponse(machinery);
    }

    @Transactional(readOnly = true)
    public Page<MachinerySearchResponse> search(MachinerySearchFilter filters, UUID userId, Pageable pageable) {
        Specification<Machinery> specification = Specification.allOf(
                MachinerySpecification.belongsTo(userId),
                MachinerySpecification.nameContainsAsset(filters.assetName()),
                MachinerySpecification.nameContainsBrand(filters.brand()),
                MachinerySpecification.nameContainsModel(filters.model()),
                MachinerySpecification.nameContainsMaterial(filters.material()),
                MachinerySpecification.yearFrom(filters.minYear()),
                MachinerySpecification.yearTo(filters.maxYear()),
                MachinerySpecification.currencyIs(filters.currency()),
                MachinerySpecification.currentValueFrom(filters.minValue()),
                MachinerySpecification.currentValueTo(filters.maxValue()),
                MachinerySpecification.isNotDeleted()
        );
        return machineryRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private MachineryResponse toResponse(Machinery machinery) {
        Assets asset = machinery.getAssets();
        return new MachineryResponse(
                machinery.getId(), asset.getId(), asset.getName(), asset.getAcquisition().getName(),
                asset.getAssetType().getName(), asset.getAssetCategory().getName(), asset.getDescription(),
                machinery.getBrand(), machinery.getModel(), machinery.getMaterial(), machinery.getYear(),
                asset.getCurrency(), asset.getCurrentValue(), asset.getCurrentValueUpdatedAt(),
                assetImageService.getImagesByAssetId(asset.getId()),
                assetDocumentService.getDocumentByAssetId(asset.getId()),
                machinery.getCreatedAt(), machinery.getUpdatedAt()
        );
    }

    private MachinerySearchResponse toSearchResponse(Machinery machinery) {
        Assets asset = machinery.getAssets();
        return new MachinerySearchResponse(
                machinery.getId(), asset.getId(), asset.getName(), machinery.getBrand(),
                machinery.getModel(), machinery.getMaterial(), machinery.getYear(),
                assetImageService.getPrimaryImageByAssetId(asset.getId()),
                asset.getCurrency(), asset.getCurrentValue()
        );
    }
}
