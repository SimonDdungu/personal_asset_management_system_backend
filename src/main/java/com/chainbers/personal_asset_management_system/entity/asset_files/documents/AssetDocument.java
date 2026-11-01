package com.chainbers.personal_asset_management_system.entity.asset_files.documents;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JoinColumn(name = "uploader_id", nullable = false)
    private AssetDocumentUpload assetDocumentUpload;

    @Column(name = "document_path", nullable = false,  length = 2048)
    private String documentPath;

}
