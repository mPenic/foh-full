CREATE SCHEMA IF NOT EXISTS kanban;

-- Task Status Dictionary
CREATE TABLE kanban.TaskStatusDictionary (
    StatusId SERIAL PRIMARY KEY,
    TaskStatusName VARCHAR(50) NOT NULL UNIQUE
);

-- Tasks
CREATE TABLE kanban.Tasks (
    TaskId SERIAL PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    Description TEXT,
    Priority INT,
    StatusId INT NOT NULL REFERENCES kanban.TaskStatusDictionary(StatusId),
    CreatedBy INT NOT NULL REFERENCES common.Users(UserId),
    AssignedTo INT NULL REFERENCES common.Users(UserId),
    CreatedAt TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CompanyId INT NOT NULL REFERENCES common.CompanyDictionary(CompanyId),
    RoleIds integer[] NOT NULL DEFAULT '{}'::integer[]
);

CREATE TABLE kanban.TaskActionLogs (
    LogId SERIAL PRIMARY KEY,
    TaskId INT NOT NULL REFERENCES kanban.Tasks(TaskId),
    ActionBy INT NOT NULL REFERENCES common.Users(UserId),
    ActionType VARCHAR(50) NOT NULL,
    Description TEXT,
    ChangedBy INT NOT NULL REFERENCES common.Users(UserId),
    ChangedAt TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
