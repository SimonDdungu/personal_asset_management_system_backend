package com.chainbers.personal_asset_management_system.repository.assets;

import com.chainbers.personal_asset_management_system.dto.metrics.CategoryCount;
import com.chainbers.personal_asset_management_system.dto.metrics.CategoryTotal;
import com.chainbers.personal_asset_management_system.dto.metrics.CurrencyAverage;
import com.chainbers.personal_asset_management_system.dto.metrics.CurrencyTotal;
import com.chainbers.personal_asset_management_system.entity.assets.Assets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetsRepository extends JpaRepository<Assets, UUID>, JpaSpecificationExecutor<Assets> {
    List<Assets> findByUserId(UUID userId);
    Optional<Assets> findByIdAndUserId(UUID id, UUID userId);
    Optional<Assets> findByIdAndUserIdAndDeletedAtIsNull(UUID id, UUID userId);
    Optional<Assets> findByIdAndUserIdAndDeletedAtIsNotNull(UUID id, UUID userId);

    @Query("""
    select count(a)
    from Assets a
    where a.user.id = :userId
      and a.deletedAt is null
    """)
    long totalAssetsCount(@Param("userId") UUID userId);

    @Query("""
    select count(a)
    from Assets a
    where a.user.id = :userId
      and a.assetType.name = 'Physical Assets'
      and a.deletedAt is null
    """)
    long totalPhysicalAssetsCount(@Param("userId") UUID userId);

    @Query("""
    select count(a)
    from Assets a
    where a.user.id = :userId
      and a.assetType.name = 'Financial Assets'
      and a.deletedAt is null
    """)
    long totalFinancialAssetsCount(@Param("userId") UUID userId);

    @Query("""
    select a.currency as currency, sum(a.currentValue) as total
    from Assets a
    where a.user.id = :userId and a.deletedAt is null
    group by a.currency
    """)
    List<CurrencyTotal> sumByCurrency(@Param("userId") UUID userId);

    @Query("""
    select a.currency as currency, sum(a.currentValue) as total
    from Assets a
    where a.user.id = :userId
      and a.deletedAt is null
      and a.assetType.name = 'Physical Assets'
    group by a.currency
    """)
    List<CurrencyTotal> sumPhysicalAssetsByCurrency(@Param("userId") UUID userId);

    @Query("""
    select a.currency as currency, sum(a.currentValue) as total
    from Assets a
    where a.user.id = :userId
      and a.deletedAt is null
      and a.assetType.name = 'Financial Assets'
    group by a.currency
    """)
    List<CurrencyTotal> sumFinancialAssetsByCurrency(@Param("userId") UUID userId);

    @Query("""
    select c.name as category, sum(a.currentValue) as total
    from Assets a join a.assetCategory c
    where a.user.id = :userId and a.currency = :currency and a.deletedAt is null
    group by c.id, c.name
    order by sum(a.currentValue) desc
    limit 5
    """)
    List<CategoryTotal> topCategories(@Param("userId") UUID userId, @Param("currency") Currency currency);

    @Query("""
    select c.name as category, sum(a.currentValue) as total
    from Assets a join a.assetCategory c
    where a.user.id = :userId
      and a.assetType.name = 'Physical Assets'
      and a.currency = :currency
      and a.deletedAt is null
    group by c.id, c.name
    order by sum(a.currentValue) desc
    limit 5
    """)
    List<CategoryTotal> topPhysicalCategories(@Param("userId") UUID userId, @Param("currency") Currency currency);

    @Query("""
    select c.name as category, sum(a.currentValue) as total
    from Assets a join a.assetCategory c
    where a.user.id = :userId
      and a.assetType.name = 'Financial Assets'
      and a.currency = :currency
      and a.deletedAt is null
    group by c.id, c.name
    order by sum(a.currentValue) desc
    limit 5
    """)
    List<CategoryTotal> topFinancialCategories(@Param("userId") UUID userId,  @Param("currency") Currency currency);

    @Query("""
    select c.name as category, a.currency as currency, count(a) as count
    from Assets a join a.assetCategory c
    where a.user.id = :userId
      and a.currency = :currency
      and a.deletedAt is null
    group by c.id, c.name, a.currency
    order by count(a) desc
    limit 5
    """)
    List<CategoryCount> topCategoriesByCount(@Param("userId") UUID userId, @Param("currency") Currency currency);

    @Query("""
    select c.name as category, a.currency as currency, count(a) as count
    from Assets a join a.assetCategory c
    where a.user.id = :userId
      and a.assetType.name = 'Physical Assets'
      and a.currency = :currency
      and a.deletedAt is null
    group by c.id, c.name, a.currency
    order by count(a) desc
    limit 5
    """)
    List<CategoryCount> topPhysicalCategoriesByCount(@Param("userId") UUID userId, @Param("currency") Currency currency);

    @Query("""
    select c.name as category, a.currency as currency, count(a) as count
    from Assets a join a.assetCategory c
    where a.user.id = :userId
      and a.assetType.name = 'Financial Assets'
      and a.currency = :currency
      and a.deletedAt is null
    group by c.id, c.name, a.currency
    order by count(a) desc
    limit 5
    """)
    List<CategoryCount> topFinancialCategoriesByCount(@Param("userId") UUID userId, @Param("currency") Currency currency);

    @Query("""
    select a.currency as currency, avg(a.currentValue) as average
    from Assets a
    where a.user.id = :userId
      and a.deletedAt is null
    group by a.currency
    """)
    List<CurrencyAverage> averageAssetValueByCurrency(@Param("userId") UUID userId);

    @Query("""
    select a.currency as currency, avg(a.currentValue) as average
    from Assets a
    where a.user.id = :userId
      and a.assetType.name = 'Physical Assets'
      and a.deletedAt is null
    group by a.currency
    """)
    List<CurrencyAverage> averagePhysicalAssetsByCurrency(@Param("userId") UUID userId);

    @Query("""
    select a.currency as currency, avg(a.currentValue) as average
    from Assets a
    where a.user.id = :userId
      and a.assetType.name = 'Financial Assets'
      and a.deletedAt is null
    group by a.currency
    """)
    List<CurrencyAverage> averageFinancialAssetsByCurrency(@Param("userId") UUID userId);

    @Query("""
    select a
    from Assets a
    where a.user.id = :userId
      and a.currency = :currency
      and a.deletedAt is null
    order by a.currentValue desc
    limit 5
    """)
    List<Assets> findLargestAssets(@Param("userId") UUID userId, @Param("currency") Currency currency);

    @Query("""
    select a
    from Assets a
    where a.user.id = :userId
      and a.assetType.name = 'Physical Assets'
      and a.currency = :currency
      and a.deletedAt is null
    order by a.currentValue desc
    limit 5
    """)
    List<Assets> findLargestPhysicalAssets(@Param("userId") UUID userId, @Param("currency") Currency currency);

    @Query("""
    select a
    from Assets a
    where a.user.id = :userId
      and a.assetType.name = 'Financial Assets'
      and a.currency = :currency
      and a.deletedAt is null
    order by a.currentValue desc
    limit 5
    """)
    List<Assets> findLargestFinancialAssets(@Param("userId") UUID userId, @Param("currency") Currency currency);

    @Query("""
    select a.currency as currency, sum(a.currentValue) as total
    from Assets a
    where a.user.id = :userId
      and a.createdAt >= :start
      and a.createdAt < :end
      and a.deletedAt is null
    group by a.currency
    """)
    List<CurrencyTotal> valueAddedThisYear(@Param("userId") UUID userId, @Param("start") Instant start, @Param("end") Instant end);

    @Query("""
    select a.currency as currency, sum(a.currentValue) as total
    from Assets a
    where a.user.id = :userId
      and a.assetType.name = 'Physical Assets'
      and a.createdAt >= :start
      and a.createdAt < :end
      and a.deletedAt is null
    group by a.currency
    """)
    List<CurrencyTotal> physicalValueAddedThisYear(@Param("userId") UUID userId, @Param("start") Instant start, @Param("end") Instant end);

    @Query("""
    select a.currency as currency, sum(a.currentValue) as total
    from Assets a
    where a.user.id = :userId
      and a.assetType.name = 'Financial Assets'
      and a.createdAt >= :start
      and a.createdAt < :end
      and a.deletedAt is null
    group by a.currency
    """)
    List<CurrencyTotal> financialValueAddedThisYear(@Param("userId") UUID userId, @Param("start") Instant start, @Param("end") Instant end);


    @Query("""
    select a.currency as currency, sum(h.value) as total
    from Assets a
    join AssetValutionHistory h on h.asset = a
    where a.user.id = :userId
      and a.createdAt < :monthEnd
      and (a.deletedAt is null or a.deletedAt >= :monthEnd)
      and h.recordedAt = (
          select max(h2.recordedAt)
          from AssetValutionHistory h2
          where h2.asset = a
            and h2.recordedAt < :monthEnd
      )
    group by a.currency
    """)
    List<CurrencyTotal> portfolioValueAtMonthEnd(@Param("userId") UUID userId, @Param("monthEnd") Instant monthEnd);

    @Query("""
    select a.currency as currency, sum(h.value) as total
    from Assets a
    join AssetValutionHistory h on h.asset = a
    where a.user.id = :userId
      and a.assetType.name = 'Physical Assets'
      and a.createdAt < :monthEnd
      and (a.deletedAt is null or a.deletedAt >= :monthEnd)
      and h.recordedAt = (
          select max(h2.recordedAt)
          from AssetValutionHistory h2
          where h2.asset = a
            and h2.recordedAt < :monthEnd
      )
    group by a.currency
    """)
    List<CurrencyTotal> physicalPortfolioValueAtMonthEnd(@Param("userId") UUID userId, @Param("monthEnd") Instant monthEnd);

    @Query("""
    select a.currency as currency, sum(h.value) as total
    from Assets a
    join AssetValutionHistory h on h.asset = a
    where a.user.id = :userId
      and a.assetType.name = 'Financial Assets'
      and a.createdAt < :monthEnd
      and (a.deletedAt is null or a.deletedAt >= :monthEnd)
      and h.recordedAt = (
          select max(h2.recordedAt)
          from AssetValutionHistory h2
          where h2.asset = a
            and h2.recordedAt < :monthEnd
      )
    group by a.currency
    """)
    List<CurrencyTotal> financialPortfolioValueAtMonthEnd(@Param("userId") UUID userId, @Param("monthEnd") Instant monthEnd);

}
