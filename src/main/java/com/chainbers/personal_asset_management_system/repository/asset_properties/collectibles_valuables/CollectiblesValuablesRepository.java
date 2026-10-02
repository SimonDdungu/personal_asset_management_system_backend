package com.chainbers.personal_asset_management_system.repository.asset_properties.collectibles_valuables;

import com.chainbers.personal_asset_management_system.entity.asset_properties.collectibles_valuables.CollectiblesValuables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CollectiblesValuablesRepository extends JpaRepository<CollectiblesValuables, UUID>, JpaSpecificationExecutor<CollectiblesValuables> {
    List<CollectiblesValuables> findByAssetsNameContainingIgnoreCaseAndAssetsUserId(String assetsName, UUID userId);

    List<CollectiblesValuables> findByCreatorContainingIgnoreCaseAndAssetsUserId(String creator, UUID userId);

    List<CollectiblesValuables> findBySerialNumberContainingIgnoreCaseAndAssetsUserId(String serialNumber, UUID userId);

    List<CollectiblesValuables> findByMaterialContainingIgnoreCaseAndAssetsUserId(String material, UUID userId);

    List<CollectiblesValuables> findByYearBetweenAndAssetsUserId(int min, int max, UUID userId);

    List<CollectiblesValuables> findByCurrentValueBetweenAndAssetsUserId(BigDecimal min, BigDecimal max, UUID userId);

    List<CollectiblesValuables> findByCurrentValueBetweenAndCurrencyIgnoreCaseAndAssetsUserId(BigDecimal min, BigDecimal max, String currency, UUID userId);
}
