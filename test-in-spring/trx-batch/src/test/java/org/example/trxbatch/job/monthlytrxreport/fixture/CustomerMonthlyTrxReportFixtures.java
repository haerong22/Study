package org.example.trxbatch.job.monthlytrxreport.fixture;

import org.example.trxbatch.dto.CustomerMonthlyTrxReport;
import org.example.trxbatch.dto.CustomerMonthlyTrxReportDetail;
import org.example.trxbatch.dto.enums.TransactionType;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class CustomerMonthlyTrxReportFixtures {

    public static CustomerMonthlyTrxReport FIXTURE_REPORT_OF_SINGLE_CUSTOMER_SINGLE_TRX() {
        CustomerMonthlyTrxReport report = new CustomerMonthlyTrxReport();
        report.setCustomerId(1L);
        report.setCustomerMonthlyTrxReportDetails(List.of((new CustomerMonthlyTrxReportDetail("acct01",
                "ticker01",
                LocalDateTime.now(),
                TransactionType.BUY,
                1000,
                10,
                BigInteger.valueOf(10000),
                "KRW"))));
        return report;
    }

    public static CustomerMonthlyTrxReport FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_1_THREE_TRXS() {
        CustomerMonthlyTrxReport reportOne = new CustomerMonthlyTrxReport();
        LocalDateTime transactionDateTimeOne = LocalDateTime.of(2024, 5, 1, 10, 20, 30, 123456);
        reportOne.setCustomerId(1L);
        reportOne.setCustomerMonthlyTrxReportDetails(
                List.of(
                        new CustomerMonthlyTrxReportDetail(
                                "acct01",
                                "ticker01",
                                transactionDateTimeOne,
                                TransactionType.BUY,
                                1000,
                                10,
                                BigInteger.valueOf(10000),
                                "KRW"
                        ),
                        new CustomerMonthlyTrxReportDetail(
                                "acct02",
                                "ticker02",
                                transactionDateTimeOne,
                                TransactionType.SELL,
                                2000,
                                20,
                                BigInteger.valueOf(40000),
                                "KRW"
                        ),
                        new CustomerMonthlyTrxReportDetail(
                                "acct03",
                                "ticker03",
                                transactionDateTimeOne,
                                TransactionType.BUY,
                                3000,
                                30,
                                BigInteger.valueOf(90000),
                                "KRW")
                )
        );
        return reportOne;
    }


    public static CustomerMonthlyTrxReport FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_2_THREE_TRXS() {
        LocalDateTime transactionDateTimeTwo = LocalDateTime.of(2024, 5, 2, 10, 20, 30, 123456);
        CustomerMonthlyTrxReport reportTwo = new CustomerMonthlyTrxReport();
        reportTwo.setCustomerId(2L);
        reportTwo.setCustomerMonthlyTrxReportDetails(
                List.of(
                        new CustomerMonthlyTrxReportDetail(
                                "acct04",
                                "ticker04",
                                transactionDateTimeTwo,
                                TransactionType.BUY,
                                1000,
                                10,
                                BigInteger.valueOf(10000),
                                "KRW"
                        ),
                        new CustomerMonthlyTrxReportDetail(
                                "acct05",
                                "ticker05",
                                transactionDateTimeTwo,
                                TransactionType.SELL,
                                2000,
                                20,
                                BigInteger.valueOf(40000),
                                "KRW"
                        ),
                        new CustomerMonthlyTrxReportDetail(
                                "acct06",
                                "ticker06",
                                transactionDateTimeTwo,
                                TransactionType.BUY,
                                3000,
                                30,
                                BigInteger.valueOf(90000),
                                "KRW"
                        )
                )
        );
        return reportTwo;
    }

}