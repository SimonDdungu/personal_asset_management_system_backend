package com.chainbers.personal_asset_management_system.controller.asset_properties.financial_assets.loan;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.loan.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.loan.LoansService;
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
@RequestMapping("assets/loans")
@RequiredArgsConstructor
public class LoansController {
    private final LoansService loansService;

    @GetMapping("/{id}")
    public LoansResponse getLoan(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return loansService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public LoansResponse createLoan(@Valid @ModelAttribute CreateLoansRequest request, @AuthenticationPrincipal User user) {
        return loansService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public LoansResponse updateLoan(
            @PathVariable UUID id, @Valid @RequestBody UpdateLoansRequest request, @AuthenticationPrincipal User user) {
        return loansService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<LoansSearchResponse> searchLoans(
            @ModelAttribute LoansSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return loansService.search(filters, user.getId(), pageable);
    }
}
