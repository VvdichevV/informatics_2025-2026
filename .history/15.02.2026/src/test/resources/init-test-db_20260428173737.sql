-- Test database initialization script
CREATE TABLE IF NOT EXISTS user (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    FirstName VARCHAR(100),
    LastName VARCHAR(100),
    Email VARCHAR(100),
    DateOfBirth VARCHAR(20),
    PhoneNumber VARCHAR(20),
    Address VARCHAR(255),
    picture LONGBLOB
);
