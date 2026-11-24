package com.chainbers.personal_asset_management_system.controller.metrics;


import com.chainbers.personal_asset_management_system.dto.metrics.CurrencyTotal;
import com.chainbers.personal_asset_management_system.dto.metrics.PortfolioMonthlyTrends;
import com.chainbers.personal_asset_management_system.entity.user.User;
import com.chainbers.personal_asset_management_system.service.metrics.MetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
public class MetricsController {
    private final MetricService metricService;

    @GetMapping("/total-networth")
    public List<CurrencyTotal> totalNetworth(@AuthenticationPrincipal User user){
        return metricService.getTotalNetWorth(user.getId());
    }

    @GetMapping("/total/physical/value")
    public List<CurrencyTotal> totalPhysicalValue(@AuthenticationPrincipal User user){
        return metricService.getTotalPhysicalValue(user.getId());
    }


    @GetMapping("/total/financial/value")
    public List<CurrencyTotal> totalFinancialValue(@AuthenticationPrincipal User user){
        return metricService.getTotalFinancialValue(user.getId());
    }

    @GetMapping("/value-added")
    public List<CurrencyTotal> getValueAddedThisYear(@AuthenticationPrincipal User user) {
        return metricService.getValueAddedThisYear(user.getId());
    }

    @GetMapping("/physical/value-added")
    public List<CurrencyTotal> getPhysicalValueAddedThisYear(@AuthenticationPrincipal User user) {
        return metricService.getPhysicalValueAddedThisYear(user.getId());
    }

    @GetMapping("/financial/value-added")
    public List<CurrencyTotal> getFinancialValueAddedThisYear(@AuthenticationPrincipal User user) {
        return metricService.getFinancialValueAddedThisYear(user.getId());
    }

    @GetMapping("/year/trend")
    public List<PortfolioMonthlyTrends> getPortfolioTrend(@RequestParam int year, @AuthenticationPrincipal User user) {
        return metricService.getPortfolioTrend(user.getId(), year);
    }

    @GetMapping("/year/physical/trend")
    public List<PortfolioMonthlyTrends> getPhysicalPortfolioTrend(@RequestParam int year, @AuthenticationPrincipal User user) {
        return metricService.getPhysicalPortfolioTrend(user.getId(), year);
    }

    @GetMapping("/year/financial/trend")
    public List<PortfolioMonthlyTrends> getFinancialPortfolioTrend(@RequestParam int year, @AuthenticationPrincipal User user) {
        return metricService.getFinancialPortfolioTrend(user.getId(), year);
    }

}
