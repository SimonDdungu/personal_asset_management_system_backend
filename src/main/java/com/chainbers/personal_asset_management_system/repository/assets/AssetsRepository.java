package com.chainbers.personal_asset_management_system.repository.assets;

import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AssetsRepository extends JpaRepository<Assets, UUID> {
    List<Assets> findByNameContainingIgnoreCase(String name);
    List<Assets> findByUserId(UUID userId);
    @Query("""
    SELECT a
    FROM Assets a
    WHERE LOWER(CONCAT(a.user.firstName, ' ', a.user.lastName))
          LIKE LOWER(CONCAT('%', :fullName, '%'))
    """)
    List<Assets> findByUserFullName(@Param("fullName") String fullName);
    List<Assets> findByUserFirstNameContainingIgnoreCase(String firstName);
    List<Assets> findByUserLastNameContainingIgnoreCase(String lastName);
    List<Assets> findByUserEmailContainingIgnoreCase(String email);
    List<Assets> findByUserPhoneNumberContainingIgnoreCase(String phoneNumber);
    List<Assets> findByAssetCategoryNameContainingIgnoreCase(String categoryName);
    List<Assets> findByAcquisitionNameIgnoreCase(String acquisitionName);
}
