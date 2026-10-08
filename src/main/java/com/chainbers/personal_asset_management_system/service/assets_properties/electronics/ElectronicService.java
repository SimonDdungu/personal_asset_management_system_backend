package com.chainbers.personal_asset_management_system.service.assets_properties.electronics;

import com.chainbers.personal_asset_management_system.dto.asset_properties.electronics.CreateElectronicsRequest;
import com.chainbers.personal_asset_management_system.dto.asset_properties.electronics.ElectronicsResponse;
import com.chainbers.personal_asset_management_system.dto.asset_properties.electronics.UpdateElectronicsRequest;
import com.chainbers.personal_asset_management_system.entity.asset_properties.electronics.Electronics;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.electronics.ElectronicsRepository;
import com.chainbers.personal_asset_management_system.repository.assets.AssetsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ElectronicService {
    private final ElectronicsRepository electronicsRepository;
    private final AssetsRepository assetsRepository;

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
        Electronics electronics = new Electronics();
        electronics.setAssets(findAsset(request.assetId(), userId));
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

        if(request.model() != null && !request.model().isBlank()) {
            electronics.setModel(request.model());
        }

        if(request.color() != null && !request.color().isBlank()) {
            electronics.setColor(request.color());
        }

        if(request.year() != null) {
            electronics.setYear(request.year());
        }

        if(request.serialNumber() != null && !request.serialNumber().isBlank()) {
            electronics.setSerialNumber(request.serialNumber());
        }

        if(request.currency() != null && !request.currency().isBlank()) {
            electronics.setCurrency(request.currency());
        }

        if(request.currentValue() != null) {
            electronics.setCurrentValue(request.currentValue());
            electronics.setCurrentValueUpdatedAt(Instant.now());
        }

        return toResponse(electronics);
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
