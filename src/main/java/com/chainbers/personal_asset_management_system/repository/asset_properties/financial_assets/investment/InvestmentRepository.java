package com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.investment;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.investment.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface InvestmentRepository extends JpaRepository<Investment, UUID>, JpaSpecificationExecutor<Investment> {
    List<Investment> findByAssetsUserId(UUID userId);

}
