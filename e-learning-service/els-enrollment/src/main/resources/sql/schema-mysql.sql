-- 결제 정보를 저장하는 테이블
DROP TABLE payments;
CREATE TABLE payments
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    user_id        INT                                 NOT NULL,
    payment_type   ENUM ('COURSE', 'SUBSCRIPTION')     NOT NULL, -- 결제 유형 구분
    amount         DECIMAL(10, 2)                      NOT NULL,
    payment_method VARCHAR(50)                         NOT NULL,
    payment_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- 강의 등록 정보를 저장하는 테이블
DROP TABLE enrollments;
CREATE TABLE enrollments
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    user_id           INT                                 NOT NULL,
    course_id         INT                                 NOT NULL,
    payment_id        INT                                 NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (payment_id) REFERENCES payments (id)
);

-- 사용자의 구독 정보를 저장하는 테이블
DROP TABLE subscriptions;
CREATE TABLE subscriptions
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT                                 NOT NULL,
    payment_id INT                                 NOT NULL,
    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    end_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (payment_id) REFERENCES payments (id)
);