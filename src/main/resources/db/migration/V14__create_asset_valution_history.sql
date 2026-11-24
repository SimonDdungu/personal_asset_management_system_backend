CREATE TABLE assets_valution_history (
         id UUID PRIMARY KEY,
         asset_id UUID NOT NULL,
         value NUMERIC(19, 4) NOT NULL,
         recorded_at TIMESTAMP WITH TIME ZONE NOT NULL,

         CONSTRAINT fk_assets_valution_history_asset FOREIGN KEY (asset_id) REFERENCES assets (id)
);

CREATE INDEX idx_asset_valuation_history_asset_date ON assets_valution_history (asset_id, recorded_at);