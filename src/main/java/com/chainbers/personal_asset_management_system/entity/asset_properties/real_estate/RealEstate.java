package com.chainbers.personal_asset_management_system.entity.asset_properties.real_estate;

import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "real_estate")
@Getter
@Setter
@NoArgsConstructor
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Assets assets;

    @Column(nullable = false)
    private String address;

    @Column
    private String description;

    @Column(name = "rental_income", precision = 16, scale = 2)
    private BigDecimal rentalIncome;

    @Column(name = "rental_income_currency", length = 3)
    private String rentalIncomeCurrency;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "current_value", nullable = false, precision = 19, scale = 2)
    private BigDecimal currentValue;

    @Column(name = "current_value_updated_at", nullable = false)
    private Instant currentValueUpdatedAt;

    @Column(name = "number_bedrooms")
    private Integer numberBedrooms;

    @Column(name = "number_bathrooms")
    private Integer numberBathrooms;

    @Column(name = "number_units")
    private Integer numberUnits;

    @Column(name = "property_size")
    private Integer propertySize;

    @Column(name = "property_size_unit", length = 3)
    private String propertySizeUnit;

    @Column(name = "construction_cost", precision = 19, scale = 2)
    private BigDecimal constructionCost;

    @Column(name = "construction_completion_date")
    private LocalDate constructionCompletionDate;

    @Column(name = "construction_currency", length = 3)
    private String constructionCurrency;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}
