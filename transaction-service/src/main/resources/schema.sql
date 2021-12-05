CREATE TABLE transaction
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    amount NUMERIC NOT NULL,
    currency VARCHAR(3) NOT NULL,
    description varchar(100)
);