package com.chainbers.personal_asset_management_system.service.asset_files.images;

import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.CreateAssetImageRequest;
import com.chainbers.personal_asset_management_system.entity.asset_files.AssetImage;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_files.AssetImageRepository;
import com.chainbers.personal_asset_management_system.repository.assets.AssetsRepository;
import com.chainbers.personal_asset_management_system.storage.SupabaseFileStorage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetImageService {
    private final AssetImageRepository assetImageRepository;
    private final AssetsRepository assetsRepository;
    private final SupabaseFileStorage fileStorage;

    @Transactional
    public List<AssetImageResponse> create(CreateAssetImageRequest request, UUID userId){
        Assets asset = assetsRepository.findByIdAndUserIdAndDeletedAtIsNull(request.assetId(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found"));

        List<MultipartFile> imageFiles = request.images();
        List<AssetImage> images = new ArrayList<>();

        if (request.primaryImageIndex() != null && request.primaryImageIndex() >= imageFiles.size()) {
            throw new IllegalArgumentException("Primary image is invalid");
        }

        for (int i = 0; i < imageFiles.size(); i++) {
            MultipartFile file = imageFiles.get(i);

            String imagePath = fileStorage.store("images", file);

            AssetImage image = new AssetImage();
            image.setAssets(asset);
            image.setImagePath(imagePath);
            image.setCaption(request.caption());
            image.setPrimary(request.primaryImageIndex() != null && request.primaryImageIndex() == i);

            images.add(image);
        }

        return assetImageRepository.saveAll(images)
                .stream()
                .map(image -> new AssetImageResponse(
                        image.getId(),
                        image.getAssets().getId(),
                        fileStorage.signedUrl(image.getImagePath()),
                        image.getCaption(),
                        image.isPrimary(),
                        image.getUploadedAt()
                )).toList();
    }
}
