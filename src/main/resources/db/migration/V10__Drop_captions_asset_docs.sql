ALTER TABLE asset_documents DROP COLUMN caption;
ALTER TABLE asset_documents ADD COLUMN original_filename VARCHAR(255);

ALTER TABLE asset_images ADD COLUMN original_filename VARCHAR(255);