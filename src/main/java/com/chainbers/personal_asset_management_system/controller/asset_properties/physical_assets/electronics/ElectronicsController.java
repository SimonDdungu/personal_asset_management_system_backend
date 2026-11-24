package com.chainbers.personal_asset_management_system.controller.asset_properties.physical_assets.electronics;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.electronics.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.electronics.ElectronicService;
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
@RequestMapping("assets/electronics")
@RequiredArgsConstructor
public class ElectronicsController {
    private final ElectronicService electronicService;

    @GetMapping("/{id}")
    public ElectronicsResponse getElectronic(@PathVariable UUID id, @AuthenticationPrincipal User user){
        return electronicService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ElectronicsResponse createElectronic(@Valid @ModelAttribute  CreateElectronicsRequest request, @AuthenticationPrincipal User user){
        return electronicService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public ElectronicsResponse updateElectronic(@PathVariable UUID id, @RequestBody UpdateElectronicsRequest request, @AuthenticationPrincipal User user){
        return electronicService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<ElectronicSearchResponse> searchElectronics(ElectronicSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return electronicService.search(filters, user.getId(), pageable);
    }

}
