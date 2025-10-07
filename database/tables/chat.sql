CREATE SCHEMA IF NOT EXISTS chat;

-- Groups
CREATE TABLE chat.Groups (
    GroupId SERIAL PRIMARY KEY,
    GroupName VARCHAR(255) NOT NULL,
    CreatedBy INT NOT NULL REFERENCES common.Users(UserId),
    CompanyId INT NOT NULL REFERENCES common.CompanyDictionary(CompanyId),
    CreatedAt TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- GroupUsers (Many-to-Many)
CREATE TABLE chat.GroupUsers (
    GroupId INT NOT NULL REFERENCES chat.Groups(GroupId) ON DELETE CASCADE,
    UserId INT NOT NULL REFERENCES common.Users(UserId) ON DELETE CASCADE,
    AddedAt TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (GroupId, UserId)
);

-- Messages
CREATE TABLE chat.Messages (
    MessageId SERIAL PRIMARY KEY,
    GroupId INT NOT NULL REFERENCES chat.Groups(GroupId) ON DELETE CASCADE,
    SenderId INT NOT NULL REFERENCES common.Users(UserId),
    MessageText TEXT NOT NULL,
    SentAt TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE chat.MessageReadReceipts (
    ReceiptId SERIAL PRIMARY KEY,
    MessageId INT NOT NULL REFERENCES chat.Messages(MessageId),
    UserId INT NOT NULL REFERENCES common.Users(UserId),
    ReadAt TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
