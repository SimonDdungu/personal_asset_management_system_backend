package com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.vehicle;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.vehicle.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.vehicle.Vehicle;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.physical_assets.vehicle.VehicleRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.asset_files.images.AssetImageService;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.physical_assets.vehicle.VehicleSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final AssetsService assetsService;
    private final AssetImageService assetImageService;
    private final AssetDocumentService assetDocumentService;

    private Vehicle findVehicle(UUID id, UUID userId) {
        return vehicleRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found: " + id));
    }

    @Transactional(readOnly = true)
    public VehicleResponse getById(UUID id, UUID userId) {
        return toResponse(findVehicle(id, userId));
    }

    @Transactional
    public VehicleResponse create(CreateVehicleRequest request, UUID userId) {
        Assets asset = assetsService.createEntity(request.asset(), userId);
        Vehicle vehicle = new Vehicle();
        vehicle.setAssets(asset);
        vehicle.setMake(request.make());
        vehicle.setModel(request.model());
        vehicle.setYear(request.year());
        vehicle.setColor(request.color());
        vehicle.setMileage(request.mileage());
        vehicle.setRegistrationNumber(request.registrationNumber());
        vehicle.setVin(request.vin());

        Vehicle saved = vehicleRepository.save(vehicle);
        if (request.images() != null && !request.images().images().isEmpty()) {
            assetImageService.create(request.images(), asset);
        }
        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public VehicleResponse update(UUID id, UpdateVehicleRequest request, UUID userId) {
        Vehicle vehicle = findVehicle(id, userId);
        if (request.make() != null && !request.make().isBlank()) vehicle.setMake(request.make());
        if (request.model() != null && !request.model().isBlank()) vehicle.setModel(request.model());
        if (request.year() != null && request.year() >= 0) vehicle.setYear(request.year());
        if (request.color() != null && !request.color().isBlank()) vehicle.setColor(request.color());
        if (request.mileage() == null) {
            vehicle.setMileage(null);
        } else if (request.mileage() >= 0) {
            vehicle.setMileage(request.mileage());
        }
        vehicle.setRegistrationNumber(request.registrationNumber());
        vehicle.setVin(request.vin());
        return toResponse(vehicle);
    }

    @Transactional(readOnly = true)
    public Page<VehicleSearchResponse> search(VehicleSearchFilter filters, UUID userId, Pageable pageable) {
        Specification<Vehicle> specification = Specification.allOf(
                VehicleSpecification.belongsTo(userId),
                VehicleSpecification.nameContainsAsset(filters.assetName()),
                VehicleSpecification.nameContainsMake(filters.make()),
                VehicleSpecification.nameContainsModel(filters.model()),
                VehicleSpecification.yearFrom(filters.minYear()),
                VehicleSpecification.yearTo(filters.maxYear()),
                VehicleSpecification.nameContainsColor(filters.color()),
                VehicleSpecification.mileageFrom(filters.minMileage()),
                VehicleSpecification.mileageTo(filters.maxMileage()),
                VehicleSpecification.nameContainsRegistrationNumber(filters.registrationNumber()),
                VehicleSpecification.nameContainsVin(filters.vin()),
                VehicleSpecification.currencyIs(filters.currency()),
                VehicleSpecification.currentValueFrom(filters.minValue()),
                VehicleSpecification.currentValueTo(filters.maxValue()),
                VehicleSpecification.isNotDeleted()
        );
        return vehicleRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private VehicleResponse toResponse(Vehicle vehicle) {
        Assets asset = vehicle.getAssets();
        return new VehicleResponse(
                vehicle.getId(), asset.getId(), asset.getName(), asset.getAcquisition().getName(),
                asset.getAssetType().getName(), asset.getAssetCategory().getName(),
                vehicle.getMake(), vehicle.getModel(), vehicle.getYear(), vehicle.getColor(),
                vehicle.getMileage(), vehicle.getRegistrationNumber(), vehicle.getVin(),
                asset.getCurrency(), asset.getCurrentValue(), asset.getCurrentValueUpdatedAt(),
                assetImageService.getImagesByAssetId(asset.getId()),
                assetDocumentService.getDocumentByAssetId(asset.getId()),
                vehicle.getCreatedAt(), vehicle.getUpdatedAt()
        );
    }

    private VehicleSearchResponse toSearchResponse(Vehicle vehicle) {
        Assets asset = vehicle.getAssets();
        return new VehicleSearchResponse(
                vehicle.getId(), asset.getId(), asset.getName(), vehicle.getMake(), vehicle.getModel(),
                vehicle.getYear(), vehicle.getColor(), vehicle.getMileage(),
                assetImageService.getPrimaryImageByAssetId(asset.getId()),
                asset.getCurrency(), asset.getCurrentValue()
        );
    }
}
