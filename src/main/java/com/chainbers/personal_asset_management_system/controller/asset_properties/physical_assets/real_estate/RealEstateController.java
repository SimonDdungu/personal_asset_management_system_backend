package com.chainbers.personal_asset_management_system.controller.asset_properties.physical_assets.real_estate;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate.*;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.real_estate.RealEstateService;
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
@RequestMapping("assets/realestate")
@RequiredArgsConstructor
public class RealEstateController {
    private final RealEstateService realEstateService;

    @GetMapping("/{id}")
    public RealEstateResponse getRealEstate(@PathVariable UUID id, @AuthenticationPrincipal User user){
        return realEstateService.getById(id, user.getId());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RealEstateResponse createRealEstate(@Valid @ModelAttribute CreateRealEstateRequest request, @AuthenticationPrincipal User user){
        return realEstateService.create(request, user.getId());
    }

    @PatchMapping("/{id}")
    public RealEstateResponse updateRealEstate(@PathVariable UUID id, @RequestBody UpdateRealEstateRequest request, @AuthenticationPrincipal User user){
        return realEstateService.update(id, request, user.getId());
    }

    @GetMapping
    public Page<RealEstateSearchResponse> searchRealEstate(RealEstateSearchFilter filters, @AuthenticationPrincipal User user, Pageable pageable) {
        return realEstateService.search(filters, user.getId(), pageable);
    }

}
