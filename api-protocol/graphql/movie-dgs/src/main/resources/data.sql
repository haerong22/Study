-- 감독 데이터 삽입
INSERT INTO director (id, name) VALUES (101, 'Christopher Nolan');
INSERT INTO director (id, name) VALUES (102, 'Quentin Tarantino');

-- 영화 데이터 삽입
INSERT INTO movie (id, title, release_date, director_id) VALUES (101, 'Inception', '2010-07-16', 101);
INSERT INTO movie (id, title, release_date, director_id) VALUES (102, 'Interstellar', '2014-11-07', 101);
INSERT INTO movie (id, title, release_date, director_id) VALUES (103, 'Django Unchained', '2012-12-25', 102);

-- 사용자 데이터 삽입
INSERT INTO users (id, username, email) VALUES (101, 'Burger', 'burger@moview.com');
INSERT INTO users (id, username, email) VALUES (102, 'John Doe', 'john@moview.com');
INSERT INTO users (id, username, email) VALUES (103, 'Jane Doe', 'jane@moview.com');
INSERT INTO users (id, username, email) VALUES (104, 'Bob', 'bob@moview.com');

-- 리뷰 데이터 삽입
INSERT INTO review (id, rating, comment, user_id, movie_id) VALUES (101, 5, 'Amazing movie!', 101, 101);
INSERT INTO review (id, rating, comment, user_id, movie_id) VALUES (102, 4, 'Very interesting.', 102, 101);
INSERT INTO review (id, rating, comment, user_id, movie_id) VALUES (103, 5, 'A masterpiece.', 101, 102);
INSERT INTO review (id, rating, comment, user_id, movie_id) VALUES (104, 3, 'Great performances.', 102, 103);