package com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.bank_account;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.bank_account.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID>, JpaSpecificationExecutor<BankAccount> {
    List<BankAccount> findByAssetsUserId(UUID userId);
}
