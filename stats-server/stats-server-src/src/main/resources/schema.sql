CREATE SEQUENCE IF NOT EXISTS endpoint_hit_seq;
CREATE TABLE IF NOT EXISTS endpoint_hits
(
    endpoint_hit_id        BIGINT    DEFAULT NEXTVAL('endpoint_hit_seq') NOT NULL,
    app                    VARCHAR(255)                                  NOT NULL,
    uri                    VARCHAR(512)                                  NOT NULL,
    ip                     VARCHAR(255)                                  NOT NULL,
    endpoint_hit_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP           NOT NULL,
    CONSTRAINT endpoint_hit_pk PRIMARY KEY (endpoint_hit_id)
);
