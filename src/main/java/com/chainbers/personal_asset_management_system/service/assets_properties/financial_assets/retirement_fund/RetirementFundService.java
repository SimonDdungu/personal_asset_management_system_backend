package com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.retirement_fund;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.retirement_fund.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.retirement_fund.RetirementFund;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.retirement_fund.RetirementFundRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.retirement_fund.RetirementFundSpecification;
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
public class RetirementFundService {
    private final RetirementFundRepository retirementFundRepository;
    private final AssetsService assetsService;
    private final AssetDocumentService assetDocumentService;

    private RetirementFund findRetirementFund(UUID id, UUID userId) {
        return retirementFundRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Retirement fund not found: " + id));
    }

    @Transactional(readOnly = true)
    public RetirementFundResponse getById(UUID id, UUID userId) {
        return toResponse(findRetirementFund(id, userId));
    }

    @Transactional
    public RetirementFundResponse create(CreateRetirementFundRequest request, UUID userId) {
        Assets asset = assetsService.createEntity(request.asset(), userId);
        RetirementFund retirementFund = new RetirementFund();
        retirementFund.setAssets(asset);
        retirementFund.setProvider(request.provider());
        RetirementFund saved = retirementFundRepository.save(retirementFund);
        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public RetirementFundResponse update(UUID id, UpdateRetirementFundRequest request, UUID userId) {
        RetirementFund retirementFund = findRetirementFund(id, userId);
        if (request.provider() != null && !request.provider().isBlank()) {
            retirementFund.setProvider(request.provider());
        }
        return toResponse(retirementFund);
    }

    @Transactional(readOnly = true)
    public Page<RetirementFundSearchResponse> search(
            RetirementFundSearchFilter filters, UUID userId, Pageable pageable) {
        Specification<RetirementFund> specification = Specification.allOf(
                RetirementFundSpecification.belongsTo(userId),
                RetirementFundSpecification.nameContainsAsset(filters.assetName()),
                RetirementFundSpecification.nameContainsProvider(filters.provider()),
                RetirementFundSpecification.currencyIs(filters.currency()),
                RetirementFundSpecification.currentValueFrom(filters.minValue()),
                RetirementFundSpecification.currentValueTo(filters.maxValue()),
                RetirementFundSpecification.isNotDeleted()
        );
        return retirementFundRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private RetirementFundResponse toResponse(RetirementFund retirementFund) {
        Assets asset = retirementFund.getAssets();
        return new RetirementFundResponse(
                retirementFund.getId(), asset.getId(), asset.getName(), asset.getAcquisition().getName(),
                asset.getAssetType().getName(), asset.getAssetCategory().getName(),
                retirementFund.getProvider(), asset.getCurrency(), asset.getCurrentValue(),
                asset.getCurrentValueUpdatedAt(), assetDocumentService.getDocumentByAssetId(asset.getId()),
                retirementFund.getCreatedAt(), retirementFund.getUpdatedAt()
        );
    }

    private RetirementFundSearchResponse toSearchResponse(RetirementFund retirementFund) {
        Assets asset = retirementFund.getAssets();
        return new RetirementFundSearchResponse(
                retirementFund.getId(), asset.getId(), asset.getName(), retirementFund.getProvider(),
                asset.getCurrency(), asset.getCurrentValue()
        );
    }
}
