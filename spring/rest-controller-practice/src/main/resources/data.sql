
insert into user(email, password, phone, reg_date, user_name, status, lock_yn, password_reset_yn)
values
   ('haerong22@gmail.com', '$2a$10$2ElPbt2mwiAc9IYH3rgJz.YZInXlUd363utdyU0TWfne6Y3vKh8h6', '010-1234-1234', '2021-02-20 00:50:11.000000', 'kim', 'USING', 0, 0),
   ('test22@test.com', '$2a$10$2ElPbt2mwiAc9IYH3rgJz.YZInXlUd363utdyU0TWfne6Y3vKh8h6', '010-4321-1111', '2021-02-25 12:33:16.000000', 'lee', 'USING', 0, 0),
   ('test33@test.com', '$2a$10$2ElPbt2mwiAc9IYH3rgJz.YZInXlUd363utdyU0TWfne6Y3vKh8h6', '010-5555-3333', now(), 'hong', 'USING', 0, 0),
   ('test44@test.com', '$2a$10$2ElPbt2mwiAc9IYH3rgJz.YZInXlUd363utdyU0TWfne6Y3vKh8h6', '010-4343-2546', now(), 'park', 'STOP', 0, 0);


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
       ('게시판2', now(), 1),
       ('게시판3', now(), 0),
       ('문의게시판', now(), 1);

insert into board (board_type_id, user_id, title, content, reg_date, top_yn)
values
       (1, 1, '게시글1', '게시글 내용1', now(), 0),
       (1, 2, '게시글2', '게시글 내용2', now(), 0),
       (1, 3, '게시글3', '게시글 내용3', now(), 0),
       (2, 1, '게시글4', '게시글 내용4', now(), 0),
       (2, 2, '게시글5', '게시글 내용5', now(), 0),
       (3, 1, '게시글6', '게시글 내용6', now(), 0),
       (3, 3, '게시글7', '게시글 내용7', now(), 0),
       (4, 1, '문의제목1', '문의 내용1', now(), 0);

insert into board_comment (comments, reg_date, board_id, user_id)
values
    ('게시글 1의 댓글1', now(), 1, 1),
    ('게시글 1의 댓글2', now(), 1, 2),
    ('게시글 2의 댓글1', now(), 2, 1),
    ('게시글 2의 댓글2', now(), 2, 3);

INSERT INTO MAIL_TEMPLATE(TEMPLATE_ID, TITLE, CONTENTS, SEND_EMAIL, SEND_USER_NAME, REG_DATE)
VALUES
        ('USER_RESET_PASSWORD',
         '{USER_NAME}님의 비밀번호 초기화 요청입니다.',
         '<div><p>{USER_NAME}님 안녕하세요.</p><p>아래 링크를 클릭하여, 비밀번호를 초기화해 주세요.</p><p><a href="{SERVER_URL}/reset?key={RESET_PASSWORD_KEY}">초기화</a></p></div>',
         'test.email.12588@gmail.com', '관리자', now()),
        ('BOARD_ADD',
         '{USER_NAME}님이 글을 게시하였습니다.',
         '<div><p>제목: {BOARD_TITLE}</p><p>내용</p><div>{BOARD_CONTENTS}</div></div>',
         'test.email.12588@gmail.com', '관리자', now()),
        ('BOARD_REPLY',
         '{USER_NAME}님이 글에 답변이 작성되었습니다.',
         '<div><p>제목: {BOARD_TITLE}</p><p>내용</p><div>{BOARD_CONTENTS}</div><p>답변</p><div>{BOARD_REPLY_CONTENTS}</div></div>',
         'test.email.12588@gmail.com', '관리자', now()),
        ('USER_SERVICE_NOTICE',
         '{USER_NAME}님 안녕하세요.',
         '<div><p>개인정보 이용내역 안내</p><p>서비스 이용중</p></div>',
         'test.email.12588@gmail.com', '관리자', now());

insert into logs (text, reg_date)
values
        ('로그1', now()),
        ('로그2', now()),
        ('로그3', now()),
        ('로그4', now()),
        ('로그5', now());
