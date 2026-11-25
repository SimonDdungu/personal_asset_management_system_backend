package com.chainbers.personal_asset_management_system.controller.asset_properties.financial_assets.retirement_fund;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.retirement_fund.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.retirement_fund.RetirementFundService;
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
@RequestMapping("assets/retirement-funds")
@RequiredArgsConstructor
public class RetirementFundController {
    private final RetirementFundService retirementFundService;

    @GetMapping("/{id}")
    public RetirementFundResponse getRetirementFund(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return retirementFundService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RetirementFundResponse createRetirementFund(
            @Valid @ModelAttribute CreateRetirementFundRequest request, @AuthenticationPrincipal User user) {
        return retirementFundService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public RetirementFundResponse updateRetirementFund(
            @PathVariable UUID id, @Valid @RequestBody UpdateRetirementFundRequest request,
            @AuthenticationPrincipal User user) {
        return retirementFundService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<RetirementFundSearchResponse> searchRetirementFunds(
            @ModelAttribute RetirementFundSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return retirementFundService.search(filters, user.getId(), pageable);
    }
}
