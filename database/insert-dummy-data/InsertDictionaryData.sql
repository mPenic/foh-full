-- Companies
INSERT INTO common.CompanyDictionary (CompanyName) VALUES
('Secret Club');
-- Roles
INSERT INTO common.RoleDictionary (RoleName) VALUES
('Technical Staff'),
('Bartender'),
('Promotor'),
('Promotor+'),
('Admin');

-- Reservation Statuses
INSERT INTO reservations.ReservationStatusDictionary (ReservationStatusName) VALUES
('Pending'),
('Confirmed'),
('In Progress'),
('Completed'),
('Cancelled');

-- Event Statuses
INSERT INTO reservations.EventStatusDictionary (EventStatusName) VALUES
('Open'),
('Closed'),
('Cancelled');

-- Task Statuses
INSERT INTO kanban.TaskStatusDictionary(TaskStatusName) VALUES
('Open'),
('Pending'),
('In Progress'),
('Completed');