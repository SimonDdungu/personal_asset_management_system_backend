package com.chainbers.personal_asset_management_system.repository.asset_properties.retirement_fund;

import com.chainbers.personal_asset_management_system.entity.asset_properties.retirement_fund.RetirementFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface RetirementFundRepository extends JpaRepository<RetirementFund, UUID>, JpaSpecificationExecutor<RetirementFund> {
    List<RetirementFund> findByAssetsUserId(UUID userId);
}
