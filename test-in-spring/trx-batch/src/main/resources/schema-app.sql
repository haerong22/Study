CREATE TABLE CUSTOMER
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255),
    phone      VARCHAR(40)  NOT NULL,
    address    TEXT         NOT NULL,
    status     VARCHAR(20)  NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE ACCOUNT
(
    acct_no     VARCHAR(10) PRIMARY KEY,
    customer_id BIGINT      NOT NULL,
    type        VARCHAR(20) NOT NULL,
    balance     BIGINT      NOT NULL,
    status      VARCHAR(20) NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE CUSTOMER_COMM
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT      NOT NULL,
    type        VARCHAR(40) NOT NULL,
    channel     VARCHAR(20) NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (customer_id, type)
);

CREATE TABLE TRX
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticker_code    VARCHAR(40)  NOT NULL,
    ticker_name_kr VARCHAR(255) NOT NULL,
    acct_no        VARCHAR(10)  NOT NULL,
    price          BIGINT       NOT NULL,
    quantity       BIGINT       NOT NULL,
    currency       VARCHAR(3)   NOT NULL,
    type           VARCHAR(20)  NOT NULL,
    transaction_at TIMESTAMP    NOT NULL,
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE APP_MESSAGE
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT      NOT NULL,
    template_id BIGINT      NOT NULL,
    value1      VARCHAR(255),
    value2      VARCHAR(255),
    value3      VARCHAR(255),
    value4      VARCHAR(255),
    value5      VARCHAR(255),
    value6      VARCHAR(255),
    value7      VARCHAR(255),
    value8      VARCHAR(255),
    value9      VARCHAR(255),
    value10     VARCHAR(255),
    send_date   DATE,
    status      VARCHAR(40) NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE MONTHLY_TRX_REPORT_RESULT
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id       BIGINT                              NOT NULL,
    target_year_month CHAR(7)                             NOT NULL,
    channel           VARCHAR(20)                         NOT NULL,
    status            VARCHAR(20)                         NOT NULL,
    fail_reason       VARCHAR(40)                         NULL,
    fail_detail       TEXT                                NULL,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
);