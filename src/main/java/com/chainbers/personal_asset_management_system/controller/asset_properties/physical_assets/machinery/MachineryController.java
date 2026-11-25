package com.chainbers.personal_asset_management_system.controller.asset_properties.physical_assets.machinery;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.machinery.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.machinery.MachineryService;
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
@RequestMapping("assets/machinery")
@RequiredArgsConstructor
public class MachineryController {
    private final MachineryService machineryService;

    @GetMapping("/{id}")
    public MachineryResponse getMachinery(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return machineryService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MachineryResponse createMachinery(
            @Valid @ModelAttribute CreateMachineryRequest request, @AuthenticationPrincipal User user) {
        return machineryService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public MachineryResponse updateMachinery(
            @PathVariable UUID id, @Valid @RequestBody UpdateMachineryRequest request, @AuthenticationPrincipal User user) {
        return machineryService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<MachinerySearchResponse> searchMachinery(
            @ModelAttribute MachinerySearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return machineryService.search(filters, user.getId(), pageable);
    }
}
