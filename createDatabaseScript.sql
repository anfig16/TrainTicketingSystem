-- Recreate database
DROP DATABASE IF EXISTS ticketing_system;
CREATE DATABASE ticketing_system;
USE ticketing_system;

-- DROP in correct dependency order
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS route;
DROP TABLE IF EXISTS train;
DROP TABLE IF EXISTS passenger;
DROP TABLE IF EXISTS station;

-- TABLES (aligned with your Java models)

-- STATION
CREATE TABLE station (
    stationName VARCHAR(100) PRIMARY KEY
);

-- TRAIN
CREATE TABLE train (
    trainID INT AUTO_INCREMENT PRIMARY KEY
);

-- ROUTE
CREATE TABLE route (
    routeName VARCHAR(100) PRIMARY KEY,
    startDest VARCHAR(100) NOT NULL,
    endDest VARCHAR(100) NOT NULL,
    date VARCHAR(50) NOT NULL,
    trainID INT,
    FOREIGN KEY (trainID) REFERENCES train(trainID) ON DELETE CASCADE
);

-- PASSENGER
CREATE TABLE passenger (
    pId INT AUTO_INCREMENT PRIMARY KEY,
    pName VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    passcode VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL
);

-- SEATS
CREATE TABLE seats (
    seatNo INT AUTO_INCREMENT PRIMARY KEY,
    seatAvailable BOOLEAN NOT NULL DEFAULT TRUE,
    trainID INT,
    FOREIGN KEY (trainID) REFERENCES train(trainID) ON DELETE CASCADE
);

-- TICKET
CREATE TABLE ticket (
    ticketNum INT AUTO_INCREMENT PRIMARY KEY,
    date VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    pId INT,
    seatNo INT,
    routeName VARCHAR(100),
    FOREIGN KEY (pId) REFERENCES passenger(pId) ON DELETE CASCADE,
    FOREIGN KEY (seatNo) REFERENCES seats(seatNo) ON DELETE CASCADE,
    FOREIGN KEY (routeName) REFERENCES route(routeName) ON DELETE CASCADE
);

-- POPULATE DATA

-- Stations (10)
INSERT INTO station (stationName) VALUES
('New York Penn'),
('Washington Union'),
('Dallas Central'),
('Houston Downtown'),
('Chicago Union'),
('Detroit Central'),
('Los Angeles Main'),
('San Francisco Central'),
('Miami Central'),
('Orlando Main');

-- Trains (10)
INSERT INTO train () VALUES (),(),(),(),(),(),(),(),(),();

-- Routes (10)
INSERT INTO route (routeName, startDest, endDest, date, trainID) VALUES
('RouteNY-DC', 'New York Penn', 'Washington Union', '2025-10-05', 1),
('RouteDallas-Houston', 'Dallas Central', 'Houston Downtown', '2025-10-06', 2),
('RouteLA-SF', 'Los Angeles Main', 'San Francisco Central', '2025-10-07', 3),
('RouteChicago-Detroit', 'Chicago Union', 'Detroit Central', '2025-10-08', 4),
('RouteMiami-Orlando', 'Miami Central', 'Orlando Main', '2025-10-09', 5),
('RouteNY-Chicago', 'New York Penn', 'Chicago Union', '2025-10-10', 6),
('RouteDallas-Detroit', 'Dallas Central', 'Detroit Central', '2025-10-11', 7),
('RouteLA-Miami', 'Los Angeles Main', 'Miami Central', '2025-10-12', 8),
('RouteSF-Orlando', 'San Francisco Central', 'Orlando Main', '2025-10-13', 9),
('RouteChicago-Houston', 'Chicago Union', 'Houston Downtown', '2025-10-14', 10);

-- Passengers (10)
INSERT INTO passenger (pName, email, passcode, phone) VALUES
('Alice Johnson', 'alice@example.com', 'password', '111-111-1111'),
('Bob Smith', 'bob@example.com', 'password', '222-222-2222'),
('Charlie Brown', 'charlie@example.com', 'password', '333-333-3333'),
('Diana Prince', 'diana@example.com', 'password', '444-444-4444'),
('Ethan Hunt', 'ethan@example.com', 'password', '555-555-5555'),
('Fiona Davis', 'fiona@example.com', 'password', '666-666-6666'),
('George King', 'george@example.com', 'password', '777-777-7777'),
('Hannah Lee', 'hannah@example.com', 'password', '888-888-8888'),
('Ivan Wright', 'ivan@example.com', 'password', '999-999-9999'),
('Jenna Cole', 'jenna@example.com', 'password', '000-000-0000');

-- Seats (10)
INSERT INTO seats (trainID, seatAvailable) VALUES
(1, FALSE),(1, FALSE),(1, TRUE),(1, TRUE),(1, TRUE),
(1, TRUE),(1, TRUE),(1, TRUE),(1, TRUE),(1, TRUE),
(2, FALSE),(2, FALSE),(2, TRUE),(2, TRUE),(2, TRUE),
(2, TRUE),(2, TRUE),(2, TRUE),(2, TRUE),(2, TRUE),
(3, FALSE),(3, FALSE),(3, TRUE),(3, TRUE),(3, TRUE),
(3, TRUE),(3, TRUE),(3, TRUE),(3, TRUE),(3, TRUE),
(4, FALSE),(4, FALSE),(4, TRUE),(4, TRUE),(4, TRUE),
(4, TRUE),(4, TRUE),(4, TRUE),(4, TRUE),(4, TRUE),
(5, FALSE),(5, FALSE),(5, TRUE),(5, TRUE),(5, TRUE),
(5, TRUE),(5, TRUE),(5, TRUE),(5, TRUE),(5, TRUE),
(6, TRUE),(6, TRUE),(6, TRUE),(6, TRUE),(6, TRUE),
(6, TRUE),(6, TRUE),(6, TRUE),(6, TRUE),(6, TRUE),
(7, TRUE),(7, TRUE),(7, TRUE),(7, TRUE),(7, TRUE),
(7, TRUE),(7, TRUE),(7, TRUE),(7, TRUE),(7, TRUE),
(8, TRUE),(8, TRUE),(8, TRUE),(8, TRUE),(8, TRUE),
(8, TRUE),(8, TRUE),(8, TRUE),(8, TRUE),(8, TRUE),
(9, TRUE),(9, TRUE),(9, TRUE),(9, TRUE),(9, TRUE),
(9, TRUE),(9, TRUE),(9, TRUE),(9, TRUE),(9, TRUE),
(10, TRUE),(10, TRUE),(10, TRUE),(10, TRUE),(10, TRUE),
(10, TRUE),(10, TRUE),(10, TRUE),(10, TRUE),(10, TRUE);

-- Tickets (10)
INSERT INTO ticket (date, price, pId, seatNo, routeName) VALUES
('2025-10-05', 50.00, 1, 1, 'RouteNY-DC'),
('2025-10-06', 40.00, 2, 11, 'RouteDallas-Houston'),
('2025-10-07', 60.00, 3, 21, 'RouteLA-SF'),
('2025-10-08', 45.00, 4, 31, 'RouteChicago-Detroit'),
('2025-10-09', 55.00, 5, 41, 'RouteMiami-Orlando'),
('2025-10-05', 50.00, 6, 2, 'RouteNY-DC'),
('2025-10-06', 40.00, 7, 12, 'RouteDallas-Houston'),
('2025-10-07', 60.00, 8, 22, 'RouteLA-SF'),
('2025-10-08', 45.00, 9, 32, 'RouteChicago-Detroit'),
('2025-10-09', 55.00, 10, 42, 'RouteMiami-Orlando');


