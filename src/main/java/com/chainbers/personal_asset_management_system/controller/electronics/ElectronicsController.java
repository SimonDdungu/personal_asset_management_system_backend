package com.chainbers.personal_asset_management_system.controller.electronics;

import com.chainbers.personal_asset_management_system.dto.asset_properties.electronics.CreateElectronicsRequest;
import com.chainbers.personal_asset_management_system.dto.asset_properties.electronics.ElectronicsResponse;
import com.chainbers.personal_asset_management_system.dto.asset_properties.electronics.UpdateElectronicsRequest;
import com.chainbers.personal_asset_management_system.service.assets_properties.electronics.ElectronicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("assets/electronics")
@RequiredArgsConstructor
public class ElectronicsController {
    private final ElectronicService electronicService;

    @GetMapping("/{id}")
    public ElectronicsResponse getElectronic(@PathVariable UUID id, @RequestParam UUID userId){
        return electronicService.getById(id, userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ElectronicsResponse createElectronic(@Valid @RequestBody CreateElectronicsRequest request, @RequestParam UUID userId){
        return electronicService.create(request, userId);
    }

    @PatchMapping("/{id}")
    public ElectronicsResponse updateElectronic(@PathVariable UUID id, @RequestBody UpdateElectronicsRequest request, @RequestParam UUID userId){
        return electronicService.update(id, request, userId);
    }





}
