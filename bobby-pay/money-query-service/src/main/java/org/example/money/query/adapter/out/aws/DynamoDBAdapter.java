package org.example.money.query.adapter.out.aws;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.money.query.adapter.axon.QueryMoneySumByAddress;
import org.example.money.query.application.port.out.GetMoneySumByAddressPort;
import org.example.money.query.application.port.out.InsertMoneyIncreaseEventByAddress;
import org.example.money.query.domain.MoneySumByRegion;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class DynamoDBAdapter implements GetMoneySumByAddressPort, InsertMoneyIncreaseEventByAddress {
    private static final String TABLE_NAME = "MoneyIncreaseEventByRegion";
    private static final String ACCESS_KEY = "";
    private static final String SECRET_KEY = "";
    private final DynamoDbClient dynamoDbClient;
    private final MoneySumByAddressMapper moneySumByAddressMapper;

    public DynamoDBAdapter() {
        this.moneySumByAddressMapper = new MoneySumByAddressMapper();
        AwsBasicCredentials credentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        this.dynamoDbClient = DynamoDbClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Override
    public int getMoneySumByAddress(String address) {
        String pk = address;
        String sk = "-1";
        return getItem(pk, sk).getBalance();
    }

    @Override
    public void insertMoneyIncreaseEventByAddress(String addressName, int moneyIncrease) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

        // 1. 일별 충전 금액 순
        // - PK: 강남구#231203 SK: 5,000, balance: 5,000
        String pk = addressName + "#" + today;
        String sk = String.valueOf(moneyIncrease);
        putItem(pk, sk, moneyIncrease);

        // 2. 지역별/일별 합계
        // - PK: 강남구#231203#summary SK: -1 balance: + 5,000
        String summaryPk = pk + "#summary";
        String summarySk = "-1";
        MoneySumByAddress moneySumByAddress = getItem(summaryPk, summarySk);
        if (moneySumByAddress == null) {
            putItem(summaryPk, summarySk, moneyIncrease);
        } else {
            int balance = moneySumByAddress.getBalance();
            balance += moneyIncrease;
            updateItem(summaryPk, summarySk, balance);
        }

        // 3. 지역별 입금 합계
        // - PK: 강남구 SK: -1 balance: + 5,000
        String summaryPk2 = addressName;
        String summarySk2 = "-1";
        MoneySumByAddress moneySumByAddress2 = getItem(summaryPk2, summarySk2);
        if (moneySumByAddress2 == null) {
            putItem(summaryPk2, summarySk2, moneyIncrease);
        } else {
            int balance2 = moneySumByAddress2.getBalance();
            balance2 += moneyIncrease;
            updateItem(summaryPk2, summarySk2, balance2);
        }
    }

    private void putItem(String pk, String sk, int balance) {
        try {
            String balanceStr = String.valueOf(balance);
            HashMap<String, AttributeValue> attrMap = new HashMap<>();
            attrMap.put("PK", AttributeValue.builder().s(pk).build());
            attrMap.put("SK", AttributeValue.builder().s(sk).build());
            attrMap.put("balance", AttributeValue.builder().n(balanceStr).build());

            PutItemRequest request = PutItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .item(attrMap)
                    .build();

            dynamoDbClient.putItem(request);
        } catch (DynamoDbException e) {
            log.error("Error adding an item to the table: {}", e.getMessage());
        }
    }

    private MoneySumByAddress getItem(String pk, String sk) {
        try {
            HashMap<String, AttributeValue> attrMap = new HashMap<>();
            attrMap.put("PK", AttributeValue.builder().s(pk).build());
            attrMap.put("SK", AttributeValue.builder().s(sk).build());

            GetItemRequest request = GetItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(attrMap)
                    .build();

            GetItemResponse response = dynamoDbClient.getItem(request);
            if (response.hasItem()) {
                return moneySumByAddressMapper.mapToMoneySumByAddress(response.item());
            } else {
                return null;
            }

        } catch (DynamoDbException e) {
            log.error("Error getting an item from the table: {}", e.getMessage());
        }
        return null;
    }

    private void queryItem(String id) {
        try {
            HashMap<String, Condition> attrMap = new HashMap<>();
            attrMap.put("PK", Condition.builder()
                    .attributeValueList(AttributeValue.builder().s(id).build())
                    .comparisonOperator(ComparisonOperator.EQ)
                    .build());

            QueryRequest request = QueryRequest.builder()
                    .tableName(TABLE_NAME)
                    .keyConditions(attrMap)
                    .build();

            QueryResponse response = dynamoDbClient.query(request);
            response.items().forEach(System.out::println);
        } catch (DynamoDbException e) {
            log.error("Error getting an item from the table: {}", e.getMessage());
        }
    }

    private void updateItem(String pk, String sk, int balance) {
        try {
            HashMap<String, AttributeValue> attrMap = new HashMap<>();
            attrMap.put("PK", AttributeValue.builder().s(pk).build());
            attrMap.put("SK", AttributeValue.builder().s(sk).build());

            String balanceStr = String.valueOf(balance);
            // Create an UpdateItemRequest
            UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(attrMap)
                    .attributeUpdates(
                            new HashMap<>() {{
                                put("balance", AttributeValueUpdate.builder()
                                        .value(AttributeValue.builder().n(balanceStr).build())
                                        .action(AttributeAction.PUT)
                                        .build());
                            }}
                    ).build();


            UpdateItemResponse response = dynamoDbClient.updateItem(updateItemRequest);

            // 결과 출력.
            Map<String, AttributeValue> attributes = response.attributes();
            if (attributes != null) {
                for (Map.Entry<String, AttributeValue> entry : attributes.entrySet()) {
                    String attributeName = entry.getKey();
                    AttributeValue attributeValue = entry.getValue();
                    log.info("{}: {}", attributeName, attributeValue);
                }
            } else {
                log.error("Item was updated, but no attributes were returned.");
            }
        } catch (DynamoDbException e) {
            log.error("Error getting an item from the table: {}", e.getMessage());
        }
    }

    @QueryHandler
    public MoneySumByRegion query(QueryMoneySumByAddress query, GetMoneySumByAddressPort getMoneySumByAddressPort) {

        return MoneySumByRegion.generateMoneySumByRegion(
                new MoneySumByRegion.MoneySumByRegionId(UUID.randomUUID().toString()),
                new MoneySumByRegion.RegionName(query.getAddress()),
                new MoneySumByRegion.MoneySum(getMoneySumByAddress(query.getAddress()))
        );
    }
}