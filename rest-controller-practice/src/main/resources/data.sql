
insert into user(email, password, phone, reg_date, user_name, status, lock_yn)
values
   ('test11@naver.com', '$2a$10$2ElPbt2mwiAc9IYH3rgJz.YZInXlUd363utdyU0TWfne6Y3vKh8h6', '010-1234-1234', '2021-02-20 00:50:11.000000', 'kim', 'USING', 0),
   ('test22@gmail.com', '$2a$10$2ElPbt2mwiAc9IYH3rgJz.YZInXlUd363utdyU0TWfne6Y3vKh8h6', '010-4321-1111', '2021-02-25 12:33:16.000000', 'lee', 'USING', 0),
   ('test33@naver.com', '$2a$10$2ElPbt2mwiAc9IYH3rgJz.YZInXlUd363utdyU0TWfne6Y3vKh8h6', '010-5555-3333', now(), 'hong', 'USING', 0),
   ('test44@gmail.com', '$2a$10$2ElPbt2mwiAc9IYH3rgJz.YZInXlUd363utdyU0TWfne6Y3vKh8h6', '010-4343-2546', now(), 'park', 'STOP', 0);


insert into notice(contents, reg_date, title, user_id)
values
    ('내용1', now(), '제목1', 1),
    ('내용2', now(), '제목2', 1),
    ('내용3', now(), '제목3', 1),
    ('내용4', now(), '제목4', 2),
    ('내용5', now(), '제목5', 2),
    ('내용6', now(), '제목6', 3),
    ('내용7', now(), '제목7', 3),
    ('내용8', now(), '제목8', 3),
    ('내용9', now(), '제목9', 3);

insert into notice_like(notice_id, user_id)
values
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 2),
    (2, 3),
    (3, 1),
    (4, 1),
    (4, 2),
    (5, 2),
    (6, 4),
    (7, 1),
    (7, 4);

insert into board_type (board_name, reg_date, using_yn)
values
       ('게시판1', now(), 1),
       ('게시판2', now(), 0),
       ('게시판3', now(), 1);

insert into board (board_type_id, user_id, title, content, reg_date, top_yn)
values
       (1, 1, '게시글1', '게시글 내용1', now(), 0),
       (1, 2, '게시글2', '게시글 내용2', now(), 0),
       (1, 3, '게시글3', '게시글 내용3', now(), 0),
       (2, 1, '게시글4', '게시글 내용4', now(), 0),
       (2, 2, '게시글5', '게시글 내용5', now(), 0),
       (3, 1, '게시글6', '게시글 내용6', now(), 0),
       (3, 3, '게시글7', '게시글 내용7', now(), 0);