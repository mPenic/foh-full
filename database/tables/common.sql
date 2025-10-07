CREATE SCHEMA IF NOT EXISTS common;

-- Company Dictionary
CREATE TABLE common.CompanyDictionary (
    CompanyId SERIAL PRIMARY KEY,
    CompanyName VARCHAR(100) UNIQUE NOT NULL,
    feat_Ch BOOLEAN NOT NULL DEFAULT FALSE,
    feat_Kb BOOLEAN NOT NULL DEFAULT FALSE,
    feat_Re BOOLEAN NOT NULL DEFAULT FALSE
);

-- Dictionaries
CREATE TABLE common.RoleDictionary (
    RoleId SERIAL PRIMARY KEY,
    RoleName VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE common.Users (
    UserId SERIAL PRIMARY KEY,
    Username VARCHAR(255) NOT NULL UNIQUE,
    PasswordHash VARCHAR(255) NOT NULL,
    RoleId INT NOT NULL REFERENCES common.RoleDictionary(RoleId),
    CompanyId INT NOT NULL REFERENCES common.CompanyDictionary(CompanyId)
);

