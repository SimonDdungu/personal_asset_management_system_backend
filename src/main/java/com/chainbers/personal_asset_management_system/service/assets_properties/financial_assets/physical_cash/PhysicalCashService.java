package com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.physical_cash;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.physical_cash.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.physical_cash.PhysicalCash;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.physical_cash.PhysicalCashRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.physical_cash.PhysicalCashSpecification;
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
public class PhysicalCashService {
    private final PhysicalCashRepository physicalCashRepository;
    private final AssetsService assetsService;
    private final AssetDocumentService assetDocumentService;

    private PhysicalCash findPhysicalCash(UUID id, UUID userId) {
        return physicalCashRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Physical cash asset not found: " + id));
    }

    @Transactional(readOnly = true)
    public PhysicalCashResponse getById(UUID id, UUID userId) {
        return toResponse(findPhysicalCash(id, userId));
    }

    @Transactional
    public PhysicalCashResponse create(CreatePhysicalCashRequest request, UUID userId) {
        Assets asset = assetsService.createEntity(request.asset(), userId);
        PhysicalCash physicalCash = new PhysicalCash();
        physicalCash.setAssets(asset);
        PhysicalCash saved = physicalCashRepository.save(physicalCash);
        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public PhysicalCashResponse update(UUID id, UpdatePhysicalCashRequest request, UUID userId) {
        return toResponse(findPhysicalCash(id, userId));
    }

    @Transactional(readOnly = true)
    public Page<PhysicalCashSearchResponse> search(PhysicalCashSearchFilter filters, UUID userId, Pageable pageable) {
        Specification<PhysicalCash> specification = Specification.allOf(
                PhysicalCashSpecification.belongsTo(userId),
                PhysicalCashSpecification.nameContainsAsset(filters.assetName()),
                PhysicalCashSpecification.currencyIs(filters.currency()),
                PhysicalCashSpecification.currentValueFrom(filters.minValue()),
                PhysicalCashSpecification.currentValueTo(filters.maxValue()),
                PhysicalCashSpecification.isNotDeleted()
        );
        return physicalCashRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private PhysicalCashResponse toResponse(PhysicalCash physicalCash) {
        Assets asset = physicalCash.getAssets();
        return new PhysicalCashResponse(
                physicalCash.getId(), asset.getId(), asset.getName(), asset.getAcquisition().getName(),
                asset.getAssetType().getName(), asset.getAssetCategory().getName(), asset.getDescription(),
                asset.getCurrency(), asset.getCurrentValue(), asset.getCurrentValueUpdatedAt(),
                assetDocumentService.getDocumentByAssetId(asset.getId()),
                physicalCash.getCreatedAt(), physicalCash.getUpdatedAt()
        );
    }

    private PhysicalCashSearchResponse toSearchResponse(PhysicalCash physicalCash) {
        Assets asset = physicalCash.getAssets();
        return new PhysicalCashSearchResponse(
                physicalCash.getId(), asset.getId(), asset.getName(), asset.getCurrency(), asset.getCurrentValue()
        );
    }
}
