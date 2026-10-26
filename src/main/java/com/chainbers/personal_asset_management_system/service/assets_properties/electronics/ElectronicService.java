package com.chainbers.personal_asset_management_system.service.assets_properties.electronics;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics.CreateElectronicsRequest;
import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics.ElectronicSearchFilter;
import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics.ElectronicsResponse;
import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics.UpdateElectronicsRequest;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.electronics.Electronics;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.physical_assets.electronics.ElectronicsRepository;
import com.chainbers.personal_asset_management_system.repository.assets.AssetsRepository;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.electronics.ElectronicSpecification;
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
public class ElectronicService {
    private final ElectronicsRepository electronicsRepository;
    private final AssetsRepository assetsRepository;
    private final AssetsService assetsService;

    private Assets findAsset(UUID assetId, UUID userId){
        return assetsRepository.findByIdAndUserIdAndDeletedAtIsNull(assetId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found: " + assetId));
    }

    private Electronics findElectronic(UUID id, UUID userId){
        return electronicsRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException(("Electronic not found: " + id)));
    }

    @Transactional(readOnly = true)
    public ElectronicsResponse getById(UUID id, UUID userId){
        return toResponse(findElectronic(id, userId));
    }

    @Transactional
    public ElectronicsResponse create(CreateElectronicsRequest request, UUID userId){
        Assets asset = assetsService.createEntity(request.asset(), userId);

        Electronics electronics = new Electronics();
        electronics.setAssets(asset);
        electronics.setBrand(request.brand());
        electronics.setModel(request.model());
        electronics.setColor(request.color());
        electronics.setYear(request.year());
        electronics.setSerialNumber(request.serialNumber());
        electronics.setCurrency(request.currency());
        electronics.setCurrentValue(request.currentValue());
        electronics.setCurrentValueUpdatedAt(Instant.now());

        Electronics savedElectronics = electronicsRepository.save(electronics);
        return toResponse(savedElectronics);
    }

    @Transactional
    public ElectronicsResponse update(UUID id, UpdateElectronicsRequest request, UUID userId){
        Electronics electronics = findElectronic(id, userId);

        if(request.brand() != null && !request.brand().isBlank()){
            electronics.setBrand(request.brand());
        }

        electronics.setModel(request.model());

        electronics.setColor(request.color());

        electronics.setYear(request.year());

        electronics.setSerialNumber(request.serialNumber());

        if(request.currency() != null) {
            electronics.setCurrency(request.currency());
        }

        if(request.currentValue() != null && request.currentValue().compareTo(BigDecimal.ZERO) >= 0 ) {
            electronics.setCurrentValue(request.currentValue());
            electronics.setCurrentValueUpdatedAt(Instant.now());
        }

        return toResponse(electronics);
    }

    @Transactional(readOnly = true)
    public Page<ElectronicsResponse> search(ElectronicSearchFilter filters, UUID userId, Pageable pageable){
        Specification<Electronics> specification = Specification.allOf(
                ElectronicSpecification.belongsTo(userId),
                ElectronicSpecification.nameContainsAsset(filters.assetName()),
                ElectronicSpecification.nameContainsBrand(filters.brand()),
                ElectronicSpecification.nameContainsModel(filters.model()),
                ElectronicSpecification.currencyIs(filters.currency()),
                ElectronicSpecification.nameContainsSerialNumber(filters.serialNumber()),
                ElectronicSpecification.nameContainsColor(filters.color()),
                ElectronicSpecification.currentValueFrom(filters.minValue()),
                ElectronicSpecification.currentValueTo(filters.maxValue()),
                ElectronicSpecification.yearFrom(filters.minYear()),
                ElectronicSpecification.yearTo(filters.maxYear()),
                ElectronicSpecification.isNotDeleted()
        );

        return electronicsRepository.findAll(specification, pageable).map(this::toResponse);
    }







    private ElectronicsResponse toResponse(Electronics e){
        return new ElectronicsResponse(
                e.getId(),
                e.getAssets().getId(),
                e.getAssets().getName(),
                e.getBrand(),
                e.getModel(),
                e.getColor(),
                e.getSerialNumber(),
                e.getYear(),
                e.getCurrency(),
                e.getCurrentValue(),
                e.getCurrentValueUpdatedAt(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
