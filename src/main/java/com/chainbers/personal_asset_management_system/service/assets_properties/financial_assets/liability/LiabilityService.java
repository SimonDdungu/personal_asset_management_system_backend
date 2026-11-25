package com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.liability;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.liability.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.liability.Liability;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.liability.LiabilityRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.liability.LiabilitySpecification;
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
public class LiabilityService {
    private final LiabilityRepository liabilityRepository;
    private final AssetsService assetsService;
    private final AssetDocumentService assetDocumentService;

    private Liability findLiability(UUID id, UUID userId) {
        return liabilityRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Liability not found: " + id));
    }

    @Transactional(readOnly = true)
    public LiabilityResponse getById(UUID id, UUID userId) {
        return toResponse(findLiability(id, userId));
    }

    @Transactional
    public LiabilityResponse create(CreateLiabilityRequest request, UUID userId) {
        Assets asset = assetsService.createEntity(request.asset(), userId);
        Liability liability = new Liability();
        liability.setAssets(asset);
        liability.setCreditor(request.creditor());
        liability.setDueDate(request.dueDate());
        liability.setDescription(request.description());
        Liability saved = liabilityRepository.save(liability);

        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public LiabilityResponse update(UUID id, UpdateLiabilityRequest request, UUID userId) {
        Liability liability = findLiability(id, userId);
        if (request.creditor() != null && !request.creditor().isBlank()) liability.setCreditor(request.creditor());
        if (request.dueDate() != null) liability.setDueDate(request.dueDate());
        liability.setDescription(request.description());
        return toResponse(liability);
    }

    @Transactional(readOnly = true)
    public Page<LiabilitySearchResponse> search(LiabilitySearchFilter filters, UUID userId, Pageable pageable) {
        Specification<Liability> specification = Specification.allOf(
                LiabilitySpecification.belongsTo(userId),
                LiabilitySpecification.nameContainsAsset(filters.assetName()),
                LiabilitySpecification.nameContainsCreditor(filters.creditor()),
                LiabilitySpecification.dueDateFrom(filters.minDueDate()),
                LiabilitySpecification.dueDateTo(filters.maxDueDate()),
                LiabilitySpecification.nameContainsDescription(filters.description()),
                LiabilitySpecification.currencyIs(filters.currency()),
                LiabilitySpecification.currentValueFrom(filters.minValue()),
                LiabilitySpecification.currentValueTo(filters.maxValue()),
                LiabilitySpecification.isNotDeleted()
        );
        return liabilityRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private LiabilityResponse toResponse(Liability liability) {
        Assets asset = liability.getAssets();
        return new LiabilityResponse(
                liability.getId(), asset.getId(), asset.getName(), asset.getAcquisition().getName(),
                asset.getAssetType().getName(), asset.getAssetCategory().getName(),
                liability.getCreditor(), liability.getDueDate(), liability.getDescription(),
                asset.getCurrency(), asset.getCurrentValue(), asset.getCurrentValueUpdatedAt(),
                assetDocumentService.getDocumentByAssetId(asset.getId()),
                liability.getCreatedAt(), liability.getUpdatedAt()
        );
    }

    private LiabilitySearchResponse toSearchResponse(Liability liability) {
        Assets asset = liability.getAssets();
        return new LiabilitySearchResponse(
                liability.getId(), asset.getId(), asset.getName(), liability.getCreditor(),
                liability.getDueDate(), asset.getCurrency(), asset.getCurrentValue()
        );
    }
}
