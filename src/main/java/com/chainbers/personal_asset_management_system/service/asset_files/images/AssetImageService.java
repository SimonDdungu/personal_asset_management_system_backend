package com.chainbers.personal_asset_management_system.service.asset_files.images;

import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageUploadResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.CreateAssetImageRequest;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.UpdateAssetImageRequest;
import com.chainbers.personal_asset_management_system.entity.asset_files.images.AssetImage;
import com.chainbers.personal_asset_management_system.entity.asset_files.images.AssetImageUpload;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_files.images.AssetImageRepository;
import com.chainbers.personal_asset_management_system.repository.asset_files.images.AssetImageUploadRepository;
import com.chainbers.personal_asset_management_system.repository.assets.AssetsRepository;
import com.chainbers.personal_asset_management_system.storage.SupabaseFileStorage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetImageService {
    private final AssetImageRepository assetImageRepository;
    private final AssetImageUploadRepository assetImageUploadRepository;
    private final SupabaseFileStorage fileStorage;

    @Transactional
    public AssetImageUploadResponse create(CreateAssetImageRequest request,  Assets asset){
        if (request.images() == null || request.images().isEmpty()) {
            throw new IllegalArgumentException("No images available");
        }

        List<MultipartFile> imageFiles = request.images();

        if (request.primaryImageIndex() != null && request.primaryImageIndex() >= imageFiles.size()) {
            throw new IllegalArgumentException("Primary image is invalid");
        }

        AssetImageUpload upload = new AssetImageUpload();
        upload.setAssets(asset);
        upload.setCaption(request.caption());
        upload = assetImageUploadRepository.save(upload);


        List<AssetImage> images = new ArrayList<>();

        for (int i = 0; i < imageFiles.size(); i++) {
            MultipartFile file = imageFiles.get(i);

            String imagePath = fileStorage.store("images", file);

            AssetImage image = new AssetImage();
            image.setAssetImageUpload(upload);
            image.setImagePath(imagePath);
            image.setPrimary(request.primaryImageIndex() != null && request.primaryImageIndex() == i);
            image.setOriginalFileName(file.getOriginalFilename());
            images.add(image);
        }

        List<AssetImageResponse> imageResponse = assetImageRepository.saveAll(images)
                                                        .stream()
                                                        .map(image -> new AssetImageResponse(
                                                                image.getId(),
                                                                fileStorage.signedUrl(image.getImagePath()),
                                                                image.getOriginalFileName(),
                                                                image.isPrimary()
                                                        ))
                                                        .toList();

        return new AssetImageUploadResponse(
                upload.getId(),
                asset.getId(),
                upload.getCaption(),
                upload.getUploadedAt(),
                upload.getUpdatedAt(),
                imageResponse
        );
    }

    @Transactional
    public AssetImageUploadResponse update(UUID uploadId, UpdateAssetImageRequest request, UUID userId){
        AssetImageUpload upload = assetImageUploadRepository.findByIdAndAssetsUserIdAndAssetsDeletedAtIsNull(uploadId, userId)
                                                        .orElseThrow(() -> new EntityNotFoundException("Image upload not found"));

        List<AssetImage> images = assetImageRepository.findAllByAssetImageUploadIdAndAssetImageUploadAssetsUserIdAndAssetImageUploadAssetsDeletedAtIsNull(uploadId, userId);

        if (images.isEmpty()) {
            throw new EntityNotFoundException("Image upload not found");
        }

        if (request.primaryImageIndex() != null && request.primaryImageIndex() >= images.size()) {
            throw new IllegalArgumentException("Primary image is invalid");
        }

        upload.setCaption(request.caption());

        if (request.primaryImageIndex() != null) {
            for (int i = 0; i < images.size(); i++) {
                images.get(i).setPrimary(i == request.primaryImageIndex());
            }
        }

        upload.setUpdatedAt(Instant.now());

        assetImageUploadRepository.save(upload);

        return new AssetImageUploadResponse(
                upload.getId(),
                upload.getAssets().getId(),
                upload.getCaption(),
                upload.getUploadedAt(),
                upload.getUpdatedAt(),
                images.stream()
                        .map(image -> new AssetImageResponse(
                                image.getId(),
                                fileStorage.signedUrl(image.getImagePath()),
                                image.getOriginalFileName(),
                                image.isPrimary()
                        ))
                        .toList()
        );

    }

    public List<AssetImageUploadResponse> getImagesByAssetId(UUID assetId) {
        List<AssetImageUpload> uploads = assetImageUploadRepository.findAllByAssetsId(assetId);

        return uploads.stream()
                .map(upload -> {
                    List<AssetImageResponse> images = assetImageRepository.findAllByAssetImageUploadId(upload.getId())
                                                            .stream()
                                                            .map(image -> new AssetImageResponse(
                                                                    image.getId(),
                                                                    fileStorage.signedUrl(image.getImagePath()),
                                                                    image.getOriginalFileName(),
                                                                    image.isPrimary()
                                                            ))
                                                            .toList();

                    return new AssetImageUploadResponse(
                            upload.getId(),
                            assetId,
                            upload.getCaption(),
                            upload.getUploadedAt(),
                            upload.getUpdatedAt(),
                            images
                    );
                })
                .toList();
    }


    @Transactional
    public void deleteImage(UUID uploadId, UUID imageId, UUID userId) {
        AssetImageUpload upload = assetImageUploadRepository
                .findByIdAndAssetsUserIdAndAssetsDeletedAtIsNull(uploadId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Image upload not found"));

        AssetImage image = assetImageRepository.findByIdAndAssetImageUploadId(imageId, upload.getId())
                                            .orElseThrow(() -> new EntityNotFoundException("Image not found"));

        // Delete the stored file
        fileStorage.delete(image.getImagePath());

        assetImageRepository.delete(image);

        // Remove the upload if no images remain
        if (assetImageRepository.findAllByAssetImageUploadId(upload.getId()).isEmpty()) {
            assetImageUploadRepository.delete(upload);
        }
    }

    @Transactional
    public void deleteUpload(UUID uploadId, UUID userId) {
        AssetImageUpload upload = assetImageUploadRepository.findByIdAndAssetsUserIdAndAssetsDeletedAtIsNull(uploadId, userId)
                                                .orElseThrow(() -> new EntityNotFoundException("Image upload not found"));

        List<AssetImage> images = assetImageRepository.findAllByAssetImageUploadId(upload.getId());

        // Delete the stored files
        for (AssetImage image : images) {
            fileStorage.delete(image.getImagePath());
        }

        // Delete database records
        assetImageRepository.deleteAll(images);

        assetImageUploadRepository.delete(upload);
    }


    public AssetImageResponse getPrimaryImageByAssetId(UUID assetId) {
        return assetImageRepository.findFirstByAssetImageUploadAssetsIdAndIsPrimaryTrue(assetId)
                .map(image -> new AssetImageResponse(
                        image.getId(),
                        fileStorage.signedUrl(image.getImagePath()),
                        image.getOriginalFileName(),
                        image.isPrimary()
                ))
                .orElse(null);
    }
}
