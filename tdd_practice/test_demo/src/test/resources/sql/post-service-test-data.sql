INSERT INTO users(id, email, nickname, address, certification_code, status, last_login_at)
VALUES (1, 'test@test.com', 'bobby', 'seoul', '1234-1234-1234-1234', 'ACTIVE', 0);

INSERT INTO users(id, email, nickname, address, certification_code, status, last_login_at)
VALUES (2, 'test2@test.com', 'bobby2', 'busan', '4321-1234-1234-1234', 'PENDING', 0);

INSERT INTO posts(id, content, created_at, modified_at, user_id)
VALUES (1, 'helloworld', 1678530673958, 0, 1);
