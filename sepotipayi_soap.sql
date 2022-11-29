CREATE DATABASE sepotipayi_soap;

CREATE TABLE logging (
  id int NOT NULL AUTO_INCREMENT,
  description char(255) NOT NULL,
  ip char(16) NOT NULL,
  endpoint char(255) NOT NULL,
  requested_at timestamp NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE subscription (
  creator_id int NOT NULL,
  subscriber_id int NOT NULL,
  creator_name char(255) NOT NULL,
  subscriber_name char(255) NOT NULL,
  status char(255) NOT NULL DEFAULT 'PENDING',
  CONSTRAINT pk_id PRIMARY KEY (creator_id, subscriber_id)
);
