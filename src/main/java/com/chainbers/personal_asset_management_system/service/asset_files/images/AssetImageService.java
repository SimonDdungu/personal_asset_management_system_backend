package com.chainbers.personal_asset_management_system.service.asset_files.images;

import com.chainbers.personal_asset_management_system.dto.asset_files.images.AssetImageResponse;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.CreateAssetImageRequest;
import com.chainbers.personal_asset_management_system.dto.asset_files.images.UpdateAssetImageRequest;
import com.chainbers.personal_asset_management_system.entity.asset_files.AssetImage;
import com.chainbers.personal_asset_management_system.entity.asset_files.AssetImageUpload;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_files.AssetImageRepository;
import com.chainbers.personal_asset_management_system.repository.asset_files.AssetImageUploadRepository;
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
    private final AssetImageUploadRepository assetImageUploadRepository;
    private final AssetsRepository assetsRepository;
    private final SupabaseFileStorage fileStorage;

    @Transactional
    public List<AssetImageResponse> create(CreateAssetImageRequest request,  Assets asset){
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

            images.add(image);
        }

        return assetImageRepository.saveAll(images)
                .stream()
                .map(image -> new AssetImageResponse(
                        image.getId(),
                        image.getAssetImageUpload().getAssets().getId(),
                        fileStorage.signedUrl(image.getImagePath()),
                        image.getAssetImageUpload().getCaption(),
                        image.isPrimary(),
                        image.getAssetImageUpload().getUploadedAt()
                )).toList();
    }

    @Transactional
    public List<AssetImageResponse> update(UUID uploadId, UpdateAssetImageRequest request, UUID userId){
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

        assetImageUploadRepository.save(upload);
        assetImageRepository.saveAll(images);

        return images.stream()
                .map(image -> new AssetImageResponse(
                        image.getId(),
                        upload.getAssets().getId(),
                        fileStorage.signedUrl(image.getImagePath()),
                        upload.getCaption(),
                        image.isPrimary(),
                        upload.getUploadedAt()
                ))
                .toList();

    }

    public List<AssetImageResponse> getImagesByAssetId(UUID assetId) {
        List<AssetImageUpload> uploads = assetImageUploadRepository.findAllByAssetsId(assetId);

        return uploads.stream()
                .flatMap(upload -> assetImageRepository
                        .findAllByAssetImageUploadId(upload.getId())
                        .stream()
                        .map(image -> new AssetImageResponse(
                                image.getId(),
                                assetId,
                                fileStorage.signedUrl(image.getImagePath()),
                                upload.getCaption(),
                                image.isPrimary(),
                                upload.getUploadedAt()
                        )))
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
}
