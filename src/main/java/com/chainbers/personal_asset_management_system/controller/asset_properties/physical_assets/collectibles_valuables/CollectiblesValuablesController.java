package com.chainbers.personal_asset_management_system.controller.asset_properties.physical_assets.collectibles_valuables;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.collectibles_valuables.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.collectibles_valuables.CollectiblesValuablesService;
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
@RequestMapping("assets/collectibles-valuables")
@RequiredArgsConstructor
public class CollectiblesValuablesController {
    private final CollectiblesValuablesService collectiblesValuablesService;

    @GetMapping("/{id}")
    public CollectiblesValuablesResponse getCollectible(
            @PathVariable UUID id, @AuthenticationPrincipal User user) {
        return collectiblesValuablesService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CollectiblesValuablesResponse createCollectible(
            @Valid @ModelAttribute CreateCollectiblesValuablesRequest request, @AuthenticationPrincipal User user) {
        return collectiblesValuablesService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public CollectiblesValuablesResponse updateCollectible(
            @PathVariable UUID id, @Valid @RequestBody UpdateCollectiblesValuablesRequest request,
            @AuthenticationPrincipal User user) {
        return collectiblesValuablesService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<CollectiblesValuablesSearchResponse> searchCollectibles(
            @ModelAttribute CollectiblesValuablesSearchFilter filters, @AuthenticationPrincipal User user,
            Pageable pageable) {
        return collectiblesValuablesService.search(filters, user.getId(), pageable);
    }
}
