package entity.assets.stock_asset;

import entity.assets.Assets;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "stock_assets")
@Getter
@Setter
@NoArgsConstructor
public class StockAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Assets assets;

    @Column(name = "ticker_symbol", nullable = false)
    private String tickerSymbol;

    @Column(name = "number_of_shares", nullable = false)
    private int numberOfShares;

    @Column(nullable = false)
    private String brokerage;

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
    protected void onUpdate(){
        updatedAt = Instant.now();
    }
}
