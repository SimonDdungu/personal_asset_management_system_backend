package com.chainbers.personal_asset_management_system.entity.assets;

import com.chainbers.personal_asset_management_system.entity.asset_files.AssetImageUpload;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.bank_account.BankAccount;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.insurance.Insurance;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.investment.Investment;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.liability.Liability;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.loan.Loans;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.physical_cash.PhysicalCash;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.retirement_fund.RetirementFund;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.savings.Savings;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.stocks.Stocks;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.collectibles_valuables.CollectiblesValuables;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.electronics.Electronics;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.furniture.Furniture;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.land.Land;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.machinery.Machinery;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.real_estate.RealEstate;
import com.chainbers.personal_asset_management_system.entity.asset_properties.physical_assets.vehicle.Vehicle;
import com.chainbers.personal_asset_management_system.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "assets")
@Getter
@Setter
@NoArgsConstructor
public class Assets {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_category_id", nullable = false)
    private AssetCategory assetCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acquisition_id", nullable = false)
    private Acquisition acquisition;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false, length = 3)
    private Currency currency;

    @Column(name = "current_value", nullable = false, precision = 19, scale = 2)
    private BigDecimal currentValue;

    @Column(name = "current_value_updated_at", nullable = false)
    private Instant currentValueUpdatedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<AssetImageUpload> imageUploads = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<Electronics> electronics = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<RealEstate> realEstate = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<Furniture> furniture = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<Machinery> machinery = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<CollectiblesValuables> collectiblesValuables = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<Land> land = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<Insurance> insurance = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<Investment> investments = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<Liability> liabilities = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<Loans> loans = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<PhysicalCash> physicalCash = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<RetirementFund> retirementFunds = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<Savings> savings = new ArrayList<>();

    @OneToMany(mappedBy = "assets", cascade = CascadeType.REMOVE)
    private List<Stocks> stocks = new ArrayList<>();

}
