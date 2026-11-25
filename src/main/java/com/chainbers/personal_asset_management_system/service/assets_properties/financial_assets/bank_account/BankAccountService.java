package com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.bank_account;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.bank_account.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.bank_account.BankAccount;
import com.chainbers.personal_asset_management_system.entity.assets.AssetValutionHistory;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.bank_account.BankAccountRepository;
import com.chainbers.personal_asset_management_system.repository.assets.AssetValutionHistoryRepository;
import com.chainbers.personal_asset_management_system.repository.assets.AssetsRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.bank_account.BankAccountSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final AssetsRepository assetsRepository;
    private final AssetValutionHistoryRepository assetValutionHistoryRepository;
    private final AssetDocumentService assetDocumentService;

    private BankAccount findBankAccount(UUID id, UUID userId) {
        return bankAccountRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Bank account not found: " + id));
    }

    @Transactional(readOnly = true)
    public BankAccountResponse getById(UUID id, UUID userId) {
        return toResponse(findBankAccount(id, userId));
    }

    @Transactional
    public BankAccountResponse create(CreateBankAccountRequest request, UUID userId) {
        Assets asset = assetsRepository.findByIdAndUserIdAndDeletedAtIsNull(request.assetId(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found: " + request.assetId()));
        Currency currency = Currency.getInstance(request.currency());
        setAssetValue(asset, request.currentBalance(), currency);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAssets(asset);
        bankAccount.setBank(request.bank());
        bankAccount.setBankAccountNumber(request.bankAccountNumber());
        bankAccount.setInterestRate(request.interestRate());
        bankAccount.setPreviousBalance(request.previousBalance());
        BankAccount saved = bankAccountRepository.save(bankAccount);

        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public BankAccountResponse update(UUID id, UpdateBankAccountRequest request, UUID userId) {
        BankAccount bankAccount = findBankAccount(id, userId);
        if (request.bank() != null && !request.bank().isBlank()) bankAccount.setBank(request.bank());
        if (request.bankAccountNumber() != null && !request.bankAccountNumber().isBlank()) {
            bankAccount.setBankAccountNumber(request.bankAccountNumber());
        }
        if (request.interestRate() == null) {
            bankAccount.setInterestRate(null);
        } else if (request.interestRate().compareTo(BigDecimal.ZERO) >= 0) {
            bankAccount.setInterestRate(request.interestRate());
        }

        Assets asset = bankAccount.getAssets();
        boolean hasCurrency = request.currency() != null && !request.currency().isBlank();
        Currency currency = hasCurrency ? Currency.getInstance(request.currency()) : asset.getCurrency();
        BigDecimal currentBalance = request.currentBalance() != null
                && request.currentBalance().compareTo(BigDecimal.ZERO) >= 0
                ? request.currentBalance()
                : asset.getCurrentValue();
        if (hasCurrency || (request.currentBalance() != null
                && request.currentBalance().compareTo(BigDecimal.ZERO) >= 0)) {
            setAssetValue(asset, currentBalance, currency);
        }
        return toResponse(bankAccount);
    }

    @Transactional(readOnly = true)
    public Page<BankAccountSearchResponse> search(BankAccountSearchFilter filters, UUID userId, Pageable pageable) {
        Specification<BankAccount> specification = Specification.allOf(
                BankAccountSpecification.belongsTo(userId),
                BankAccountSpecification.nameContainsAsset(filters.assetName()),
                BankAccountSpecification.nameContainsBank(filters.bank()),
                BankAccountSpecification.nameContainsBankAccountNumber(filters.bankAccountNumber()),
                BankAccountSpecification.interestRateFrom(filters.minInterestRate()),
                BankAccountSpecification.interestRateTo(filters.maxInterestRate()),
                BankAccountSpecification.previousBalanceFrom(filters.minPreviousBalance()),
                BankAccountSpecification.previousBalanceTo(filters.maxPreviousBalance()),
                BankAccountSpecification.currencyIs(toCurrency(filters.currency())),
                BankAccountSpecification.currentValueFrom(filters.minValue()),
                BankAccountSpecification.currentValueTo(filters.maxValue()),
                BankAccountSpecification.isNotDeleted()
        );
        return bankAccountRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private Currency toCurrency(String currency) {
        return currency == null || currency.isBlank() ? null : Currency.getInstance(currency);
    }

    private void setAssetValue(Assets asset, BigDecimal value, Currency currency) {
        boolean valueChanged = asset.getCurrentValue().compareTo(value) != 0;
        asset.setCurrency(currency);
        if (valueChanged) {
            asset.setCurrentValue(value);
            asset.setCurrentValueUpdatedAt(Instant.now());
            AssetValutionHistory history = new AssetValutionHistory();
            history.setAsset(asset);
            history.setValue(value);
            assetValutionHistoryRepository.save(history);
        }
        assetsRepository.save(asset);
    }

    private BankAccountResponse toResponse(BankAccount bankAccount) {
        Assets asset = bankAccount.getAssets();
        return new BankAccountResponse(
                bankAccount.getId(), asset.getId(), asset.getName(),
                bankAccount.getBank(), bankAccount.getBankAccountNumber(), bankAccount.getInterestRate(),
                asset.getCurrency().getCurrencyCode(), asset.getCurrentValue(), bankAccount.getPreviousBalance(),
                asset.getCurrentValueUpdatedAt(), assetDocumentService.getDocumentByAssetId(asset.getId()),
                bankAccount.getCreatedAt(), bankAccount.getUpdatedAt()
        );
    }

    private BankAccountSearchResponse toSearchResponse(BankAccount bankAccount) {
        Assets asset = bankAccount.getAssets();
        return new BankAccountSearchResponse(
                bankAccount.getId(), asset.getId(), asset.getName(), bankAccount.getBank(),
                bankAccount.getInterestRate(), asset.getCurrency().getCurrencyCode(), asset.getCurrentValue()
        );
    }
}
