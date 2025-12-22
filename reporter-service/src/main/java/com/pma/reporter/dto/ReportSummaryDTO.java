package com.pma.reporter.dto; 

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List; 

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor 
public class ReportSummaryDTO {
    private Long totalInvoices;
    private BigDecimal totalRevenue;
    private List<DrugReportDTO> topSellingDrugs;
}