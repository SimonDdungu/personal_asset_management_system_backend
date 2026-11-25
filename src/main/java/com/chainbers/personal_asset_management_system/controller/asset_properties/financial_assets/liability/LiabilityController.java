package com.chainbers.personal_asset_management_system.controller.asset_properties.financial_assets.liability;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.liability.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.liability.LiabilityService;
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
@RequestMapping("assets/liabilities")
@RequiredArgsConstructor
public class LiabilityController {
    private final LiabilityService liabilityService;

    @GetMapping("/{id}")
    public LiabilityResponse getLiability(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return liabilityService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public LiabilityResponse createLiability(
            @Valid @ModelAttribute CreateLiabilityRequest request, @AuthenticationPrincipal User user) {
        return liabilityService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public LiabilityResponse updateLiability(
            @PathVariable UUID id, @Valid @RequestBody UpdateLiabilityRequest request, @AuthenticationPrincipal User user) {
        return liabilityService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<LiabilitySearchResponse> searchLiabilities(
            @ModelAttribute LiabilitySearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return liabilityService.search(filters, user.getId(), pageable);
    }
}
