CREATE TABLE orders (id SERIAL PRIMARY KEY, name VARCHAR(50), created_by varchar(50), last_modified_by varchar(50), last_modified_date timestamp, created_date timestamp, price INT, product_id INT, customer_id INT);

ALTER TABLE orders REPLICA IDENTITY FULL;

CREATE TABLE outbox (id SERIAL PRIMARY KEY, aggregatetype VARCHAR(255), aggregateid varchar(255), type varchar(255), payload varchar(255));
