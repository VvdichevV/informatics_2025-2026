# Integration Test Plan - Layer 1 (Local Integration Tests)

## Overview
This document outlines the integration testing strategy for the User Management System using TestContainers with MySQL.

## Testing Strategy

### Purpose
Layer 1 integration tests validate that the DatabaseHelper (DAO) layer correctly interacts with MySQL database without mocking, using isolated TestContainers to manage the database lifecycle.

### Approach
- Use TestContainers to spin up a real MySQL 8.3.0 container for each test run
- Tests run against the real DatabaseHelper implementation
- No mocking of database connections or SQL execution
- Each test is independent and runs in isolation

## Modules to be Tested

### Primary Component
- **DatabaseHelper**: Implements UserDAO interface with MySQL operations
  - `insertUser()`: Persist new user to database
  - `getUser()`: Retrieve user by username
  - `updateUser()`: Update existing user
  - `deleteUser()`: Remove user from database
  - `checkLogin()`: Verify user credentials
  - `getAllUsers()`: Retrieve all users

### Supporting Components
- **UserDAO Interface**: Contract for database operations
- **User Model**: Data transfer object for user information

## Test Scenarios Covered

### CRUD Operations (Create, Read, Update, Delete)
1. **testInsertUser**: Verify new user can be inserted and retrieved
2. **testGetUser**: Verify user can be retrieved with correct data
3. **testGetUserNotFound**: Verify non-existent user returns null
4. **testUpdateUser**: Verify user fields can be updated
5. **testDeleteUser**: Verify user can be deleted and is no longer retrievable

### Authentication
6. **testCheckLogin_SuccessfulLogin**: Verify correct credentials authenticate
7. **testCheckLogin_FailedLogin**: Verify incorrect credentials fail

### Data Integrity
8. **testGetAllUsers**: Verify multiple users can be retrieved
9. **testConcurrentUserOperations**: Verify thread-safety of database operations

## Dependencies and Setup Requirements

### Required Libraries
- **TestContainers**: 1.19.1
  - Core framework for container management
  - MySQL module for database container
  - JUnit 5 integration
- **JUnit 5**: 5.9.3
  - Test framework with modern annotations
  - @Tag support for test categorization
- **MySQL Connector/J**: 8.3.0
  - JDBC driver for MySQL connectivity

### Database Schema
- Automatically created during test setup
- Uses same schema as production database
- Schema includes all columns: username (PK), password, FirstName, LastName, Email, DateOfBirth, PhoneNumber, Address, picture

### Test Environment
- Each test gets a fresh MySQL container
- Database is automatically cleaned before each test
- Connection parameters are injected into DatabaseHelper
- Container is automatically stopped and cleaned up after tests complete

## Validation Criteria

### Success Criteria
- All 9 tests pass
- No SQL exceptions during test execution
- Data persists correctly across multiple operations
- User retrieval returns exact data that was stored
- Concurrent operations don't cause data corruption
- Container startup and shutdown is clean

### Expected Assertions
- User objects are not null after insertion
- Retrieved user data matches inserted data exactly
- Non-existent users return null
- Update operations succeed and modify data
- Delete operations succeed and remove data
- Login verification works correctly for both valid and invalid credentials
- getAllUsers returns correct user count
- Thread operations complete without errors

## Test Execution

### Run All Layer 1 Tests
```bash
mvn verify
```

### Run Specific Test
```bash
mvn verify -Dtest=DatabaseHelperL1Test#testInsertUser
```

### Run Tests with Maven Failsafe
```bash
mvn verify -Dgroups="Layer1"
```

## Known Limitations

1. This is Layer 1 (Local) - does not test against real Azure services
2. TestContainers requires Docker to be installed and running
3. Test execution takes longer than unit tests due to container startup
4. Binary picture data is set to null in tests (can be enhanced)

## Next Steps for Enhancement

- Layer 2: Add smoke tests with docker-compose
- Layer 3: Add Azure SQL integration tests
- Layer 4: Add behavioral comparison tests between old and new implementations

---
Generated: 2026-04-28
Project: User Management System (Informatics 2025-2026)
