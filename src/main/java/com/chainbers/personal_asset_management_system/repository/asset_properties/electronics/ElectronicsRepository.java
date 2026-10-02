package com.chainbers.personal_asset_management_system.repository.asset_properties.electronics;

import com.chainbers.personal_asset_management_system.entity.asset_properties.electronics.Electronics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ElectronicsRepository extends JpaRepository<Electronics, UUID>, JpaSpecificationExecutor<ElectronicsRepository> {
    List<Electronics> findByAssetsNameContainingIgnoreCaseAndAssetsUserId(String assetsName, UUID userId);

    List<Electronics> findBySerialNumberContainingIgnoreCaseAndAssetsUserId(String serialNumber, UUID userId);

    List<Electronics> findByBrandContainingIgnoreCaseAndAssetsUserId(String brand, UUID userId);

    List<Electronics> findByModelContainingIgnoreCaseAndAssetsUserId(String model, UUID userId);

    List<Electronics> findByColorContainingIgnoreCaseAndAssetsUserId(String color, UUID userId);

    List<Electronics> findByCurrentValueBetweenAndAssetsUserId(BigDecimal min, BigDecimal max, UUID userId);

    List<Electronics> findByCurrentValueBetweenAndCurrencyIgnoreCaseAndAssetsUserId(BigDecimal min, BigDecimal max, String currency, UUID userId);

}
