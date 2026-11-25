package com.chainbers.personal_asset_management_system.controller.asset_properties.physical_assets.vehicle;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.vehicle.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.vehicle.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("assets/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping("/{id}")
    public VehicleResponse getVehicle(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return vehicleService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse createVehicle(@Valid @ModelAttribute CreateVehicleRequest request, @AuthenticationPrincipal User user) {
        return vehicleService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public VehicleResponse updateVehicle(
            @PathVariable UUID id, @Valid @RequestBody UpdateVehicleRequest request, @AuthenticationPrincipal User user) {
        return vehicleService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<VehicleSearchResponse> searchVehicles(
            @ModelAttribute VehicleSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return vehicleService.search(filters, user.getId(), pageable);
    }
}
