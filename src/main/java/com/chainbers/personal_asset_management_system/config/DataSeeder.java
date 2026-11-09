package com.chainbers.personal_asset_management_system.config;

import com.chainbers.personal_asset_management_system.entity.asset_files.documents.AssetDocumentType;
import com.chainbers.personal_asset_management_system.entity.assets.Acquisition;
import com.chainbers.personal_asset_management_system.entity.assets.AssetCategory;
import com.chainbers.personal_asset_management_system.entity.assets.AssetType;
import com.chainbers.personal_asset_management_system.repository.asset_files.documents.AssetDocumentTypeRepository;
import com.chainbers.personal_asset_management_system.repository.assets.AcquisitionRepository;
import com.chainbers.personal_asset_management_system.repository.assets.AssetCategoryRepository;
import com.chainbers.personal_asset_management_system.repository.assets.AssetTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AcquisitionRepository acquisitionRepository;
    private final AssetTypeRepository assetTypeRepository;
    private final AssetCategoryRepository assetCategoryRepository;
    private final AssetDocumentTypeRepository assetDocumentTypeRepository;

    @Override
    @Transactional
    public void run(String... args) {
        seedAcquisitions();
        seedAssetTypes();
        seedAssetCategories();
        seedDocumentTypes();
    }

    private void seedAcquisitions() {
        String[] acquisitions = {
                "Purchased",
                "Built",
                "Created",
                "Inherited",
                "Gifted",
                "Other"
        };

        for (String name : acquisitions) {
            if (!acquisitionRepository.existsByName(name)) {
                Acquisition acquisition = new Acquisition();
                acquisition.setName(name);
                acquisitionRepository.save(acquisition);
            }
        }
    }

    private void seedDocumentTypes() {
        String[] documentTypes = {
                "Receipt",
                "Proof of Ownership",
                "Purchase Document",
                "Transaction Document",
                "User Manual",
                "Contract",
                "Report",
                "Invoice",
                "Record",
                "Financial Statement",
                "Ledger",
                "Journal",
                "Appraisal",
                "Other"
        };

        for (String documentType : documentTypes) {
            if (!assetDocumentTypeRepository.existsByName(documentType)) {
                AssetDocumentType assetDocumentType = new AssetDocumentType();
                assetDocumentType.setName(documentType);
                assetDocumentTypeRepository.save(assetDocumentType);
            }
        }
    }


    private void seedAssetTypes() {
        String[] assetTypes = {"Physical Assets", "Financial Assets"};

        for (String name : assetTypes) {
            if (assetTypeRepository.findByName(name).isEmpty()) {
                AssetType assetType = new AssetType();
                assetType.setName(name);
                assetTypeRepository.save(assetType);
            }
        }
    }

    private void seedCategories(String assetTypeName, String[] categories) {
        AssetType assetType = assetTypeRepository.findByName(assetTypeName)
                .orElseThrow(() -> new EntityNotFoundException("Asset type not found: " + assetTypeName));

        for (String name : categories) {
            if (assetCategoryRepository.findByNameAndAssetType(name, assetType).isEmpty()) {

                AssetCategory category = new AssetCategory();
                category.setName(name);
                category.setAssetType(assetType);

                assetCategoryRepository.save(category);
            }
        }
    }

    private void seedAssetCategories(){
            String[] physicalCategories = {
                    "Land",
                    "Houses",
                    "Vehicles",
                    "Electronics",
                    "Furniture",
                    "Real estate",
                    "Machinery & Equipment",
                    "Other Physical Assets"
            };

            String[] financialCategories = {
                    "Bank Accounts",
                    "Cash",
                    "Savings",
                    "Investments",
                    "Stocks",
                    "Retirement Funds",
                    "Loans",
                    "Liabilities",
                    "Insurance Policies",
                    "Other Financial Assets"
            };

            seedCategories("Physical Assets", physicalCategories);
            seedCategories("Financial Assets", financialCategories);
    }

}
