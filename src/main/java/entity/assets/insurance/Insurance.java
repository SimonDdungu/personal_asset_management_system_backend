package entity.assets.insurance;

import entity.assets.Assets;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "insurance")
@Getter
@Setter
@NoArgsConstructor
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Assets assets;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "coverage_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal coverageAmount;

    @Column(name = "coverage_amount_updated_at", nullable = false)
    private Instant coverageAmountUpdatedAt;

    @Column(name = "premium_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal premiumAmount;

    @Column(name = "premium_frequency", nullable = false)
    private String premiumFrequency;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate(){
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected  void onUpdate(){
        updatedAt = Instant.now();
    }

}
