ALTER TABLE real_estate ALTER COLUMN propertySize TYPE numeric(12, 2);
ALTER TABLE real_estate ALTER COLUMN propertySizeUnit TYPE varchar(25);
ALTER TABLE real_estate ALTER COLUMN rentalIncome TYPE numeric(19, 2);
ALTER TABLE bank_accounts ALTER COLUMN bankAccountNumber TYPE varchar(40);
ALTER TABLE asset_images ALTER COLUMN image_url TYPE varchar(2048);
ALTER TABLE asset_documents ALTER COLUMN document_url TYPE varchar(2048);