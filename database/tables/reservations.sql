CREATE SCHEMA IF NOT EXISTS reservations;

CREATE TABLE reservations.ReservationStatusDictionary (
    ReservationStatusId SERIAL PRIMARY KEY,
    ReservationStatusName VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE reservations.EventStatusDictionary (
    EventStatusId SERIAL PRIMARY KEY,
    EventStatusName VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE reservations.Sections (
    SectionId SERIAL PRIMARY KEY,
    IsSpecial BOOLEAN NOT NULL DEFAULT FALSE,
    SectionNumber INT NOT NULL,
    CompanyId INT NOT NULL REFERENCES common.CompanyDictionary(CompanyId)
);

CREATE TABLE reservations.Tables (
    TableId SERIAL PRIMARY KEY,
    TableNumber INT NOT NULL,
    SectionId INT REFERENCES reservations.Sections(SectionId),
    CompanyId INT NOT NULL REFERENCES common.CompanyDictionary(CompanyId)
);

CREATE TABLE reservations.Events (
    EventId SERIAL PRIMARY KEY,
    EventName VARCHAR(255) NOT NULL,
    EventDate DATE NOT NULL,
    CreatedBy INT REFERENCES common.Users(UserId),
    EventStatusId INT NOT NULL REFERENCES reservations.EventStatusDictionary(EventStatusId),
    CompanyId INT NOT NULL REFERENCES common.CompanyDictionary(CompanyId)
);

CREATE TABLE reservations.Reservations (
    ReservationId SERIAL PRIMARY KEY,
    EventId INT REFERENCES reservations.Events(EventId),
    TableId INT REFERENCES reservations.Tables(TableId),
    ReservationDate DATE NOT NULL,
    ReservationStatusId INT NOT NULL REFERENCES reservations.ReservationStatusDictionary(ReservationStatusId),
    ReservationName VARCHAR(255) NOT NULL,
    MoneySpent NUMERIC(10,2),
    CreatedAt TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CreatedBy INT REFERENCES common.Users(UserId),
    CompanyId INT NOT NULL REFERENCES common.CompanyDictionary(CompanyId)
);

CREATE TABLE reservations.ReservationMetadata (
    ReservationId INT PRIMARY KEY REFERENCES reservations.Reservations(ReservationId),
    UpdatedAt TIMESTAMP WITH TIME ZONE,
    UpdatedBy INT REFERENCES common.Users(UserId),
    CompletedAt TIMESTAMP WITH TIME ZONE,
    CompletedBy INT REFERENCES common.Users(UserId),
    CompanyId INT NOT NULL REFERENCES common.CompanyDictionary(CompanyId)
);

CREATE TABLE reservations.ReservationActionLogs (
    LogId SERIAL PRIMARY KEY,
    ReservationId INT NOT NULL REFERENCES reservations.Reservations(ReservationId),
    ActionBy INT NOT NULL REFERENCES common.Users(UserId),
    ActionType VARCHAR(50) NOT NULL,
    Description VARCHAR(255) NOT NULL,
    ChangedAt TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CompanyId INT NOT NULL REFERENCES common.CompanyDictionary(CompanyId)
);