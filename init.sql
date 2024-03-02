CREATE UNLOGGED TABLE customers (
	id SERIAL PRIMARY KEY,
	"name" VARCHAR(50) NOT NULL,
	"limit" INTEGER NOT NULL,
	balance INTEGER NOT NULL DEFAULT 0 CHECK ("limit" - limit_in_use >= balance),
	limit_in_use INTEGER NOT NULL DEFAULT 0 CHECK("limit" >= limit_in_use)
);

CREATE UNLOGGED TABLE transactions (
	id SERIAL PRIMARY KEY,
	customer_id INTEGER NOT NULL,
	amount INTEGER NOT NULL,
	type CHAR(1) NOT NULL,
	description VARCHAR(10) NOT NULL,
	transacted_in TIMESTAMP NOT NULL DEFAULT NOW(),
	CONSTRAINT fk_customers_transactions_id
		FOREIGN KEY (customer_id) REFERENCES customers(id)
);

DO $$
BEGIN
	INSERT INTO customers (name, "limit")
	VALUES
		('o barato sai caro', 1000 * 100),
		('zan corp ltda', 800 * 100),
		('les cruders', 10000 * 100),
		('padaria joia de cocaia', 100000 * 100),
		('kid mais', 5000 * 100);
END;
$$;

CREATE OR REPLACE FUNCTION debit(
	customer_id_param INT,
	amount_param INT,
	description_param VARCHAR(10),
	out new_balance INT,
	out available_limit INT)
LANGUAGE plpgsql
AS $$
BEGIN
	PERFORM pg_advisory_xact_lock(customer_id_param);
	new_balance := null;
	available_limit := null;
	UPDATE customers
	   SET limit_in_use = limit_in_use + amount_param,
	       balance  = balance - amount_param
	 WHERE id = customer_id_param
	 returning balance, "limit" - limit_in_use into new_balance, available_limit;

	INSERT INTO transactions
	VALUES(DEFAULT, customer_id_param, amount_param, 'd', description_param, NOW());
END;
$$;


CREATE OR REPLACE FUNCTION credit(
	customer_id_param INT,
	amount_param INT,
	description_param VARCHAR(10),
	out new_balance INT,
	out available_limit INT)
LANGUAGE plpgsql
AS $$
BEGIN
	PERFORM pg_advisory_xact_lock(customer_id_param);
	new_balance := null;
	available_limit := null;
	UPDATE customers
	   SET limit_in_use = limit_in_use + amount_param
	 WHERE id = customer_id_param
	 returning balance, "limit" - limit_in_use into new_balance, available_limit;

	INSERT INTO transactions
	VALUES(DEFAULT, customer_id_param, amount_param, 'c', description_param, NOW());
END;
$$;