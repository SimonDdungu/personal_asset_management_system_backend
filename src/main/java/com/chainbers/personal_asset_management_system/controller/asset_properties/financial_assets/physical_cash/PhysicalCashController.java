package com.chainbers.personal_asset_management_system.controller.asset_properties.financial_assets.physical_cash;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.physical_cash.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.physical_cash.PhysicalCashService;
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
@RequestMapping("assets/physical-cash")
@RequiredArgsConstructor
public class PhysicalCashController {
    private final PhysicalCashService physicalCashService;

    @GetMapping("/{id}")
    public PhysicalCashResponse getPhysicalCash(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return physicalCashService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PhysicalCashResponse createPhysicalCash(
            @Valid @ModelAttribute CreatePhysicalCashRequest request, @AuthenticationPrincipal User user) {
        return physicalCashService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public PhysicalCashResponse updatePhysicalCash(
            @PathVariable UUID id, @Valid @RequestBody UpdatePhysicalCashRequest request,
            @AuthenticationPrincipal User user) {
        return physicalCashService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<PhysicalCashSearchResponse> searchPhysicalCash(
            @ModelAttribute PhysicalCashSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return physicalCashService.search(filters, user.getId(), pageable);
    }
}
