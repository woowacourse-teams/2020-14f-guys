create table if not exists MEMBER
(
    ID         bigint       not null primary key auto_increment,
    KAKAO_ID   bigint       not null unique,
    NAME       varchar(64)  not null unique,
    EMAIL      varchar(128) not null unique,
    CASH       numeric      not null,
    ROLE       varchar(16)  not null,
    CREATED_AT datetime     not null,
    UPDATED_AT datetime     not null
);

create table if not exists RACE
(
    ID                                   bigint       not null primary key auto_increment,
    TITLE                                varchar(128) not null,
    DESCRIPTION                          varchar(255) not null,
    THUMBNAIL_BASE_IMAGE_URL             text         not null,
    CERTIFICATION_EXAMPLE_BASE_IMAGE_URL text         not null,
    START_DATE                           datetime     not null,
    END_DATE                             datetime     not null,
    CATEGORY                             varchar(32)  not null,
    CASH                                 numeric      not null,
    CREATED_AT                           datetime     not null,
    UPDATED_AT                           datetime     not null
);

create table if not exists RIDER
(
    ID         bigint   not null primary key auto_increment,
    MEMBER_ID  bigint   not null,
    RACE_ID    bigint   not null,
    CREATED_AT datetime not null,
    UPDATED_AT datetime not null
);

create table if not exists MISSION
(
    ID                  bigint       not null primary key auto_increment,
    START_TIME          datetime     not null,
    END_TIME            datetime     not null,
    MISSION_INSTRUCTION varchar(255) not null,
    RACE_ID             bigint       not null,
    CREATED_AT          datetime     not null,
    UPDATED_AT          datetime     not null
);

create table if not exists CERTIFICATION
(
    ID             bigint       not null primary key auto_increment,
    STATUS         varchar(16)  not null,
    DESCRIPTION    varchar(255),
    BASE_IMAGE_URL varchar(255) not null,
    RIDER_ID       bigint       not null,
    MISSION_ID     bigint       not null,
    CREATED_AT     datetime     not null,
    UPDATED_AT     datetime     not null
);

create table if not exists REPORT
(
    ID               bigint       not null primary key auto_increment,
    REPORT_TYPE      varchar(32)  not null,
    DESCRIPTION      varchar(255) not null,
    CERTIFICATION_ID bigint       not null,
    MEMBER_ID        bigint       not null,
    CREATED_AT       datetime     not null,
    UPDATED_AT       datetime     not null
);
