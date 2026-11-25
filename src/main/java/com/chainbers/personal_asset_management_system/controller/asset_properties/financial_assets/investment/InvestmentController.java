package com.chainbers.personal_asset_management_system.controller.asset_properties.financial_assets.investment;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.investment.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.investment.InvestmentService;
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
@RequestMapping("assets/investments")
@RequiredArgsConstructor
public class InvestmentController {
    private final InvestmentService investmentService;

    @GetMapping("/{id}")
    public InvestmentResponse getInvestment(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return investmentService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public InvestmentResponse createInvestment(
            @Valid @ModelAttribute CreateInvestmentRequest request, @AuthenticationPrincipal User user) {
        return investmentService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public InvestmentResponse updateInvestment(
            @PathVariable UUID id, @Valid @RequestBody UpdateInvestmentRequest request, @AuthenticationPrincipal User user) {
        return investmentService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<InvestmentSearchResponse> searchInvestments(
            @ModelAttribute InvestmentSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return investmentService.search(filters, user.getId(), pageable);
    }
}
