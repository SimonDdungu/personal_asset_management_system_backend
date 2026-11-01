-- Create asset_images_upload table
CREATE TABLE asset_images_upload (
     id UUID PRIMARY KEY,
     asset_id UUID NOT NULL,
     caption VARCHAR(255),
     uploaded_at TIMESTAMP WITH TIME ZONE NOT NULL,
     updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

     CONSTRAINT fk_asset_images_upload_assets FOREIGN KEY (asset_id) REFERENCES assets (id)
);

-- Update asset_images
ALTER TABLE asset_images ADD COLUMN uploader_id UUID NOT NULL;

ALTER TABLE asset_images ADD CONSTRAINT fk_asset_images_upload FOREIGN KEY (uploader_id) REFERENCES asset_images_upload (id);

ALTER TABLE asset_images DROP COLUMN caption, DROP COLUMN uploaded_at, DROP COLUMN asset_id;