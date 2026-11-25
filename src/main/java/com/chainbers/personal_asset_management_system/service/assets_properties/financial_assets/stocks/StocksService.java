package com.chainbers.personal_asset_management_system.service.assets_properties.financial_assets.stocks;

import com.chainbers.personal_asset_management_system.dto.asset_properties.financial_assets.stocks.*;
import com.chainbers.personal_asset_management_system.entity.asset_properties.financial_assets.stocks.Stocks;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import com.chainbers.personal_asset_management_system.repository.asset_properties.financial_assets.stocks.StocksRepository;
import com.chainbers.personal_asset_management_system.service.asset_files.documents.AssetDocumentService;
import com.chainbers.personal_asset_management_system.service.assets.AssetsService;
import com.chainbers.personal_asset_management_system.specifications.asset_properties.financial_assets.stocks.StocksSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StocksService {
    private final StocksRepository stocksRepository;
    private final AssetsService assetsService;
    private final AssetDocumentService assetDocumentService;

    private Stocks findStocks(UUID id, UUID userId) {
        return stocksRepository.findByIdAndAssetsUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Stocks asset not found: " + id));
    }

    @Transactional(readOnly = true)
    public StocksResponse getById(UUID id, UUID userId) {
        return toResponse(findStocks(id, userId));
    }

    @Transactional
    public StocksResponse create(CreateStocksRequest request, UUID userId) {
        Assets asset = assetsService.createEntity(request.asset(), userId);
        Stocks stocks = new Stocks();
        stocks.setAssets(asset);
        stocks.setTickerSymbol(request.tickerSymbol());
        stocks.setNumberShares(request.numberShares());
        stocks.setBrokerage(request.brokerage());
        Stocks saved = stocksRepository.save(stocks);
        if (request.documents() != null && !request.documents().documents().isEmpty()) {
            assetDocumentService.create(request.documents(), asset);
        }
        return toResponse(saved);
    }

    @Transactional
    public StocksResponse update(UUID id, UpdateStocksRequest request, UUID userId) {
        Stocks stocks = findStocks(id, userId);
        if (request.tickerSymbol() != null && !request.tickerSymbol().isBlank()) {
            stocks.setTickerSymbol(request.tickerSymbol());
        }
        if (request.numberShares() != null && request.numberShares() >= 0) {
            stocks.setNumberShares(request.numberShares());
        }
        if (request.brokerage() != null && !request.brokerage().isBlank()) {
            stocks.setBrokerage(request.brokerage());
        }
        return toResponse(stocks);
    }

    @Transactional(readOnly = true)
    public Page<StocksSearchResponse> search(StocksSearchFilter filters, UUID userId, Pageable pageable) {
        Specification<Stocks> specification = Specification.allOf(
                StocksSpecification.belongsTo(userId),
                StocksSpecification.nameContainsAsset(filters.assetName()),
                StocksSpecification.nameContainsTickerSymbol(filters.tickerSymbol()),
                StocksSpecification.numberSharesFrom(filters.minNumberShares()),
                StocksSpecification.numberSharesTo(filters.maxNumberShares()),
                StocksSpecification.nameContainsBrokerage(filters.brokerage()),
                StocksSpecification.currencyIs(filters.currency()),
                StocksSpecification.currentValueFrom(filters.minValue()),
                StocksSpecification.currentValueTo(filters.maxValue()),
                StocksSpecification.isNotDeleted()
        );
        return stocksRepository.findAll(specification, pageable).map(this::toSearchResponse);
    }

    private StocksResponse toResponse(Stocks stocks) {
        Assets asset = stocks.getAssets();
        return new StocksResponse(
                stocks.getId(), asset.getId(), asset.getName(), asset.getAcquisition().getName(),
                asset.getAssetType().getName(), asset.getAssetCategory().getName(),
                stocks.getTickerSymbol(), stocks.getNumberShares(), stocks.getBrokerage(),
                asset.getCurrency(), asset.getCurrentValue(), asset.getCurrentValueUpdatedAt(),
                assetDocumentService.getDocumentByAssetId(asset.getId()),
                stocks.getCreatedAt(), stocks.getUpdatedAt()
        );
    }

    private StocksSearchResponse toSearchResponse(Stocks stocks) {
        Assets asset = stocks.getAssets();
        return new StocksSearchResponse(
                stocks.getId(), asset.getId(), asset.getName(), stocks.getTickerSymbol(),
                stocks.getNumberShares(), stocks.getBrokerage(), asset.getCurrency(), asset.getCurrentValue()
        );
    }
}
