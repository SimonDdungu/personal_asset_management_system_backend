package com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.savings;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.savings.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.savings.Savings;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.savings.SavingsRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.savings.SavingsSpecification;
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
public class SavingsService {
    private final SavingsRepository savingsRepository;
    private final AssetsService assetsService;
    private final AssetDocumentService assetDocumentService;

    private Savings findSavings(UUID id, UUID userId) {
        return savingsRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Savings asset not found: " + id));
    }

    @Transactional(readOnly = true)
    public SavingsResponse getById(UUID id, UUID userId) {
        return toResponse(findSavings(id, userId));
    }

    @Transactional
    public SavingsResponse create(CreateSavingsRequest request, UUID userId) {
        Assets asset = assetsService.createEntity(request.asset(), userId);
        Savings savings = new Savings();
        savings.setAssets(asset);
        savings.setInstitution(request.institution());
        savings.setAccountNumber(request.accountNumber());
        savings.setInterestRate(request.interestRate());
        savings.setMaturityDate(request.maturityDate());
        Savings saved = savingsRepository.save(savings);
        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public SavingsResponse update(UUID id, UpdateSavingsRequest request, UUID userId) {
        Savings savings = findSavings(id, userId);
        if (request.institution() != null && !request.institution().isBlank()) {
            savings.setInstitution(request.institution());
        }
        if (request.accountNumber() != null && !request.accountNumber().isBlank()) {
            savings.setAccountNumber(request.accountNumber());
        }
        if (request.interestRate() == null) {
            savings.setInterestRate(null);
        } else if (request.interestRate().compareTo(java.math.BigDecimal.ZERO) >= 0) {
            savings.setInterestRate(request.interestRate());
        }
        savings.setMaturityDate(request.maturityDate());
        return toResponse(savings);
    }

    @Transactional(readOnly = true)
    public Page<SavingsSearchResponse> search(SavingsSearchFilter filters, UUID userId, Pageable pageable) {
        Specification<Savings> specification = Specification.allOf(
                SavingsSpecification.belongsTo(userId),
                SavingsSpecification.nameContainsAsset(filters.assetName()),
                SavingsSpecification.nameContainsInstitution(filters.institution()),
                SavingsSpecification.nameContainsAccountNumber(filters.accountNumber()),
                SavingsSpecification.interestRateFrom(filters.minInterestRate()),
                SavingsSpecification.interestRateTo(filters.maxInterestRate()),
                SavingsSpecification.maturityDateFrom(filters.minMaturityDate()),
                SavingsSpecification.maturityDateTo(filters.maxMaturityDate()),
                SavingsSpecification.currencyIs(filters.currency()),
                SavingsSpecification.currentValueFrom(filters.minValue()),
                SavingsSpecification.currentValueTo(filters.maxValue()),
                SavingsSpecification.isNotDeleted()
        );
        return savingsRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private SavingsResponse toResponse(Savings savings) {
        Assets asset = savings.getAssets();
        return new SavingsResponse(
                savings.getId(), asset.getId(), asset.getName(), asset.getAcquisition().getName(),
                asset.getAssetType().getName(), asset.getAssetCategory().getName(),
                savings.getInstitution(), savings.getAccountNumber(), savings.getInterestRate(),
                savings.getMaturityDate(), asset.getCurrency(), asset.getCurrentValue(),
                asset.getCurrentValueUpdatedAt(), assetDocumentService.getDocumentByAssetId(asset.getId()),
                savings.getCreatedAt(), savings.getUpdatedAt()
        );
    }

    private SavingsSearchResponse toSearchResponse(Savings savings) {
        Assets asset = savings.getAssets();
        return new SavingsSearchResponse(
                savings.getId(), asset.getId(), asset.getName(), savings.getInstitution(),
                savings.getInterestRate(), savings.getMaturityDate(), asset.getCurrency(), asset.getCurrentValue()
        );
    }
}
