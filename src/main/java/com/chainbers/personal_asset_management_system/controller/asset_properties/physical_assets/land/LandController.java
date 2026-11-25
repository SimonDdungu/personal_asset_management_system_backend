package com.chainbers.personal_asset_management_system.controller.asset_properties.physical_assets.land;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.land.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.land.LandService;
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
@RequestMapping("assets/land")
@RequiredArgsConstructor
public class LandController {
    private final LandService landService;

    @GetMapping("/{id}")
    public LandResponse getLand(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return landService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public LandResponse createLand(@Valid @ModelAttribute CreateLandRequest request, @AuthenticationPrincipal User user) {
        return landService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public LandResponse updateLand(
            @PathVariable UUID id, @Valid @RequestBody UpdateLandRequest request, @AuthenticationPrincipal User user) {
        return landService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<LandSearchResponse> searchLand(
            @ModelAttribute LandSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return landService.search(filters, user.getId(), pageable);
    }
}
