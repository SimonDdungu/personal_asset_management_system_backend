package com.chainbers.personal_asset_management_system.controller.asset_properties.financial_assets.savings;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.savings.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.savings.SavingsService;
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
@RequestMapping("assets/savings")
@RequiredArgsConstructor
public class SavingsController {
    private final SavingsService savingsService;

    @GetMapping("/{id}")
    public SavingsResponse getSavings(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return savingsService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SavingsResponse createSavings(
            @Valid @ModelAttribute CreateSavingsRequest request, @AuthenticationPrincipal User user) {
        return savingsService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public SavingsResponse updateSavings(
            @PathVariable UUID id, @Valid @RequestBody UpdateSavingsRequest request, @AuthenticationPrincipal User user) {
        return savingsService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<SavingsSearchResponse> searchSavings(
            @ModelAttribute SavingsSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return savingsService.search(filters, user.getId(), pageable);
    }
}
