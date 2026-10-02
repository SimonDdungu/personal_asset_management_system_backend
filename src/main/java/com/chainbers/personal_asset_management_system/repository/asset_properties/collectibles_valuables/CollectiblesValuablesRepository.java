package com.chainbers.personal_asset_management_system.repository.asset_properties.collectibles_valuables;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CollectiblesValuables extends JpaRepository<CollectiblesValuables, UUID> {
    List<CollectiblesValuables>
}
