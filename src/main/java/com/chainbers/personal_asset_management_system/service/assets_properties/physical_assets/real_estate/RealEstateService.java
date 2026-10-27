package com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.real_estate;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics.CreateElectronicsRequest;
import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics.ElectronicSearchFilter;
import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics.ElectronicsResponse;
import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate.CreateRealEstateRequest;
import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate.RealEstateResponse;
import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate.RealEstateSearchFilter;
import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate.UpdateRealEstateRequest;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.electronics.Electronics;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.real_estate.RealEstate;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.physical_assets.real_estate.RealEstateRepository;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.electronics.ElectronicSpecification;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.real_estate.RealEstateSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RealEstateService {
    private final RealEstateRepository realEstateRepository;
    private final AssetsService assetsService;

    private RealEstate findRealEstate(UUID id, UUID userId){
        return realEstateRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException(("Real estate not found: " + id)));
    }

    @Transactional(readOnly = true)
    public RealEstateResponse getById(UUID id, UUID userId){
        return toResponse(findRealEstate(id, userId));
    }

    @Transactional
    public RealEstateResponse create(CreateRealEstateRequest request, UUID userId){

        Assets asset = assetsService.createEntity(request.asset(), userId);

        RealEstate realEstate = new RealEstate();

        realEstate.setAssets(asset);

        realEstate.setAddress(request.address());
        realEstate.setDescription(request.description());

        realEstate.setNumberBedrooms(request.numberBedrooms());
        realEstate.setNumberBathrooms(request.numberBathrooms());
        realEstate.setNumberUnits(request.numberUnits());

        realEstate.setPropertySize(request.propertySize());
        realEstate.setPropertySizeUnit(request.propertySizeUnit());

        realEstate.setConstructionCost(request.constructionCost());
        realEstate.setConstructionCurrency(request.constructionCostCurrency());

        realEstate.setConstructionCompletionDate(request.constructionCompletionDate());

        realEstate.setRentalIncome(request.rentalIncome());
        realEstate.setRentalIncomeCurrency(request.rentalIncomeCurrency());

        realEstate.setCurrency(request.currency());
        realEstate.setCurrentValue(request.currentValue());
        realEstate.setCurrentValueUpdatedAt(Instant.now());

        RealEstate savedRealEstate = realEstateRepository.save(realEstate);
        return toResponse(savedRealEstate);
    }

    @Transactional
    public RealEstateResponse update(UUID id, UpdateRealEstateRequest request, UUID userId){

        RealEstate realEstate = findRealEstate(id, userId);

        if(request.address() != null && !request.address().isBlank()){
            realEstate.setAddress(request.address());
        }

        realEstate.setDescription(request.description());

        if(request.numberBedrooms() != null && request.numberBedrooms() >= 0){
            realEstate.setNumberBedrooms(request.numberBedrooms());
        }

        if(request.numberBathrooms() != null && request.numberBathrooms() >= 0){
            realEstate.setNumberBathrooms(request.numberBathrooms());
        }

        if(request.numberUnits() != null && request.numberUnits() >= 0){
            realEstate.setNumberUnits(request.numberUnits());
        }

        if(request.propertySize() != null && request.propertySize().compareTo(BigDecimal.ZERO) >= 0){
            realEstate.setPropertySize(request.propertySize());
        }

        if(request.propertySizeUnit() != null){
            realEstate.setPropertySizeUnit(request.propertySizeUnit());
        }

        if(request.constructionCost() != null && request.constructionCost().compareTo(BigDecimal.ZERO) >= 0){
            realEstate.setConstructionCost(request.constructionCost());
        }

        if(request.constructionCostCurrency() != null){
            realEstate.setConstructionCurrency(request.constructionCostCurrency());
        }


        realEstate.setConstructionCompletionDate(request.constructionCompletionDate());

        if(request.rentalIncome() != null && request.rentalIncome().compareTo(BigDecimal.ZERO) >= 0){
            realEstate.setRentalIncome(request.rentalIncome());
        }

        if(request.rentalIncomeCurrency() != null){
            realEstate.setRentalIncomeCurrency(request.rentalIncomeCurrency());
        }

        if(request.currency() != null){
            realEstate.setCurrency(request.currency());
        }

        if(request.currentValue() != null && request.currentValue().compareTo(BigDecimal.ZERO) >= 0){
            realEstate.setCurrentValue(request.currentValue());
            realEstate.setCurrentValueUpdatedAt(Instant.now());
        }

        realEstate.setUpdatedAt(Instant.now());

        return toResponse(realEstate);
    }


    @Transactional(readOnly = true)
    public Page<RealEstateResponse> search(RealEstateSearchFilter filters, UUID userId, Pageable pageable){
        Specification<RealEstate> specification = Specification.allOf(
                RealEstateSpecification.belongsTo(userId),
                RealEstateSpecification.nameContainsAsset(filters.assetName()),
                RealEstateSpecification.nameContainsAddress(filters.address()),

                RealEstateSpecification.bedroomsFrom(filters.minNumberBedrooms()),
                RealEstateSpecification.bedroomsTo(filters.maxNumberBedrooms()),

                RealEstateSpecification.bathroomsFrom(filters.minNumberBathrooms()),
                RealEstateSpecification.bathroomsTo(filters.maxNumberBathrooms()),

                RealEstateSpecification.numberUnitsFrom(filters.minNumberUnits()),
                RealEstateSpecification.numberUnitsTo(filters.maxNumberUnits()),

                RealEstateSpecification.propertySizeFrom(filters.minPropertySize()),
                RealEstateSpecification.propertySizeTo(filters.maxPropertySize()),
                RealEstateSpecification.propertySizeUnit(filters.propertySizeUnit()),

                RealEstateSpecification.constructionCostFrom(filters.minConstructionCost()),
                RealEstateSpecification.constructionCostTo(filters.maxConstructionCost()),
                RealEstateSpecification.constructionCurrencyIs(filters.constructionCostCurrency()),

                RealEstateSpecification.constructionCompletionDateFrom(filters.minConstructionCompletionDate()),
                RealEstateSpecification.constructionCompletionDateTo(filters.maxConstructionCompletionDate()),

                RealEstateSpecification.rentalIncomeFrom(filters.minRentalIncome()),
                RealEstateSpecification.rentalIncomeTo(filters.maxRentalIncome()),
                RealEstateSpecification.rentalCurrencyIs(filters.rentalIncomeCurrency()),

                RealEstateSpecification.currencyIs(filters.currency()),
                RealEstateSpecification.currentValueFrom(filters.minValue()),
                RealEstateSpecification.currentValueTo(filters.maxValue()),

                RealEstateSpecification.isNotDeleted()
        );

        return realEstateRepository.findAll(specification, pageable).map(this::toResponse);
    }


    private RealEstateResponse toResponse(RealEstate r){
        return new RealEstateResponse(
                r.getId(),
                r.getAssets().getId(),
                r.getAssets().getName(),
                r.getAddress(),
                r.getDescription(),
                r.getNumberBedrooms(),
                r.getNumberBathrooms(),
                r.getNumberUnits(),
                r.getPropertySize(),
                r.getPropertySizeUnit(),
                r.getConstructionCurrency(),
                r.getConstructionCost(),
                r.getConstructionCompletionDate(),
                r.getRentalIncomeCurrency(),
                r.getRentalIncome(),
                r.getCurrency(),
                r.getCurrentValue(),
                r.getCurrentValueUpdatedAt(),
                r.getCreatedAt(),
                r.getUpdatedAt()

        );
    }

}
