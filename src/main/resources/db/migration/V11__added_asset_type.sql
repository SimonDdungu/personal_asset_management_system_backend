ALTER TABLE assets
    ADD COLUMN asset_type UUID;

ALTER TABLE assets
    ADD CONSTRAINT fk_assets_asset_type
        FOREIGN KEY (asset_type)
            REFERENCES asset_types (id);