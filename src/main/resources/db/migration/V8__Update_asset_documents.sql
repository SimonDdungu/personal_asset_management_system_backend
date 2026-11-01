CREATE TABLE asset_documents_upload (
    id UUID PRIMARY KEY,
    asset_id UUID NOT NULL,
    document_type_id UUID NOT NULL,
    caption VARCHAR(255),
    uploaded_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_asset_documents_upload_asset FOREIGN KEY (asset_id) REFERENCES assets (id),

    CONSTRAINT fk_asset_documents_upload_type FOREIGN KEY (document_type_id) REFERENCES asset_document_types (id)
);

ALTER TABLE asset_documents DROP COLUMN asset_id, DROP COLUMN document_type_id;

ALTER TABLE asset_documents RENAME COLUMN document_url TO document_path;

ALTER TABLE asset_documents ADD COLUMN uploader_id UUID NOT NULL,
    ADD CONSTRAINT fk_asset_documents_upload FOREIGN KEY (uploader_id) REFERENCES asset_documents_upload (id);