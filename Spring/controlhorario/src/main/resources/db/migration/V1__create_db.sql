DROP TABLE IF EXISTS WORKDAY;

CREATE SEQUENCE workday_id_seq;  

CREATE TABLE WORKDAY(
    id integer NOT NULL DEFAULT nextval('workday_id_seq'),
    employee_number VARCHAR(100),
    entry_time TIMESTAMPTZ,
    exit_time TIMESTAMPTZ
);

ALTER SEQUENCE workday_id_seq
OWNED BY WORKDAY.id;