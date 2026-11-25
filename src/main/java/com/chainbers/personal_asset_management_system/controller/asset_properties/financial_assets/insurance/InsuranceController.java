package com.chainbers.personal_asset_management_system.controller.asset_properties.financial_assets.insurance;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.insurance.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.insurance.InsuranceService;
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
@RequestMapping("assets/insurance")
@RequiredArgsConstructor
public class InsuranceController {
    private final InsuranceService insuranceService;

    @GetMapping("/{id}")
    public InsuranceResponse getInsurance(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return insuranceService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public InsuranceResponse createInsurance(
            @Valid @ModelAttribute CreateInsuranceRequest request, @AuthenticationPrincipal User user) {
        return insuranceService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public InsuranceResponse updateInsurance(
            @PathVariable UUID id, @Valid @RequestBody UpdateInsuranceRequest request, @AuthenticationPrincipal User user) {
        return insuranceService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<InsuranceSearchResponse> searchInsurance(
            @ModelAttribute InsuranceSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return insuranceService.search(filters, user.getId(), pageable);
    }
}
