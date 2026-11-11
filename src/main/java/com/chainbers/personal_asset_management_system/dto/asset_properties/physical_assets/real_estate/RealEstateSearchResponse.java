package com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate;

import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageUploadResponse;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.real_estate.PropertySizeUnit;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public record RealEstateSearchResponse(
        UUID id,

        UUID assetId,

        String assetName,

        String address,

        Integer numberBedrooms,

        Integer numberBathrooms,

        Integer numberUnits,

        BigDecimal propertySize,

        PropertySizeUnit propertySizeUnit,

        AssetImageResponse image,

        Currency currency,

        BigDecimal currentValue
) {
}
