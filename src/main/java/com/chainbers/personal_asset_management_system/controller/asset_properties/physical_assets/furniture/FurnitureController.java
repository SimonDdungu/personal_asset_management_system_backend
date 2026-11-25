package com.chainbers.personal_asset_management_system.controller.asset_properties.physical_assets.furniture;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.furniture.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.furniture.FurnitureService;
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
@RequestMapping("assets/furniture")
@RequiredArgsConstructor
public class FurnitureController {
    private final FurnitureService furnitureService;

    @GetMapping("/{id}")
    public FurnitureResponse getFurniture(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return furnitureService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public FurnitureResponse createFurniture(
            @Valid @ModelAttribute CreateFurnitureRequest request, @AuthenticationPrincipal User user) {
        return furnitureService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public FurnitureResponse updateFurniture(
            @PathVariable UUID id, @Valid @RequestBody UpdateFurnitureRequest request, @AuthenticationPrincipal User user) {
        return furnitureService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<FurnitureSearchResponse> searchFurniture(
            @ModelAttribute FurnitureSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return furnitureService.search(filters, user.getId(), pageable);
    }
}
