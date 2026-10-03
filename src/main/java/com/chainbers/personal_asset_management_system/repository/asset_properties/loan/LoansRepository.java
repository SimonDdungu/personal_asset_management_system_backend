package com.chainbers.personal_asset_management_system.repository.asset_properties.loan;

import com.chainbers.personal_asset_management_system.entity.asset_properties.loan.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LoansRepository extends JpaRepository<Loans, UUID>, JpaSpecificationExecutor<Loans> {
    List<Loans> findByAssetsUserId(UUID userId);
}
