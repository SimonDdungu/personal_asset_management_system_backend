package com.chainbers.personal_asset_management_system.entity.asset_files;

import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "asset_documents")
@Getter
@Setter
@NoArgsConstructor
public class AssetDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Assets assets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", nullable = false)
    private AssetDocumentType assetDocumentType;

    @Column(name = "document_url", nullable = false)
    private String documentUrl;

    @Column
    private String caption;

    @CreationTimestamp
    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

}
