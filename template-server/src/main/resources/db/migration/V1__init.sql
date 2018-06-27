CREATE TABLE IF NOT EXISTS product (
  `id`               BIGINT       NOT NULL,
  created_by         VARCHAR(255),
  created_date       DATETIME,
  last_modified_by   VARCHAR(255),
  last_modified_date DATETIME,
  category           INTEGER,
  `desc`             LONGTEXT,
  name               VARCHAR(255) NOT NULL,
  unit_price         FLOAT        NOT NULL,
  `version`          BIGINT       NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY `UNIQUE_PRODUCT_NAME` (`name`)
);

CREATE TABLE IF NOT EXISTS product_tags (
product_id BIGINT       NOT NULL,
tags_caption            VARCHAR(255),
tags_level              INTEGER
);

CREATE SEQUENCE product_seq START WITH 1 INCREMENT BY 1;
