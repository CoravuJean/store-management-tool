CREATE TABLE product
(
	id INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1) NOT NULL,
	name VARCHAR(50) NOT NULL UNIQUE,
	measurement_unit_name VARCHAR(50) NOT NULL,
	quantity INT NOT NULL,
	price REAL NOT NULL,
	CONSTRAINT pk_product PRIMARY KEY (id)
);
