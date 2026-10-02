package com.chainbers.personal_asset_management_system.repository.asset_properties.furniture;

import com.chainbers.personal_asset_management_system.entity.asset_properties.furniture.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface FurnitureRepository extends JpaRepository<Furniture, UUID>, JpaSpecificationExecutor<Furniture> {
    List<Furniture> findByAssetsNameContainingIgnoreCaseAndAssetsUserId(String assetsName, UUID userId);

    List<Furniture> findByBrandContainingIgnoreCaseAndAssetsUserId(String brand, UUID userId);

    List<Furniture> findByMaterialContainingIgnoreCaseAndAssetsUserId(String material, UUID userId);

    List<Furniture> findByCurrentValueBetweenAndAssetsUserId(BigDecimal min, BigDecimal max, UUID userId);

    List<Furniture> findByCurrentValueBetweenAndCurrencyIgnoreCaseAndAssetsUserId(BigDecimal min, BigDecimal max, String currency, UUID userId);
}
