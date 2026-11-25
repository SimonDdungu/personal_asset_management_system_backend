package com.chainbers.personal_asset_management_system.controller.asset_properties.financial_assets.bank_account;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.bank_account.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.bank_account.BankAccountService;
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
@RequestMapping("assets/bank-accounts")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @GetMapping("/{id}")
    public BankAccountResponse getBankAccount(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return bankAccountService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccountResponse createBankAccount(
            @Valid @ModelAttribute CreateBankAccountRequest request, @AuthenticationPrincipal User user) {
        return bankAccountService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public BankAccountResponse updateBankAccount(
            @PathVariable UUID id, @Valid @RequestBody UpdateBankAccountRequest request,
            @AuthenticationPrincipal User user) {
        return bankAccountService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<BankAccountSearchResponse> searchBankAccounts(
            @ModelAttribute BankAccountSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return bankAccountService.search(filters, user.getId(), pageable);
    }
}
