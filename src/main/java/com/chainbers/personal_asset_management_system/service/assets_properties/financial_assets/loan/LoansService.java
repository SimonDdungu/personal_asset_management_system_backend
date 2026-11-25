package com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.loan;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.loan.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.loan.Loans;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.loan.LoansRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.loan.LoansSpecification;
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
public class LoansService {
    private final LoansRepository loansRepository;
    private final AssetsService assetsService;
    private final AssetDocumentService assetDocumentService;

    private Loans findLoans(UUID id, UUID userId) {
        return loansRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found: " + id));
    }

    @Transactional(readOnly = true)
    public LoansResponse getById(UUID id, UUID userId) {
        return toResponse(findLoans(id, userId));
    }

    @Transactional
    public LoansResponse create(CreateLoansRequest request, UUID userId) {
        Assets asset = assetsService.createEntity(request.asset(), userId);
        Loans loan = new Loans();
        loan.setAssets(asset);
        loan.setLender(request.lender());
        loan.setOriginalAmount(request.originalAmount());
        loan.setInterestRate(request.interestRate());
        loan.setStartDate(request.startDate());
        loan.setMaturityDate(request.maturityDate());
        Loans saved = loansRepository.save(loan);

        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public LoansResponse update(UUID id, UpdateLoansRequest request, UUID userId) {
        Loans loan = findLoans(id, userId);
        if (request.lender() != null && !request.lender().isBlank()) loan.setLender(request.lender());
        if (request.originalAmount() != null && request.originalAmount().compareTo(BigDecimal.ZERO) >= 0) {
            loan.setOriginalAmount(request.originalAmount());
        }
        if (request.interestRate() == null) {
            loan.setInterestRate(null);
        } else if (request.interestRate().compareTo(java.math.BigDecimal.ZERO) >= 0) {
            loan.setInterestRate(request.interestRate());
        }
        if (request.startDate() != null) loan.setStartDate(request.startDate());
        loan.setMaturityDate(request.maturityDate());
        return toResponse(loan);
    }

    @Transactional(readOnly = true)
    public Page<LoansSearchResponse> search(LoansSearchFilter filters, UUID userId, Pageable pageable) {
        Specification<Loans> specification = Specification.allOf(
                LoansSpecification.belongsTo(userId),
                LoansSpecification.nameContainsAsset(filters.assetName()),
                LoansSpecification.nameContainsLender(filters.lender()),
                LoansSpecification.originalAmountFrom(filters.minOriginalAmount()),
                LoansSpecification.originalAmountTo(filters.maxOriginalAmount()),
                LoansSpecification.interestRateFrom(filters.minInterestRate()),
                LoansSpecification.interestRateTo(filters.maxInterestRate()),
                LoansSpecification.startDateFrom(filters.minStartDate()),
                LoansSpecification.startDateTo(filters.maxStartDate()),
                LoansSpecification.maturityDateFrom(filters.minMaturityDate()),
                LoansSpecification.maturityDateTo(filters.maxMaturityDate()),
                LoansSpecification.currencyIs(filters.currency()),
                LoansSpecification.currentValueFrom(filters.minValue()),
                LoansSpecification.currentValueTo(filters.maxValue()),
                LoansSpecification.isNotDeleted()
        );
        return loansRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private LoansResponse toResponse(Loans loan) {
        Assets asset = loan.getAssets();
        return new LoansResponse(
                loan.getId(), asset.getId(), asset.getName(), asset.getAcquisition().getName(),
                asset.getAssetType().getName(), asset.getAssetCategory().getName(),
                loan.getLender(), loan.getOriginalAmount(), loan.getInterestRate(),
                loan.getStartDate(), loan.getMaturityDate(), asset.getCurrency(),
                asset.getCurrentValue(), asset.getCurrentValueUpdatedAt(),
                assetDocumentService.getDocumentByAssetId(asset.getId()),
                loan.getCreatedAt(), loan.getUpdatedAt()
        );
    }

    private LoansSearchResponse toSearchResponse(Loans loan) {
        Assets asset = loan.getAssets();
        return new LoansSearchResponse(
                loan.getId(), asset.getId(), asset.getName(), loan.getLender(),
                loan.getOriginalAmount(), loan.getInterestRate(), loan.getMaturityDate(),
                asset.getCurrency(), asset.getCurrentValue()
        );
    }
}
