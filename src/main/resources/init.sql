USE CSE305;

DROP TABLE IF EXISTS `Rental`;
DROP TABLE IF EXISTS `MovieQ`;
DROP TABLE IF EXISTS `AppearedIn`;
DROP TABLE IF EXISTS `Account`;
DROP TABLE IF EXISTS `Customer`;
DROP TABLE IF EXISTS `Employee`;
DROP TABLE IF EXISTS `Login`;
DROP TABLE IF EXISTS `Person`;
DROP TABLE IF EXISTS `Movie`;
DROP TABLE IF EXISTS `Actor`;
DROP TABLE IF EXISTS `Order`;
DROP TABLE IF EXISTS `Location`;

-- CREATE DOMAIN AccountType AS CHAR(20)
--     CHECK ( value IN ('Limited', 'Unlimited-1', 'Unlimited-2', 'Unlimited-3') );
-- CREATE DOMAIN EmpId AS INTEGER
--     CHECK (value > 0 AND value < 1000000000);
-- CREATE DOMAIN CustomerId AS INTEGER
--     CHECK (value > 0 AND value < 1000000000);

CREATE TABLE `Location` (
    ZipCode INTEGER,
    City CHAR(20) NOT NULL,
    State CHAR(20) NOT NULL, 
    PRIMARY KEY (ZipCode) 
);

CREATE TABLE Person (
    SSN INTEGER,
    LastName CHAR(20) NOT NULL,
    FirstName CHAR(20) NOT NULL,
    `Address` CHAR(20),
    ZipCode INTEGER,
    Telephone VARCHAR(20),
    PRIMARY KEY (SSN),
    FOREIGN KEY (ZipCode) REFERENCES `Location` (ZipCode)
    ON DELETE NO ACTION 
    ON UPDATE CASCADE 
);

CREATE TABLE Employee (
    ID                  INTEGER AUTO_INCREMENT /* CHECK (ID > 0 AND ID < 1000000000) */,
    SSN                 INTEGER,
    Email               CHAR(32),
    StartDate           DATE,
    HourlyRate          INTEGER,
    PRIMARY KEY (ID),
    FOREIGN KEY (SSN)   REFERENCES Person (SSN)
    ON DELETE NO ACTION 
    ON UPDATE CASCADE 
);


CREATE TABLE Customer (
    ID                  INTEGER /* CHECK (ID > 0 AND ID < 1000000000) */,
    Email               CHAR(32),
    Rating              INTEGER,
    CreditCardNumber    VARCHAR(19),
    PRIMARY KEY (ID),
    FOREIGN KEY (ID) REFERENCES Person (SSN)
    ON DELETE NO ACTION
    ON UPDATE CASCADE 
);


CREATE TABLE Account (
    ID INTEGER AUTO_INCREMENT,
    DateOpened DATE,
    `Type` CHAR(20) CHECK ( `Type` IN ('Limited', 'Unlimited-1', 'Unlimited-2', 'Unlimited-3') ),
    CustomerId INTEGER,
    PRIMARY KEY (ID),
    FOREIGN KEY (CustomerId) REFERENCES Customer (ID)
    ON DELETE NO ACTION 
    ON UPDATE CASCADE 
);

CREATE TABLE `Order` (
    ID INTEGER AUTO_INCREMENT,
    `DateTime` DATETIME, 
    ReturnDate DATE, 
    PRIMARY KEY (ID) 
);

CREATE TABLE Movie (
    ID INTEGER AUTO_INCREMENT,
    `Name` CHAR(20) NOT NULL, 
    `Type` CHAR(20) NOT NULL, 
    Rating INTEGER,
    DistrFee DECIMAL(19,4), 
    NumCopies INTEGER,
    PRIMARY KEY (ID)
);

CREATE TABLE Actor (
    ID INTEGER,
    `Name` CHAR(20) NOT NULL, 
    Age INTEGER NOT NULL, 
    Gender CHAR(1) NOT NULL, 
    Rating INTEGER,
    PRIMARY KEY (ID) 
);

CREATE TABLE Rental (
    AccountId INTEGER,
    CustRepId INTEGER,
    OrderId INTEGER,
    MovieId INTEGER,
    PRIMARY KEY (AccountId, CustRepId, OrderId, MovieId), 
    FOREIGN KEY (AccountId) REFERENCES Account (ID)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    FOREIGN KEY (CustRepId) REFERENCES Employee (ID)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    FOREIGN KEY (OrderId) REFERENCES `Order` (ID)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    FOREIGN KEY (MovieId) REFERENCES Movie (ID)
    ON DELETE NO ACTION
    ON UPDATE CASCADE 
);

CREATE TABLE MovieQ ( 
    AccountId INTEGER, /* CHECK (AccountId > 0 AND AccountId < 1000000000), */
    MovieId INTEGER,
    PRIMARY KEY (AccountId, MovieId),
    FOREIGN KEY (AccountId) REFERENCES Account (ID)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    FOREIGN KEY (MovieId) REFERENCES Movie (ID) 
    ON DELETE NO ACTION
    ON UPDATE CASCADE 
);

CREATE TABLE AppearedIn (
    ActorId INTEGER,
    MovieId INTEGER,
    PRIMARY KEY (ActorId, MovieId),
    FOREIGN KEY (ActorId) REFERENCES Actor (ID)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    FOREIGN KEY (MovieId) REFERENCES Movie (ID) 
    ON DELETE NO ACTION
    ON UPDATE CASCADE 
);

CREATE TABLE Login (
    Username    CHAR(32) NOT NULL,  -- Assuming username is the email
    Password    VARCHAR(255) NOT NULL DEFAULT 'password', -- Storing password as a hash for security
    Role        CHAR(25) CHECK (Role IN ('Customer', 'Manager', 'CustomerRepresentative')),
    PersonID    INTEGER NOT NULL,
    PRIMARY KEY (Username),
    FOREIGN KEY (PersonID) REFERENCES Person (SSN)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);