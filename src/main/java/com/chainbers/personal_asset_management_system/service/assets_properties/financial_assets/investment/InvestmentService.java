package com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.investment;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.investment.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.investment.Investment;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.investment.InvestmentRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.investment.InvestmentSpecification;
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
public class InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final AssetsService assetsService;
    private final AssetDocumentService assetDocumentService;

    private Investment findInvestment(UUID id, UUID userId) {
        return investmentRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Investment not found: " + id));
    }

    @Transactional(readOnly = true)
    public InvestmentResponse getById(UUID id, UUID userId) {
        return toResponse(findInvestment(id, userId));
    }

    @Transactional
    public InvestmentResponse create(CreateInvestmentRequest request, UUID userId) {
        Assets asset = assetsService.createEntity(request.asset(), userId);
        Investment investment = new Investment();
        investment.setAssets(asset);
        investment.setQuantity(request.quantity());
        investment.setInstitution(request.institution());
        investment.setInterestRate(request.interestRate());
        investment.setReferenceNumber(request.referenceNumber());
        Investment saved = investmentRepository.save(investment);

        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public InvestmentResponse update(UUID id, UpdateInvestmentRequest request, UUID userId) {
        Investment investment = findInvestment(id, userId);
        if (request.quantity() != null && request.quantity() >= 0) investment.setQuantity(request.quantity());
        investment.setInstitution(request.institution());
        if (request.interestRate() == null) {
            investment.setInterestRate(null);
        } else if (request.interestRate().compareTo(java.math.BigDecimal.ZERO) >= 0) {
            investment.setInterestRate(request.interestRate());
        }
        investment.setReferenceNumber(request.referenceNumber());
        return toResponse(investment);
    }

    @Transactional(readOnly = true)
    public Page<InvestmentSearchResponse> search(InvestmentSearchFilter filters, UUID userId, Pageable pageable) {
        Specification<Investment> specification = Specification.allOf(
                InvestmentSpecification.belongsTo(userId),
                InvestmentSpecification.nameContainsAsset(filters.assetName()),
                InvestmentSpecification.quantityFrom(filters.minQuantity()),
                InvestmentSpecification.quantityTo(filters.maxQuantity()),
                InvestmentSpecification.nameContainsInstitution(filters.institution()),
                InvestmentSpecification.interestRateFrom(filters.minInterestRate()),
                InvestmentSpecification.interestRateTo(filters.maxInterestRate()),
                InvestmentSpecification.nameContainsReferenceNumber(filters.referenceNumber()),
                InvestmentSpecification.currencyIs(filters.currency()),
                InvestmentSpecification.currentValueFrom(filters.minValue()),
                InvestmentSpecification.currentValueTo(filters.maxValue()),
                InvestmentSpecification.isNotDeleted()
        );
        return investmentRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private InvestmentResponse toResponse(Investment investment) {
        Assets asset = investment.getAssets();
        return new InvestmentResponse(
                investment.getId(), asset.getId(), asset.getName(), asset.getAcquisition().getName(),
                asset.getAssetType().getName(), asset.getAssetCategory().getName(),
                investment.getQuantity(), investment.getInstitution(), investment.getInterestRate(),
                investment.getReferenceNumber(), asset.getCurrency(), asset.getCurrentValue(),
                asset.getCurrentValueUpdatedAt(), assetDocumentService.getDocumentByAssetId(asset.getId()),
                investment.getCreatedAt(), investment.getUpdatedAt()
        );
    }

    private InvestmentSearchResponse toSearchResponse(Investment investment) {
        Assets asset = investment.getAssets();
        return new InvestmentSearchResponse(
                investment.getId(), asset.getId(), asset.getName(), investment.getQuantity(),
                investment.getInstitution(), investment.getInterestRate(), asset.getCurrency(), asset.getCurrentValue()
        );
    }
}
