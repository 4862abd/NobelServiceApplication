DROP TABLE IF EXISTS `MEMBER`;

CREATE TABLE `MEMBER`
(
    `ID`        BIGINT       NOT NULL COMMENT 'ID',
    `USERNAME`  VARCHAR(255) NOT NULL COMMENT '로그인아이디',
    `EMAIL`     VARCHAR(255) NOT NULL COMMENT '이메일',
    `PASSWORD`  VARCHAR(255) NOT NULL COMMENT '비밀번호',
    `INIT_DATE` DATETIME NULL COMMENT '가입일',
    `NICKNAME`  VARCHAR(255) NOT NULL COMMENT '별명'
);

DROP TABLE IF EXISTS `AUTHORITY`;

CREATE TABLE `AUTHORITY`
(
    `ID`             BIGINT      NOT NULL COMMENT 'ID',
    `AUTHORITY_NAME` VARCHAR(50) NOT NULL COMMENT '권한명'
);

DROP TABLE IF EXISTS `LOGIN_HISTORY`;

CREATE TABLE `LOGIN_HISTORY`
(
    `ID`        BIGINT  NOT NULL COMMENT 'ID',
    `MEMBER_ID` BIGINT  NOT NULL COMMENT '유저ID',
    `LOCK_YN`   CHAR(1) NOT NULL DEFAULT 'N' COMMENT '잠금여부',
    `TRY_COUNT` INT NULL DEFAULT 0 COMMENT '시도횟수',
    `TRY_DATE`  DATETIME NULL COMMENT '시도날짜'
);

DROP TABLE IF EXISTS `COIN`;

CREATE TABLE `COIN`
(
    `ID`        BIGINT NOT NULL COMMENT 'ID',
    `MEMBER_ID` BIGINT NOT NULL COMMENT '유저ID',
    `COUNT`     BIGINT NOT NULL DEFAULT 50 COMMENT '코인수'
);

DROP TABLE IF EXISTS `TITLE`;

CREATE TABLE `TITLE`
(
    `ID`        BIGINT       NOT NULL COMMENT 'ID',
    `MEMBER_ID` BIGINT       NOT NULL COMMENT '작가ID',
    `TITLE`     VARCHAR(255) NOT NULL COMMENT '제목',
    `CONTENT`   TEXT NULL COMMENT '간략소개'
);

DROP TABLE IF EXISTS `IMAGE`;

CREATE TABLE `IMAGE`
(
    `ID`       BIGINT NOT NULL COMMENT 'ID',
    `NOVEL_ID` BIGINT NOT NULL COMMENT '소설ID',
    `Field`    VARCHAR(255) NULL
);

DROP TABLE IF EXISTS `CONTENT`;

CREATE TABLE `CONTENT`
(
    `ID`          BIGINT   NOT NULL COMMENT 'ID',
    `TITLE_ID`    BIGINT   NOT NULL COMMENT '타이틀ID',
    `CONTENT`     TEXT     NOT NULL COMMENT '본문',
    `REGIST_DATE` DATETIME NOT NULL DEFAULT NOW() COMMENT '개시일',
    `PRICE`       BIGINT   NOT NULL DEFAULT 3 COMMENT '가격'
);

DROP TABLE IF EXISTS `COIN_HISTORY`;

CREATE TABLE `COIN_HISTORY`
(
    `ID`       BIGINT   NOT NULL COMMENT 'ID',
    `PRICE`    BIGINT   NOT NULL COMMENT '비용',
    `PAY_DATE` DATETIME NOT NULL DEFAULT NOW() COMMENT '구매일'
);

ALTER TABLE `MEMBER`
    ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (
                                            `ID`
        );

ALTER TABLE `AUTHORITY`
    ADD CONSTRAINT `PK_AUTHORITY` PRIMARY KEY (
                                               `ID`
        );

ALTER TABLE `LOGIN_HISTORY`
    ADD CONSTRAINT `PK_LOGIN_HISTORY` PRIMARY KEY (
                                                   `ID`
        );

ALTER TABLE `COIN`
    ADD CONSTRAINT `PK_COIN` PRIMARY KEY (
                                          `ID`
        );

ALTER TABLE `TITLE`
    ADD CONSTRAINT `PK_TITLE` PRIMARY KEY (
                                           `ID`
        );

ALTER TABLE `IMAGE`
    ADD CONSTRAINT `PK_IMAGE` PRIMARY KEY (
                                           `ID`
        );

ALTER TABLE `CONTENT`
    ADD CONSTRAINT `PK_CONTENT` PRIMARY KEY (
                                             `ID`
        );

ALTER TABLE `COIN_HISTORY`
    ADD CONSTRAINT `PK_COIN_HISTORY` PRIMARY KEY (
                                                  `ID`
        );

-- MEMBER_AUTHORITY
DROP TABLE IF EXISTS `MEMBER_AUTHORITY`;

CREATE TABLE `MEMBER_AUTHORITY`
(
    `MEMBER_ID`      BIGINT      NOT NULL,
    `AUTHORITY_NAME` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`MEMBER_ID`, `AUTHORITY_NAME`)
);

-- MEMBER_COIN_HISTORY
DROP TABLE IF EXISTS `MEMBER_COIN_HISTORY`;

CREATE TABLE `MEMBER_COIN_HISTORY`
(
    `MEMBER_ID`       BIGINT NOT NULL,
    `COIN_HISTORY_ID` BIGINT NOT NULL,
    PRIMARY KEY (`MEMBER_ID`, `COIN_HISTORY_ID`)
);

-- CONTENT_COIN_HISTORY
DROP TABLE IF EXISTS `CONTENT_COIN_HISTORY`;

CREATE TABLE `CONTENT_COIN_HISTORY`
(
    `CONTENT_ID`      BIGINT NOT NULL,
    `COIN_HISTORY_ID` BIGINT NOT NULL,
    PRIMARY KEY (`CONTENT_ID`, `COIN_HISTORY_ID`)
);


-- NOVEL_TITLE_CONTENT
DROP TABLE IF EXISTS `TITLE_CONTENT`;

CREATE TABLE `TITLE_CONTENT`
(
    `TITLE_ID`   BIGINT NOT NULL,
    `CONTENT_ID` BIGINT NOT NULL,
    PRIMARY KEY (`TITLE_ID`, `CONTENT_ID`)
);;