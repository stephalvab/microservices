USE appbankbd;

CREATE TABLE  IF NOT EXISTS `account_type` (
                                               `account_type_id` int NOT NULL AUTO_INCREMENT,
                                               `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`account_type_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE  IF NOT EXISTS `person` (
                                         `client_id` int NOT NULL AUTO_INCREMENT,
                                         `address` varchar(255) DEFAULT NULL,
    `age` int NOT NULL,
    `gender` varchar(255) DEFAULT NULL,
    `identification` varchar(255) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    `phone` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`client_id`),
    UNIQUE KEY `UK_4r2cs4eybw7joyi0u8v7vywhg` (`identification`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE  IF NOT EXISTS `client` (
    `password` varchar(255) DEFAULT NULL,
    `status` bit(1) NOT NULL,
    `client_id` int NOT NULL,
    PRIMARY KEY (`client_id`),
    CONSTRAINT `FKcxli0sgm0c24a09lfqwoi9wmt` FOREIGN KEY (`client_id`) REFERENCES `person` (`client_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE  IF NOT EXISTS `account` (
                                          `account_id` int NOT NULL AUTO_INCREMENT,
                                          `account_number` varchar(255) DEFAULT NULL,
    `balance` double NOT NULL,
    `status` bit(1) NOT NULL,
    `account_type_id` int DEFAULT NULL,
    `client_id` int DEFAULT NULL,
    PRIMARY KEY (`account_id`),
    KEY `FKgw84mgpacw9htdxcs2j1p7u6j` (`account_type_id`),
    KEY `FKkm8yb63h4ownvnlrbwnadntyn` (`client_id`),
    CONSTRAINT `FKgw84mgpacw9htdxcs2j1p7u6j` FOREIGN KEY (`account_type_id`) REFERENCES `account_type` (`account_type_id`),
    CONSTRAINT `FKkm8yb63h4ownvnlrbwnadntyn` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE  IF NOT EXISTS `transaction` (
                                              `transaction_id` int NOT NULL AUTO_INCREMENT,
                                              `amount` double NOT NULL,
                                              `available_balance` double NOT NULL,
                                              `date_register` datetime(6) DEFAULT NULL,
    `status` bit(1) NOT NULL,
    `account_id` int DEFAULT NULL,
    PRIMARY KEY (`transaction_id`),
    KEY `FK6g20fcr3bhr6bihgy24rq1r1b` (`account_id`),
    CONSTRAINT `FK6g20fcr3bhr6bihgy24rq1r1b` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO appbankbd.account_type (name) VALUES ('Ahorros'), ('Corriente');
