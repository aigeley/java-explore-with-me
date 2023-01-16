CREATE SEQUENCE IF NOT EXISTS category_seq;
CREATE TABLE IF NOT EXISTS categories
(
    category_id BIGINT DEFAULT NEXTVAL('category_seq') NOT NULL,
    name        VARCHAR(255)                           NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (category_id),
    CONSTRAINT category_name_uq UNIQUE (name)
);
CREATE SEQUENCE IF NOT EXISTS user_seq;
CREATE TABLE IF NOT EXISTS users
(
    user_id BIGINT DEFAULT NEXTVAL('user_seq') NOT NULL,
    email   VARCHAR(512)                       NOT NULL,
    name    VARCHAR(255)                       NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (user_id),
    CONSTRAINT user_email_uq UNIQUE (email)
);
CREATE SEQUENCE IF NOT EXISTS event_seq;
CREATE TABLE IF NOT EXISTS events
(
    event_id           BIGINT       DEFAULT NEXTVAL('event_seq') NOT NULL,
    annotation         VARCHAR(2000)                             NOT NULL,
    category_id        BIGINT                                    NOT NULL,
    created_on         TIMESTAMP    DEFAULT CURRENT_TIMESTAMP    NOT NULL,
    description        VARCHAR(7000)                             NOT NULL,
    event_date         TIMESTAMP                                 NOT NULL,
    user_id            BIGINT                                    NOT NULL,
    location_lat       REAL,
    location_lon       REAL,
    paid               BOOLEAN      DEFAULT false                NOT NULL,
    participant_limit  INTEGER      DEFAULT 0                    NOT NULL,
    published_on       TIMESTAMP,
    request_moderation BOOLEAN      DEFAULT true                 NOT NULL,
    state              VARCHAR(255) DEFAULT 'PENDING'            NOT NULL,
    title              VARCHAR(120)                              NOT NULL,
    CONSTRAINT event_pk PRIMARY KEY (event_id),
    CONSTRAINT event_category_fk FOREIGN KEY (category_id) REFERENCES categories (category_id) ON DELETE CASCADE,
    CONSTRAINT event_user_fk FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);
CREATE SEQUENCE IF NOT EXISTS participation_request_seq;
CREATE TABLE IF NOT EXISTS participation_requests
(
    participation_request_id BIGINT       DEFAULT NEXTVAL('participation_request_seq') NOT NULL,
    created                  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP                    NOT NULL,
    event_id                 BIGINT                                                    NOT NULL,
    user_id                  BIGINT                                                    NOT NULL,
    status                   VARCHAR(255) DEFAULT 'PENDING'                            NOT NULL,
    CONSTRAINT participation_request_pk PRIMARY KEY (participation_request_id),
    CONSTRAINT participation_request_uq UNIQUE (event_id, user_id),
    CONSTRAINT participation_request_user_fk FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT participation_request_event_fk FOREIGN KEY (event_id) REFERENCES events (event_id) ON DELETE CASCADE
);
CREATE SEQUENCE IF NOT EXISTS compilation_seq;
CREATE TABLE IF NOT EXISTS compilations
(
    compilation_id BIGINT  DEFAULT NEXTVAL('compilation_seq') NOT NULL,
    pinned         BOOLEAN DEFAULT false                      NOT NULL,
    title          VARCHAR(255)                               NOT NULL,
    CONSTRAINT compilation_pk PRIMARY KEY (compilation_id)
);
CREATE TABLE IF NOT EXISTS events_compilations
(
    compilation_id BIGINT NOT NULL,
    event_id       BIGINT NOT NULL,
    CONSTRAINT events_compilations_pk PRIMARY KEY (compilation_id, event_id),
    CONSTRAINT events_compilations_compilation_fk FOREIGN KEY (compilation_id) REFERENCES compilations (compilation_id)
        ON DELETE CASCADE,
    CONSTRAINT events_compilations_event_fk FOREIGN KEY (event_id) REFERENCES events (event_id) ON DELETE CASCADE
);