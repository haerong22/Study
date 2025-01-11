CREATE TABLE `user`
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    email            VARCHAR(255) NOT NULL,
    provider         VARCHAR(10) NOT NULL,
    social_id        VARCHAR(255) NOT NULL
);