package entity.assets.liability;

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
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
public class Liability {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Assets assets;

    @Column(nullable = false)
    private String creditor;

    @Column(name = "amount_owed", nullable = false, precision = 19, scale = 2)
    private BigDecimal amountOwed;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column
    private String description;

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
