package org.example.trxbatch.job.monthlytrxreport;

import org.example.trxbatch.dto.CustomerMonthlyTrxReport;
import org.example.trxbatch.dto.enums.ReportChannel;
import org.example.trxbatch.exception.TrxBatchNotSupportedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MonthlyTrxReportClassifierUnitTest {

    @Mock
    private MonthlyTrxReportViaAppMessengerWriter appMessengerWriter;

    @Mock
    private MonthlyTrxReportViaPostWriter postWriter;

    @Mock
    private MonthlyTrxReportViaEmailWriter emailWriter;

    private MonthlyTrxReportClassifier classifier;

    @BeforeEach
    void setUp() {
        classifier = new MonthlyTrxReportClassifier(postWriter, appMessengerWriter, emailWriter);
    }

    @Test
    void testClassify() {
        var emailReport = new CustomerMonthlyTrxReport();
        emailReport.setChannel(ReportChannel.EMAIL);
        var emailWriterResult = classifier.classify(emailReport);

        var postReport = new CustomerMonthlyTrxReport();
        postReport.setChannel(ReportChannel.POST);
        var postWriterResult = classifier.classify(postReport);

        var appMessengerReport = new CustomerMonthlyTrxReport();
        appMessengerReport.setChannel(ReportChannel.APP_MESSAGE);
        var appMessengerWriterResult = classifier.classify(appMessengerReport);

        // then
        assertEquals(emailWriter, emailWriterResult);
        assertEquals(postWriter, postWriterResult);
        assertEquals(appMessengerWriter, appMessengerWriterResult);
    }

    @Test
    void testClassifyWithUnknownChannel() {
        // given
        var report = new CustomerMonthlyTrxReport();
        report.setChannel(ReportChannel.NONE);

        // expect
        assertThrows(TrxBatchNotSupportedException.class, () -> classifier.classify(report));
    }
}