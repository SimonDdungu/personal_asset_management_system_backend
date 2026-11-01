-- Remove current value and current value updated at from all asset properties and store them in one asset table

-- 1. current_value and current_value_upated_at and currency to assets
ALTER TABLE assets
    ADD COLUMN current_value NUMERIC(19, 2) NOT NULL DEFAULT 0.00,
    ADD COLUMN current_value_updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN currency VARCHAR(3);

-- 2. Remove current value fields from individual asset tables
ALTER TABLE collectibles_valuables DROP COLUMN current_value, DROP COLUMN current_value_updated_at, DROP COLUMN currency;

ALTER TABLE electronics DROP COLUMN current_value, DROP COLUMN current_value_updated_at, DROP COLUMN currency;

ALTER TABLE furniture DROP COLUMN current_value, DROP COLUMN current_value_updated_at, DROP COLUMN currency;

ALTER TABLE land DROP COLUMN current_value, DROP COLUMN current_value_updated_at, DROP COLUMN currency;

ALTER TABLE machinery DROP COLUMN current_value, DROP COLUMN current_value_updated_at, DROP COLUMN currency;

ALTER TABLE miscellaneous DROP COLUMN current_value, DROP COLUMN current_value_updated_at, DROP COLUMN currency;

ALTER TABLE real_estate DROP COLUMN current_value, DROP COLUMN current_value_updated_at, DROP COLUMN currency;

ALTER TABLE vehicles DROP COLUMN current_value, DROP COLUMN current_value_updated_at, DROP COLUMN currency;

ALTER TABLE investments DROP COLUMN current_value, DROP COLUMN current_value_updated_at, DROP COLUMN currency;

ALTER TABLE physical_cash DROP COLUMN current_value, DROP COLUMN current_value_updated_at, DROP COLUMN currency;

ALTER TABLE retirement_funds DROP COLUMN current_value, DROP COLUMN current_value_updated_at, DROP COLUMN currency;

-- 3. Remove balance fields from bank_accounts
ALTER TABLE bank_accounts DROP COLUMN current_balance, DROP COLUMN current_balance_updated_at, DROP COLUMN currency;

-- 4. Remove coverage fields from insurance
ALTER TABLE insurance DROP COLUMN coverage_amount, DROP COLUMN coverage_amount_updated_at, DROP COLUMN currency;

-- 5. Create the separate liability table
CREATE TABLE liability (
   id UUID PRIMARY KEY,
   asset_id UUID NOT NULL,
   creditor VARCHAR(255) NOT NULL,
   due_date DATE NOT NULL,
   description VARCHAR(255),
   created_at TIMESTAMP WITH TIME ZONE NOT NULL,
   updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

   CONSTRAINT fk_liability_assets FOREIGN KEY (asset_id) REFERENCES assets (id)
);

-- 6. Remove outstanding balance from loans
ALTER TABLE loans DROP COLUMN outstanding_balance, DROP COLUMN currency;

-- 7. Remove balance fields from savings
ALTER TABLE savings DROP COLUMN current_balance, DROP COLUMN current_balance_updated_at, DROP COLUMN currency;

ALTER TABLE asset_images RENAME COLUMN image_url TO image_path;
