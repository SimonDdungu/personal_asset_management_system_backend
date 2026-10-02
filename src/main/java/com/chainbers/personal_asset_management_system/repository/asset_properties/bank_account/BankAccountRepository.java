package com.chainbers.personal_asset_management_system.repository.asset_properties.bank_account;

import com.chainbers.personal_asset_management_system.entity.asset_properties.bank_account.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID>, JpaSpecificationExecutor<BankAccount> {
    List<BankAccount> findByAssetsNameContainingIgnoreCaseAndAssetsUserId(String assetsName, UUID userId);

    List<BankAccount> findByBankContainingIgnoreCaseAndAssetsUserId(String bank, UUID userId);

    Optional<BankAccount> findByBankAccountNumberAndAssetsUserId(int bankAccNo, UUID userId);

    boolean existsByBankAccountNumberAndAssetsUserId(int bankAccountNumber, UUID userId);

    List<BankAccount> findByCurrentBalanceBetweenAndAssetsUserId(BigDecimal min, BigDecimal max, UUID userId);

    List<BankAccount> findByCurrentBalanceBetweenAndCurrencyIgnoreCaseAndAssetsUserId(BigDecimal min, BigDecimal max, String currency, UUID userId);
}
