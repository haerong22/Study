DROP TABLE COURSE_RATINGS;
CREATE TABLE COURSE_RATINGS
(
    id         INT      NOT NULL AUTO_INCREMENT COMMENT '평가의 고유 식별자',
    course_id  INT      NOT NULL COMMENT '평가가 속한 강의의 ID, COURSES 테이블 참조',
    user_id    INT      NOT NULL COMMENT '평가를 남긴 사용자의 ID',
    rating     TINYINT  NOT NULL COMMENT '사용자가 부여한 평점 (1-5)',
    comment    TEXT     NULL COMMENT '사용자가 남긴 평가 코멘트',
    created_at DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '레코드 생성 시간',
    PRIMARY KEY (id)
) COMMENT '사용자의 강의 평가 정보를 저장하는 테이블';

DROP TABLE COURSE_SESSIONS;
CREATE TABLE COURSE_SESSIONS
(
    id        INT          NOT NULL AUTO_INCREMENT COMMENT '세션의 고유 식별자',
    course_id INT          NOT NULL COMMENT '해당 세션이 속한 강의의 ID, COURSES 테이블 참조',
    title     VARCHAR(255) NOT NULL COMMENT '세션의 제목',
    PRIMARY KEY (id)
) COMMENT '각 강의의 세션들을 관리하는 테이블';

DROP TABLE COURSES;
CREATE TABLE COURSES
(
    id            INT          NOT NULL AUTO_INCREMENT COMMENT '강의의 고유 식별자',
    title         VARCHAR(255) NOT NULL COMMENT '강의 제목',
    description   TEXT         NULL COMMENT '강의에 대한 자세한 설명',
    instructor_id INT          NOT NULL COMMENT '강사의 식별자, 외래 키로 사용될 수 있음',
    created_at    DATETIME     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '강의 생성 시간',
    PRIMARY KEY (id)
) COMMENT '강의 기본 정보를 저장하는 테이블';

ALTER TABLE COURSE_SESSIONS
    ADD CONSTRAINT FK_COURSES_TO_COURSE_SESSIONS
        FOREIGN KEY (course_id)
            REFERENCES COURSES (id);

ALTER TABLE COURSE_RATINGS
    ADD CONSTRAINT FK_COURSES_TO_COURSE_RATINGS
        FOREIGN KEY (course_id)
            REFERENCES COURSES (id);