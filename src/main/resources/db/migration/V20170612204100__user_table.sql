CREATE SEQUENCE user_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE "user" (
  id BIGINT PRIMARY KEY DEFAULT (NEXTVAL('user_id_seq')),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
  first_name VARCHAR(200) NOT NULL,
  last_name VARCHAR(200) NOT NULL,
  email VARCHAR(200) NOT NULL
);