
--Insert Users
INSERT INTO common.Users (Username, PasswordHash, RoleId, CompanyId)
VALUES 
('promotor', '$2a$12$BB3T16NW82o5C8C5rkcGn.kBnvcnkHWuPQ6QLymWQc83wiL27L0V6', 2, 1),
('promotorplus', '$2a$12$4eLOG6bqGQPaI3gZPSmcnujIPN3CfkY4jBbUYsk9t5n/PW6/UyNlW', 3, 1),
('bartender', '$2a$12$2FPZqlx3XL8ZvY51VDLMP.fsSrPTLech4H34GvwmjTqAgM6KHsMtC', 4, 1),
('admin', '$2a$12$Br.cme4JHpzSJT6dowljMerG7PEkgBu/w4hg3.xhlmXIU54nDjUTy', 5, 1);
-- Insert Sections
INSERT INTO reservations.Sections (IsSpecial, SectionNumber, CompanyId)
VALUES
(FALSE, 101, 1),
(FALSE, 102, 1),
(TRUE, 201, 1),
(FALSE, 103, 1),
(TRUE, 202, 1);  -- A special section

-- Insert Tables
INSERT INTO reservations.Tables (TableNumber, SectionId, CompanyId)
VALUES
(1, 1, 1),
(2, 1, 1),
(3, 2, 1),
(4, 2, 1),
(10, 3, 1),
(5, 4, 1),
(20, 5, 1); 

-- Insert Event
INSERT INTO reservations.Events (EventName, EventDate, CreatedBy, EventStatusId, CompanyId)
VALUES
('Christmas Party', CURRENT_DATE, 4, 1, 1),
('New Years', CURRENT_DATE + INTERVAL '10 days', 4, 2, 1);

INSERT INTO reservations.Reservations (
    EventId, TableId, ReservationDate, ReservationStatusId, ReservationName, MoneySpent, CreatedBy, CompanyId
)
VALUES
-- For EventId=1 (Summer Party)
(1, NULL, CURRENT_DATE, 1, 'Kico',    NULL, 1, 1),   -- Pending, no table
(1, NULL, CURRENT_DATE, 1, 'Čmarino',NULL, 1, 1),   -- Pending
(1, NULL, CURRENT_DATE, 1, 'Sislav', NULL, 1, 1),   -- Pending
(1, 1, CURRENT_DATE, 2, 'Zoran', NULL, 2, 1), -- In Progress, TableId=1

-- For EventId=2 (New Years)
(2, 2, CURRENT_DATE - INTERVAL '10 days', 1, 'Gaser 1', NULL, 1, 1),
(2, 3, CURRENT_DATE - INTERVAL '10 days', 3, 'Gaser 2', 200.00, 3, 1);

INSERT INTO reservations.ReservationMetadata (ReservationId, UpdatedAt, UpdatedBy, CompletedAt, CompletedBy, CompanyId)
VALUES
(1, CURRENT_TIMESTAMP, 2, NULL, NULL, 1),
(4, CURRENT_TIMESTAMP, 2, NULL, NULL, 1),  
(6, CURRENT_TIMESTAMP, 3, CURRENT_TIMESTAMP, 3, 1); 

-- Insert ActionLogs
INSERT INTO reservations.ReservationActionLogs (ReservationId, ActionBy, ActionType, Description, CompanyId)
VALUES
(1, 2, 'STATUS_CHANGE', 'Changed reservation #1 to In Progress', 1),
(6, 3, 'STATUS_CHANGE', 'Changed reservation #6 to Completed', 1);

