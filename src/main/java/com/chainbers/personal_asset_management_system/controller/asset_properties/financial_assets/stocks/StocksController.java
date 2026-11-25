package com.chainbers.personal_asset_management_system.controller.asset_properties.financial_assets.stocks;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.stocks.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.stocks.StocksService;
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
@RequestMapping("assets/stocks")
@RequiredArgsConstructor
public class StocksController {
    private final StocksService stocksService;

    @GetMapping("/{id}")
    public StocksResponse getStocks(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return stocksService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public StocksResponse createStocks(@Valid @ModelAttribute CreateStocksRequest request, @AuthenticationPrincipal User user) {
        return stocksService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public StocksResponse updateStocks(
            @PathVariable UUID id, @Valid @RequestBody UpdateStocksRequest request, @AuthenticationPrincipal User user) {
        return stocksService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<StocksSearchResponse> searchStocks(
            @ModelAttribute StocksSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return stocksService.search(filters, user.getId(), pageable);
    }
}
