package com.chainbers.personal_asset_management_system.specifications.assets;

import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class AssetsSpecification {

    public static Specification<Assets> nameContains(String name) {
        return (root, query, cb) -> (name == null || name.isBlank())
                ? null
                : cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%");
    }

    public static Specification<Assets> hasCategory(UUID categoryId){
        return (root, query, cb) -> (categoryId == null)
                ?  null
                : cb.equal(root.get("assetCategory").get("id"), categoryId);
    }

    public static Specification<Assets> hasAcquisition(UUID acquisitionId){
        return (root, query, cb) -> (acquisitionId == null)
                ? null
                : cb.equal(root.get("acquisition").get("id"), acquisitionId);
    }

    public static Specification<Assets> belongsTo(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Assets> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("deletedAt"));
    }

    public static Specification<Assets> isDeleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("deletedAt"));
    }

}
