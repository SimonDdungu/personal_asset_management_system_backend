ALTER TABLE assets ADD COLUMN name varchar(255) NOT NULL;
ALTER TABLE assets ADD COLUMN acquisition_id uuid NOT NULL;

ALTER TABLE assets ADD CONSTRAINT fk_assets_acquisition FOREIGN KEY (acquisition_id) REFERENCES acquisition (id);