package com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.stocks;

import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.stocks.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface StocksRepository extends JpaRepository<Stocks, UUID>, JpaSpecificationExecutor<Stocks> {
    List<Stocks> findByAssetsUserId(UUID userId);
}
