package com.chainbers.personal_asset_management_system.specifications.assets;

import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class AssetSpecification {

    public static Specification<Assets> nameContains(String name) {
        return (root, query, criteriaBuilder) -> (name == null || name.isBlank())
                ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%");
    }

    public static Specification<Assets> hasCategory(UUID categoryId){
        return (root, query, criteriaBuilder) -> (categoryId == null)
                ?  null
                : criteriaBuilder.equal(root.get("assetCategory").get("id"), categoryId);
    }

    public static Specification<Assets> hasAcquisition(UUID acquisitionId){
        return (root, query, criteriaBuilder) -> (acquisitionId == null)
                ? null
                : criteriaBuilder.equal(root.get("acquisition").get("id"), acquisitionId);
    }

    public static Specification<Assets> belongsTo(UUID userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Assets> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<Assets> isDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("deletedAt"));
    }

}
