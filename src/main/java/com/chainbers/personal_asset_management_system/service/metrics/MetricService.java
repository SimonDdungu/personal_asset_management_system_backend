package com.chainbers.personal_asset_management_system.service.metrics;


import com.chainbers.personal_asset_management_system.dto.metrics.*;
import com.chainbers.personal_asset_management_system.repository.assets.AssetsRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MetricService {
    private final AssetsRepository assetsRepository;


    @Transactional(readOnly = true)
    public List<CurrencyTotal> getTotalNetWorth(UUID userId) {
        return assetsRepository.sumByCurrency(userId);
    }

    @Transactional(readOnly = true)
    public List<CurrencyTotal> getTotalPhysicalValue(UUID userId) {
        return assetsRepository.sumPhysicalAssetsByCurrency(userId);
    }

    @Transactional(readOnly = true)
    public List<CurrencyTotal> getTotalFinancialValue(UUID userId) {
        return assetsRepository.sumFinancialAssetsByCurrency(userId);
    }

    @Transactional(readOnly = true)
    public List<CurrencyTotal> getValueAddedThisYear(UUID userId) {
        Instant start = LocalDate.of(Year.now().getValue(), 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        Instant end = LocalDate.of(Year.now().getValue() + 1, 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        return assetsRepository.valueAddedThisYear(userId, start, end);
    }

    @Transactional(readOnly = true)
    public List<CurrencyTotal> getPhysicalValueAddedThisYear(UUID userId) {
        Instant start = LocalDate.of(Year.now().getValue(), 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        Instant end = LocalDate.of(Year.now().getValue() + 1, 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        return assetsRepository.physicalValueAddedThisYear(userId, start, end);
    }

    @Transactional(readOnly = true)
    public List<CurrencyTotal> getFinancialValueAddedThisYear(UUID userId) {
        Instant start = LocalDate.of(Year.now().getValue(), 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        Instant end = LocalDate.of(Year.now().getValue() + 1, 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        return assetsRepository.financialValueAddedThisYear(userId, start, end);
    }

    @Transactional(readOnly = true)
    public List<PortfolioMonthlyTrends> getPortfolioTrend(UUID userId, int year) {
        return buildPortfolioTrend(
                userId,
                year,
                monthEnd -> assetsRepository.portfolioValueAtMonthEnd(userId, monthEnd)
        );
    }

    @Transactional(readOnly = true)
    public List<PortfolioMonthlyTrends> getPhysicalPortfolioTrend(UUID userId, int year) {
        return buildPortfolioTrend(
                userId,
                year,
                monthEnd -> assetsRepository.physicalPortfolioValueAtMonthEnd(userId, monthEnd)
        );
    }

    @Transactional(readOnly = true)
    public List<PortfolioMonthlyTrends> getFinancialPortfolioTrend(UUID userId, int year) {
        return buildPortfolioTrend(
                userId,
                year,
                monthEnd -> assetsRepository.financialPortfolioValueAtMonthEnd(userId, monthEnd)
        );
    }


    private List<PortfolioMonthlyTrends> buildPortfolioTrend(UUID userId, int year, Function<Instant, List<CurrencyTotal>> query) {

        Map<Currency, BigDecimal[]> currencyTotals = new LinkedHashMap<>();

        ZoneId zone = ZoneId.systemDefault();

        for (int month = 1; month <= 12; month++) {

            Instant monthEnd = YearMonth.of(year, month)
                    .plusMonths(1)
                    .atDay(1)
                    .atStartOfDay(zone)
                    .toInstant();

            List<CurrencyTotal> monthlyTotals = query.apply(monthEnd);

            for (CurrencyTotal currencyTotal : monthlyTotals) {

                BigDecimal[] monthlyValues = currencyTotals.computeIfAbsent(
                        currencyTotal.currency(),
                        currency -> new BigDecimal[12]
                );

                monthlyValues[month - 1] = currencyTotal.total();
            }
        }

        List<PortfolioMonthlyTrends> result = new ArrayList<>();

        for (Map.Entry<Currency, BigDecimal[]> entry : currencyTotals.entrySet()) {

            List<MonthlyPortfolioValue> monthlyValues = new ArrayList<>();

            for (int month = 1; month <= 12; month++) {

                BigDecimal total = entry.getValue()[month - 1];

                monthlyValues.add(new MonthlyPortfolioValue(
                        Month.of(month).name(),
                        total != null ? total : BigDecimal.ZERO
                ));
            }

            result.add(new PortfolioMonthlyTrends(
                    year,
                    entry.getKey(),
                    monthlyValues
            ));
        }

        return result;
    }




}
