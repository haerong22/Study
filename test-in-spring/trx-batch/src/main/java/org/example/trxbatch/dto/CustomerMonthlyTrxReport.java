package org.example.trxbatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.trxbatch.dto.enums.ReportChannel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMonthlyTrxReport {
    private Long customerId;
    private ReportChannel channel;
    private List<CustomerMonthlyTrxReportDetail> customerMonthlyTrxReportDetails;
}