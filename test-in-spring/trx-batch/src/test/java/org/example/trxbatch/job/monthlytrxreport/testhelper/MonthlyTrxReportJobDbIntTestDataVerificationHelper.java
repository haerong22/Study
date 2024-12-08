package org.example.trxbatch.job.monthlytrxreport.testhelper;

import org.example.trxbatch.dto.enums.ReportChannel;
import org.jooq.DSLContext;

import java.time.LocalDate;

import static org.example.trxbatch.generated.Tables.APP_MESSAGE;
import static org.example.trxbatch.generated.Tables.MONTHLY_TRX_REPORT_RESULT;
import static org.jooq.impl.DSL.count;

public class MonthlyTrxReportJobDbIntTestDataVerificationHelper {

    public static final String TARGET_YEAR_MONTH = "2024-04";
    public static final Integer HEAVY_CUSTOMER_COUNT = 10;
    public static final Integer SUCCESSFUL_REPORT_SUCCESS_COUNT = 429;
    public static final Integer SUCCESSFUL_REPORT_VIA_POST_SUCCESS_COUNT = 143;
    public static final Integer SUCCESSFUL_REPORT_VIA_EMAIL_SUCCESS_COUNT = 142;
    public static final Integer SUCCESSFUL_REPORT_VIA_APP_MESSAGE_SUCCESS_COUNT = 144;
    public static final Integer SUCCESSFUL_APP_MESSAGE_DB_STORE_COUNT = SUCCESSFUL_REPORT_VIA_APP_MESSAGE_SUCCESS_COUNT;


    public static Integer getReportSuccessCount(DSLContext trxBatchDsl) {
        return trxBatchDsl.select(count())
                          .from(MONTHLY_TRX_REPORT_RESULT)
                          .where(MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH.eq(TARGET_YEAR_MONTH))
                          .and(MONTHLY_TRX_REPORT_RESULT.STATUS.eq("SUCCESS"))
                          .fetchOne()
                          .value1();
    }

    public static Integer getReportViaPostSuccessCount(DSLContext trxBatchDsl) {
        return trxBatchDsl.select(count())
                          .from(MONTHLY_TRX_REPORT_RESULT)
                          .where(MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH.eq(TARGET_YEAR_MONTH))
                          .and(MONTHLY_TRX_REPORT_RESULT.STATUS.eq("SUCCESS"))
                          .and(MONTHLY_TRX_REPORT_RESULT.CHANNEL.eq(ReportChannel.POST.name()))
                          .fetchOne()
                          .value1();
    }

    public static Integer getReportViaEmailSuccessCount(DSLContext trxBatchDsl) {
        return trxBatchDsl.select(count())
                          .from(MONTHLY_TRX_REPORT_RESULT)
                          .where(MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH.eq(TARGET_YEAR_MONTH))
                          .and(MONTHLY_TRX_REPORT_RESULT.STATUS.eq("SUCCESS"))
                          .and(MONTHLY_TRX_REPORT_RESULT.CHANNEL.eq(ReportChannel.EMAIL.name()))
                          .fetchOne()
                          .value1();
    }

    public static Integer getReportViaAppMessageSuccessCount(DSLContext trxBatchDsl) {
        return trxBatchDsl.select(count())
                          .from(MONTHLY_TRX_REPORT_RESULT)
                          .where(MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH.eq(TARGET_YEAR_MONTH))
                          .and(MONTHLY_TRX_REPORT_RESULT.STATUS.eq("SUCCESS"))
                          .and(MONTHLY_TRX_REPORT_RESULT.CHANNEL.eq(ReportChannel.APP_MESSAGE.name()))
                          .fetchOne()
                          .value1();
    }

    public static Integer getAppMessageDbStoredCount(DSLContext trxBatchDsl) {
        return trxBatchDsl.select(count())
                          .from(APP_MESSAGE)
                          .where(APP_MESSAGE.SEND_DATE.eq(LocalDate.of(2024, 5, 10)))
                          .fetchOne()
                          .value1();
    }

}