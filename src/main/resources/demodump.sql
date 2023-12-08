USE CSE305;

-- Inserting into Location
INSERT INTO `Location` (ZipCode, City, `State`) VALUES
(11790, 'Stony Brook', 'NY'),
(93536, 'Los Angeles', 'CA'),
(11794, 'Stony Brook', 'NY');

-- Inserting into Person
INSERT INTO Person (SSN, LastName, FirstName, `Address`, ZipCode, Telephone) VALUES
(111111111, 'Yang', 'Shang', '123 Success Street', 11790, '516-632-8959'),
(222222222, 'Du', 'Victor', '456 Fortune Road', 11790, '516-632-4360'),
(333333333, 'Smith', 'John', '789 Peace Blvd.', 93536, '315-443-4321'),
(444444444, 'Philip', 'Lewis', '135 Knowledge Lane', 11794, '516-666-8888'),
(123456789, 'Smith', 'David', '123 College road', 11790, '516-215-2345'),
(789123456, 'Warren', 'David', '456 Sunken Street', 11794, '631-632-9987');

-- Inserting into Employee
INSERT INTO Employee (ID, SSN, Email, StartDate, HourlyRate) VALUES
(1, 123456789, "david.smith@blockbuster.com",'2005-11-01', 60),
(2, 789123456, "david.warren@blockbuster.com", '2006-02-02', 50);

-- Inserting into Login
INSERT INTO `Login` (Username, Password, Role, PersonID) VALUES
("david.smith@blockbuster.com", "password", 'CustomerRepresentative', 123456789),
("david.warren@blockbuster.com", "password", 'Manager', 789123456),
("victor.du@x.com", "password", 'Customer', 222222222);

-- Inserting into Customer
INSERT INTO Customer (ID, Email, Rating, CreditCardNumber) VALUES
(111111111, 'syang@cs.sunysb.edu', 1, '1234567812345678'),
(222222222, 'vicdu@cs.sunysb.edu', 1, '5678123456781234'),
(333333333, 'jsmith@ic.sunysb.edu', 1, '2345678923456789'),
(444444444, 'pml@cs.sunysb.edu', 1, '6789234567892345');

-- Inserting into Account
INSERT INTO Account (ID, DateOpened, `Type`, CustomerId) VALUES
(1, '2006-10-01', 'Unlimited-2', 444444444),
(2, '2006-10-15', 'Limited', 222222222),
(3, '2006-10-15', 'Unlimited-3', 333333333);

-- Inserting into Movie
INSERT INTO Movie (ID, `Name`, `Type`, Rating, DistrFee, NumCopies) VALUES
(1, 'The Godfather', 'Drama', 5, 10000, 3),
(2, 'Shawshank Redemption', 'Drama', 4, 1000, 2),
(3, 'Borat', 'Comedy', 3, 500, 1),
(4, 'Anchorman', 'Comedy', 4, 1500, 2),
(5, 'Superbad', 'Comedy', 3, 800, 1),
(6, 'Bridesmaids', 'Comedy', 4, 1200, 2),
(7, 'Dumb and Dumber', 'Comedy', 3, 1000, 2),
(8, 'The Hangover', 'Comedy', 4, 1200, 2);

-- Inserting into Actor
INSERT INTO Actor (ID, `Name`, Age, Gender, Rating) VALUES
(1, 'Al Pacino', 63, 'M', 5),
(2, 'Tim Robbins', 53, 'M', 2);

-- Inserting into `Order`
INSERT INTO `Order` (ID, `DateTime`, ReturnDate) VALUES
(1, '2009-11-11 10:00', '2009-11-14'),
(2, '2009-11-11 18:15', NULL),
(3, '2009-11-12 09:30', NULL),
(4, '2009-11-21 22:22', NULL),
(5, '2009-11-11 18:15', NULL),
(6, '2009-11-12 09:30', NULL),
(7, '2009-11-21 22:22', NULL);
-- Inserting into AppearedIn
INSERT INTO AppearedIn (ActorId, MovieId) VALUES
(1, 1),
(1, 3),
(2, 1);

-- Inserting into Rental
INSERT INTO Rental (AccountId, CustRepId, OrderId, MovieId) VALUES
(1, 1, 1, 1),
(2, 1, 2, 3),
(1, 1, 3, 3),
(2, 1, 4, 2),
(3, 1, 5, 4),
(3, 1, 6, 5),
(3, 1, 3, 6);

-- Inserting into MovieQ
INSERT INTO MovieQ (AccountId, MovieId) VALUES
(1, 1),
(1, 3),
(2, 2),
(2, 3);
