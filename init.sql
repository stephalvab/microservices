

CREATE DATABASE IF NOT EXISTS appbankbd;
USE appbankbd;
CREATE TABLE  IF NOT EXISTS PERSON (
                                       person_id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                       name VARCHAR(150) NOT NULL,
    gender CHAR(1) NOT NULL,
    identification VARCHAR(20) UNIQUE NOT NULL,
    address VARCHAR(250) NOT NULL,
    phone VARCHAR(20)
    );

CREATE TABLE  IF NOT EXISTS CLIENT (
                                       client_id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                       person_id INTEGER UNIQUE NOT NULL,
                                       password VARCHAR(250) NOT NULL,
    status BOOLEAN NOT NULL,
    FOREIGN KEY (person_id) REFERENCES PERSON(person_id)
    );


CREATE TABLE  IF NOT EXISTS ACCOUNT_TYPE (
                                             account_type_id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                             name VARCHAR(20) NOT NULL
    );

CREATE TABLE  IF NOT EXISTS ACCOUNT (
                                        account_id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                        client_id INTEGER NOT NULL,
                                        account_number VARCHAR(250) UNIQUE NOT NULL,
    account_type_id INTEGER NOT NULL,
    amount DECIMAL(8,2) NOT NULL,
    status BOOLEAN NOT NULL,
    FOREIGN KEY (client_id) REFERENCES CLIENT(client_id),
    FOREIGN KEY (account_type_id) REFERENCES ACCOUNT_TYPE(account_type_id)
    );


CREATE TABLE  IF NOT EXISTS TRANSACTION (
                                            transaction_id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                            account_id INTEGER NOT NULL,
                                            date_register DATETIME NOT NULL,
                                            transaction_type CHAR(1) NOT NULL,
    amount DECIMAL(8,2) NOT NULL,
    status BOOLEAN NOT NULL,
    FOREIGN KEY (account_id) REFERENCES ACCOUNT(account_id)
    );
