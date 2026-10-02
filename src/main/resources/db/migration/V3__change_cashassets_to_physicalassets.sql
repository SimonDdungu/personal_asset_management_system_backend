ALTER TABLE cash_assets RENAME TO physical_cash;
ALTER TABLE physical_cash DROP COLUMN bank_account_number;