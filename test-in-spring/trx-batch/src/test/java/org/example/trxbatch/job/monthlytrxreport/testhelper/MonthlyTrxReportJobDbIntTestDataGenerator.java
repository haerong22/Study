package org.example.trxbatch.job.monthlytrxreport.testhelper;

import org.apache.commons.lang3.tuple.Pair;
import org.example.trxbatch.dto.enums.CustomerCommType;
import org.example.trxbatch.dto.enums.ReportChannel;
import org.example.trxbatch.dto.enums.TransactionType;
import org.jooq.DSLContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.example.trxbatch.generated.Tables.CUSTOMER_COMM;
import static org.example.trxbatch.generated.Tables.TRX;
import static org.example.trxbatch.generated.tables.Account.ACCOUNT;
import static org.example.trxbatch.generated.tables.Customer.CUSTOMER;

public class MonthlyTrxReportJobDbIntTestDataGenerator {

    private static final String ACCOUNT_STATUS_ACTIVE = "ACTIVE";
    private static final String CURRENCY_KRW = "KRW";
    private static final int CUSTOMER_SIZE = 500;
    private static final int ACCOUNT_NUMBER_START = 1_000_000_000;
    private static final List<Pair<String, String>> TICKERS = List.of(Pair.of("TICK001", "삼성전자"),
                                                                      Pair.of("TICK002", "SK하이닉스"),
                                                                      Pair.of("TICK003", "LG화학"),
                                                                      Pair.of("TICK004", "NAVER"),
                                                                      Pair.of("TICK005", "삼성바이오로직스"),
                                                                      Pair.of("TICK006", "현대차"),
                                                                      Pair.of("TICK007", "셀트리온"),
                                                                      Pair.of("TICK008", "삼성SDI"),
                                                                      Pair.of("TICK009", "카카오"),
                                                                      Pair.of("TICK010", "LG전자"));

    private int lastCreatedAccountNumber = ACCOUNT_NUMBER_START;
    private final DSLContext trxBatchDsl;

    public MonthlyTrxReportJobDbIntTestDataGenerator(DSLContext trxBatchDsl) {
        this.trxBatchDsl = trxBatchDsl;
    }

//    @Test
    public void setUpData() {
        generateAccountData();
        generateCustomerData();
        generateCustomerCommData();
        generateTrxData();
    }

    private void generateAccountData() {
        var insertStep = trxBatchDsl.insertInto(ACCOUNT,
                                                ACCOUNT.ACCT_NO,
                                                ACCOUNT.CUSTOMER_ID,
                                                ACCOUNT.TYPE,
                                                ACCOUNT.BALANCE,
                                                ACCOUNT.STATUS);

        int accountNumber = 1_000_000_000;
        for (long customerId = 1; customerId <= CUSTOMER_SIZE; customerId++) {
            // Primary account
            accountNumber++;
            insertStep = insertStep.values(String.valueOf(accountNumber),
                                           customerId,
                                           "PRIMARY",
                                           getBalanceForCustomer(customerId),
                                           ACCOUNT_STATUS_ACTIVE);

            // Secondary account for customer IDs ending in 0, 1, 2, 3, 4
            if (customerId % 10 < 5) {
                accountNumber++;
                insertStep = insertStep.values(String.valueOf(accountNumber),
                                               customerId,
                                               "SECONDARY",
                                               getPensionBalanceForCustomer(customerId),
                                               ACCOUNT_STATUS_ACTIVE);
            }
        }

        this.lastCreatedAccountNumber = accountNumber;
        insertStep.execute();
    }

    private Long getBalanceForCustomer(long customerId) {
        return 100 * (customerId % 10);
    }

    private Long getPensionBalanceForCustomer(long customerId) {
        return 150 * customerId;
    }

    private void generateCustomerData() {
        var insertStep = trxBatchDsl.insertInto(CUSTOMER,
                                                CUSTOMER.NAME,
                                                CUSTOMER.EMAIL,
                                                CUSTOMER.PHONE,
                                                CUSTOMER.ADDRESS,
                                                CUSTOMER.STATUS);

        for (int i = 1; i <= CUSTOMER_SIZE; i++) {
            String name = "Customer" + i;
            String email = "customer" + i + "@example.com";
            String phone = "555-" + String.format("%04d", i);
            String address = i + " Main St";

            insertStep = insertStep.values(name, email, phone, address, ACCOUNT_STATUS_ACTIVE);
        }
        insertStep.execute();
    }


    private void generateCustomerCommData() {
        var insertStep =
                trxBatchDsl.insertInto(CUSTOMER_COMM, CUSTOMER_COMM.CUSTOMER_ID, CUSTOMER_COMM.TYPE, CUSTOMER_COMM.CHANNEL);

        for (long customerId = 1; customerId <= CUSTOMER_SIZE; customerId++) {
            if (customerId % 10 == 0) {
                continue;
            }
            String channel = switch ((int) (customerId % 10)) {
                case 1, 2, 3 -> ReportChannel.EMAIL.name();
                case 4, 5, 6 -> ReportChannel.APP_MESSAGE.name();
                case 7, 8, 9 -> ReportChannel.POST.name();
                default -> throw new IllegalStateException("Unexpected value: " + customerId % 10);
            };
            insertStep = insertStep.values(customerId, CustomerCommType.MONTHLY_TRX_REPORT.name(), channel);
        }

        insertStep.execute();
    }


    private void generateTrxData() {
        var insertStep = trxBatchDsl.insertInto(TRX,
                                                TRX.TICKER_CODE,
                                                TRX.TICKER_NAME_KR,
                                                TRX.ACCT_NO,
                                                TRX.PRICE,
                                                TRX.CURRENCY,
                                                TRX.QUANTITY,
                                                TRX.TYPE,
                                                TRX.TRANSACTION_AT);

        Random random = new Random();
        for (int acctNo = ACCOUNT_NUMBER_START + 1; acctNo <= this.lastCreatedAccountNumber; acctNo++) {
            int transactionCount = random.nextInt(41);

            for (int i = 0; i < transactionCount; i++) {
                int tickerIndex = random.nextInt(TICKERS.size());
                String ticker = TICKERS.get(tickerIndex).getLeft();
                String tickerName = TICKERS.get(tickerIndex).getRight();
                long price = 100 + random.nextInt(5) * 100;
                long quantity = 1 + random.nextInt(100);
                TransactionType[] types = TransactionType.values();
                String type = types[random.nextInt(types.length)].name();
                LocalDateTime transactionAt =
                        LocalDateTime.of(2024, 4, 1, 10, 31, random.nextInt(60)).plusDays(random.nextInt(40));

                insertStep = insertStep.values(ticker,
                                               tickerName,
                                               String.valueOf(acctNo),
                                               price,
                                               CURRENCY_KRW,
                                               quantity,
                                               type,
                                               transactionAt);
            }
        }

        insertStep.execute();
    }
}