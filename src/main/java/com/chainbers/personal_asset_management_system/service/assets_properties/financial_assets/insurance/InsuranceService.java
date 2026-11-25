package com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.insurance;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.insurance.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.insurance.Insurance;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.insurance.InsuranceRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.insurance.InsuranceSpecification;
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
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final AssetsService assetsService;
    private final AssetDocumentService assetDocumentService;

    private Insurance findInsurance(UUID id, UUID userId) {
        return insuranceRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found: " + id));
    }

    @Transactional(readOnly = true)
    public InsuranceResponse getById(UUID id, UUID userId) {
        return toResponse(findInsurance(id, userId));
    }

    @Transactional
    public InsuranceResponse create(CreateInsuranceRequest request, UUID userId) {
        Assets asset = assetsService.createEntity(request.asset(), userId);
        Insurance insurance = new Insurance();
        insurance.setAssets(asset);
        insurance.setProvider(request.provider());
        insurance.setPremiumAmount(request.premiumAmount());
        insurance.setPremiumFrequency(request.premiumFrequency());
        insurance.setStartDate(request.startDate());
        insurance.setEndDate(request.endDate());

        Insurance saved = insuranceRepository.save(insurance);
        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public InsuranceResponse update(UUID id, UpdateInsuranceRequest request, UUID userId) {
        Insurance insurance = findInsurance(id, userId);
        if (request.provider() != null && !request.provider().isBlank()) insurance.setProvider(request.provider());
        if (request.premiumAmount() != null
                && request.premiumAmount().compareTo(BigDecimal.ZERO) >= 0) {
            insurance.setPremiumAmount(request.premiumAmount());
        }
        if (request.premiumFrequency() != null) insurance.setPremiumFrequency(request.premiumFrequency());
        if (request.startDate() != null) insurance.setStartDate(request.startDate());
        if (request.endDate() != null) insurance.setEndDate(request.endDate());
        return toResponse(insurance);
    }

    @Transactional(readOnly = true)
    public Page<InsuranceSearchResponse> search(InsuranceSearchFilter filters, UUID userId, Pageable pageable) {
        Specification<Insurance> specification = Specification.allOf(
                InsuranceSpecification.belongsTo(userId),
                InsuranceSpecification.nameContainsAsset(filters.assetName()),
                InsuranceSpecification.nameContainsProvider(filters.provider()),
                InsuranceSpecification.premiumAmountFrom(filters.minPremiumAmount()),
                InsuranceSpecification.premiumAmountTo(filters.maxPremiumAmount()),
                InsuranceSpecification.premiumFrequencyIs(filters.premiumFrequency()),
                InsuranceSpecification.startDateFrom(filters.minStartDate()),
                InsuranceSpecification.startDateTo(filters.maxStartDate()),
                InsuranceSpecification.endDateFrom(filters.minEndDate()),
                InsuranceSpecification.endDateTo(filters.maxEndDate()),
                InsuranceSpecification.currencyIs(filters.currency()),
                InsuranceSpecification.currentValueFrom(filters.minValue()),
                InsuranceSpecification.currentValueTo(filters.maxValue()),
                InsuranceSpecification.isNotDeleted()
        );
        return insuranceRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private InsuranceResponse toResponse(Insurance insurance) {
        Assets asset = insurance.getAssets();
        return new InsuranceResponse(
                insurance.getId(), asset.getId(), asset.getName(), asset.getAcquisition().getName(),
                asset.getAssetType().getName(), asset.getAssetCategory().getName(),
                insurance.getProvider(), insurance.getPremiumAmount(), insurance.getPremiumFrequency(),
                insurance.getStartDate(), insurance.getEndDate(), asset.getCurrency(),
                asset.getCurrentValue(), asset.getCurrentValueUpdatedAt(),
                assetDocumentService.getDocumentByAssetId(asset.getId()),
                insurance.getCreatedAt(), insurance.getUpdatedAt()
        );
    }

    private InsuranceSearchResponse toSearchResponse(Insurance insurance) {
        Assets asset = insurance.getAssets();
        return new InsuranceSearchResponse(
                insurance.getId(), asset.getId(), asset.getName(), insurance.getProvider(),
                insurance.getPremiumAmount(), insurance.getPremiumFrequency(), insurance.getEndDate(),
                asset.getCurrency(), asset.getCurrentValue()
        );
    }
}
