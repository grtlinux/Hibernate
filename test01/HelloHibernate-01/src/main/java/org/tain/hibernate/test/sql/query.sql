CREATE DATABASE IF NOT EXISTS tain;

USE tain;

DROP TABLE IF EXISTS user_table;

CREATE TABLE user_table (
  user_id      int(20)                            NOT NULL,
  user_name    varchar(255)    CHARACTER SET utf8 NOT NULL,
  created_by   VARCHAR (255)   CHARACTER SET utf8 NOT NULL,
  created_date DATE                               NOT NULL,
  PRIMARY KEY (user_id)
);


