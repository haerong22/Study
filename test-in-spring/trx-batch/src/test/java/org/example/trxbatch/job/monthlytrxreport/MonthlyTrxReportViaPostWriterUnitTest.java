package org.example.trxbatch.job.monthlytrxreport;

import org.example.trxbatch.dto.CustomerMonthlyTrxReport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.example.trxbatch.job.monthlytrxreport.fixture.CustomerMonthlyTrxReportFixtures.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class MonthlyTrxReportViaPostWriterUnitTest {
    private MonthlyTrxReportViaPostWriter writer;

    MockedStatic<Paths> mockedPaths;

    @BeforeEach
    public void setUp() {
        mockedPaths = Mockito.mockStatic(Paths.class);
        mockedPaths.when(() -> Paths.get(any(), any())).thenReturn(Path.of("/dummy-path"));

        writer = new MonthlyTrxReportViaPostWriter("2023-05");
        ReflectionTestUtils.setField(writer, "csvFileSavePath", "/dummy-path");
    }

    @AfterEach
    public void tearDown() {
        mockedPaths.close();
    }

    @Test
    void testEmpty() throws Exception {
        // given
        Chunk<CustomerMonthlyTrxReport> reports = new Chunk<>();

        try (MockedConstruction<FlatFileItemWriter> mockFlatFileItemWriter = Mockito.mockConstruction(FlatFileItemWriter.class)) {
            // when
            writer.write(reports);

            // then
            assertEquals(0, mockFlatFileItemWriter.constructed().size());
        }
    }

    @Test
    void testSuccess() throws Exception {
        Chunk<CustomerMonthlyTrxReport> reports = new Chunk<>(FIXTURE_REPORT_OF_SINGLE_CUSTOMER_SINGLE_TRX());

        try (MockedConstruction<FlatFileItemWriter> mockFlatFileItemWriter = Mockito.mockConstruction(FlatFileItemWriter.class)) {
            // when
            writer.write(reports);

            // then
            assertEquals(1, mockFlatFileItemWriter.constructed().size());
        }


        reports = new Chunk<>(
                FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_1_THREE_TRXS(),
                FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_2_THREE_TRXS()
        );

        try (MockedConstruction<FlatFileItemWriter> mockFlatFileItemWriter = Mockito.mockConstruction(FlatFileItemWriter.class)) {
            // when
            writer.write(reports);

            // then
            assertEquals(2, mockFlatFileItemWriter.constructed().size());
        }
    }

    @Test
    void testFailure() throws Exception {
        Chunk<CustomerMonthlyTrxReport> reports = new Chunk<>(FIXTURE_REPORT_OF_SINGLE_CUSTOMER_SINGLE_TRX());

        try (
                MockedConstruction<FlatFileItemWriter> mockFlatFileItemWriter = Mockito.mockConstruction(FlatFileItemWriter.class,
                        (mock, context) -> doThrow(new RuntimeException("test exception")).when(mock).open(any()))
        ) {
            // when
            writer.write(reports);

            // then
            assertEquals(1, mockFlatFileItemWriter.constructed().size());
        }
    }

    @Test
    void testFailure2() throws Exception {
        Chunk<CustomerMonthlyTrxReport> reports = new Chunk<>(
                FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_1_THREE_TRXS(),
                FIXTURE_REPORT_OF_SINGLE_CUSTOMER_WITH_ID_2_THREE_TRXS()
        );

        try (
                MockedConstruction<FlatFileItemWriter> mockFlatFileItemWriter = Mockito.mockConstruction(FlatFileItemWriter.class,
                        (mock, context) -> doThrow(new RuntimeException("test exception")).when(mock).open(any()))
        ) {
            // when
            writer.write(reports);

            // then
            assertEquals(2, mockFlatFileItemWriter.constructed().size());
        }
    }
}