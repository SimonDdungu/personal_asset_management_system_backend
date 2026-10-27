package com.chainbers.personal_asset_management_system.controller.asset_properties.physical_assets.real_estate;

import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate.CreateRealEstateRequest;
import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate.RealEstateResponse;
import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate.RealEstateSearchFilter;
import com.chainbers.personal_asset_management_system.dto.asset_properties.physical_assets.real_estate.UpdateRealEstateRequest;
import com.chainbers.personal_asset_management_system.service.assets_properties.physical_assets.real_estate.RealEstateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("assets/realestate")
@RequiredArgsConstructor
public class RealEstateController {
    private final RealEstateService realEstateService;

    @GetMapping("/{id}")
    public RealEstateResponse getRealEstate(@PathVariable UUID id, @RequestParam UUID userId){
        return realEstateService.getById(id, userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RealEstateResponse createRealEstate(@Valid @RequestBody CreateRealEstateRequest request, @RequestParam UUID userId){
        return realEstateService.create(request, userId);
    }

    @PatchMapping("/{id}")
    public RealEstateResponse updateRealEstate(@PathVariable UUID id, @RequestBody UpdateRealEstateRequest request, @RequestParam UUID userId){
        return realEstateService.update(id, request, userId);
    }

    @GetMapping
    public Page<RealEstateResponse> searchRealEstate(RealEstateSearchFilter filters, @RequestParam UUID userId, Pageable pageable) {
        return realEstateService.search(filters, userId, pageable);
    }

}
