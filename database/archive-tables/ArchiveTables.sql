CREATE SCHEMA IF NOT EXISTS archive;

CREATE TABLE archive.Events (LIKE Events INCLUDING ALL);
CREATE TABLE archive.Reservations (LIKE Reservations INCLUDING ALL);
CREATE TABLE archive.ActionLogs(LIKE ActionLogs INCLUDING ALL);
