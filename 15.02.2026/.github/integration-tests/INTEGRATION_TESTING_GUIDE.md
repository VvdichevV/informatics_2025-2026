# Integration Testing Guide

## Quick Start

This project now includes comprehensive Layer 1 integration tests using **TestContainers** and **JUnit 5**.

### Prerequisites

1. **Java 11+** - Installed and configured
2. **Maven 3.6+** - For building and running tests
3. **Docker** - Running and accessible
4. **Git** (optional) - For version control

### Installation & Setup

#### 1. Install Prerequisites

**macOS:**
```bash
brew install java maven docker
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-11-jdk maven docker.io
sudo usermod -aG docker $USER  # Add user to docker group
```

**Windows:**
- Download Java 11+ from https://www.oracle.com/java/technologies/downloads/
- Download Maven from https://maven.apache.org/download.cgi
- Install Docker Desktop from https://www.docker.com/products/docker-desktop

#### 2. Verify Installation

```bash
java -version
mvn --version
docker --version
```

#### 3. Start Docker (if not running)

```bash
# On macOS/Linux
docker daemon

# On Windows
# Open Docker Desktop application
```

---

## Running Integration Tests

### Run All Integration Tests

```bash
cd /home/vdichev26/Programming/Programming/Java/informatics_2025-2026/15.02.2026
mvn verify
```

This will:
1. Compile the project
2. Run all Layer 1 tests
3. Generate test reports
4. Clean up Docker containers

### Run Specific Test

```bash
mvn verify -Dtest=DatabaseHelperL1Test#testInsertUser
```

### Run Using Shell Script

Make the script executable and run:

```bash
chmod +x .github/integration-tests/run-layer1-tests.sh
./.github/integration-tests/run-layer1-tests.sh
```

Or for all layers:

```bash
chmod +x .github/integration-tests/run-all-tests.sh
./.github/integration-tests/run-all-tests.sh
```

### Run with Verbose Output

```bash
mvn verify -X
```

---

## What Gets Tested

The integration test suite validates the **DatabaseHelper** class (UserDAO implementation) with these 9 comprehensive tests:

### 1. **Insert User** (`testInsertUser`)
- Creates a new user in the database
- Verifies the user is stored correctly
- **Expected Result**: User is persisted and retrievable

### 2. **Get User** (`testGetUser`)
- Retrieves an existing user by username
- Validates all fields match what was stored
- **Expected Result**: User data is exact match

### 3. **Get Non-Existent User** (`testGetUserNotFound`)
- Attempts to retrieve a user that doesn't exist
- **Expected Result**: Returns null safely

### 4. **Update User** (`testUpdateUser`)
- Modifies an existing user's details
- Updates multiple fields (password, name, email)
- **Expected Result**: Changes persist to database

### 5. **Delete User** (`testDeleteUser`)
- Removes a user from the database
- Verifies the user cannot be retrieved afterward
- **Expected Result**: User is completely removed

### 6. **Login Success** (`testCheckLogin_SuccessfulLogin`)
- Tests authentication with correct credentials
- **Expected Result**: Authentication succeeds

### 7. **Login Failure** (`testCheckLogin_FailedLogin`)
- Tests authentication with incorrect password
- **Expected Result**: Authentication fails safely

### 8. **Get All Users** (`testGetAllUsers`)
- Retrieves all users from database
- Validates the correct number of users are returned
- **Expected Result**: List contains all inserted users

### 9. **Concurrent Operations** (`testConcurrentUserOperations`)
- Tests thread-safety of database operations
- Runs multiple insert operations simultaneously
- **Expected Result**: No data corruption, all operations succeed

---

## Test Results & Reporting

After running tests, check the results:

### Maven Console Output
```
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
```

### Test Reports Location
```
target/surefire-reports/DatabaseHelperL1Test.txt
target/surefire-reports/TEST-DatabaseHelperL1Test.xml
```

### View Detailed Report
```bash
cat target/surefire-reports/DatabaseHelperL1Test.txt
```

---

## How Integration Tests Work

### TestContainers Architecture

```
┌──────────────────────────────────────┐
│   Your Test (DatabaseHelperL1Test)   │
│                                      │
│  Creates DatabaseHelper instance    │
│  with test database connection      │
└──────────────────┬──────────────────┘
                   │
                   ↓
┌──────────────────────────────────────┐
│   TestContainers Framework           │
│                                      │
│  Manages MySQL 8.3.0 Container       │
│  - Starts container                  │
│  - Waits for readiness              │
│  - Provides JDBC URL & credentials   │
│  - Cleans up after tests            │
└──────────────────┬──────────────────┘
                   │
                   ↓
┌──────────────────────────────────────┐
│   Docker                             │
│                                      │
│  Runs isolated MySQL container       │
│  - Fresh database for each test      │
│  - No interference with production   │
│  - Automatic cleanup                │
└──────────────────────────────────────┘
```

### Test Lifecycle

For each test method:

1. **@BeforeAll**: Container starts (once per test class)
   - MySQL container is created
   - Database schema is initialized
   - Table structure is created

2. **@BeforeEach**: Test setup
   - Database connection is established
   - User table is truncated (cleared)
   - Test is ready to run

3. **Test Execution**: Your test runs
   - Creates test data
   - Calls DatabaseHelper methods
   - Asserts results

4. **@AfterEach**: Test cleanup
   - Connection is closed
   - Test state is cleared

5. **Container Shutdown**: Once all tests complete
   - Container stops
   - Resources are freed
   - No leftover processes

---

## Troubleshooting

### Problem: "Docker daemon is not running"

**Solution:**
```bash
# Start Docker
docker daemon

# Or on Docker Desktop, open the application
```

### Problem: "Connection refused to database"

**Solution:**
```bash
# Ensure Docker can start containers
docker ps

# If error, restart Docker
docker restart
```

### Problem: "Port already in use"

**Solution:**
TestContainers automatically finds free ports, but if you have other MySQL instances:
```bash
# Find MySQL processes
lsof -i :3306

# Kill if necessary
kill -9 <PID>
```

### Problem: Test hangs or times out

**Solution:**
```bash
# Increase Docker memory/resources
# Increase timeout in test with:
# myContainer.withStartupTimeout(Duration.ofSeconds(120))

# Or run with more verbose output
mvn verify -X -DtestFailureIgnore=true
```

### Problem: "Tests not found"

**Solution:**
```bash
# Ensure test class is named with L1Test suffix
# Ensure Maven can find the test
mvn test -Dtest=DatabaseHelperL1Test

# Clean and rebuild
mvn clean verify
```

---

## Project Structure

```
15.02.2026/
├── pom.xml                          # Maven configuration (NEW)
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   └── DatabaseHelperL1Test.java  # Integration tests (NEW)
│   │   └── resources/
│   │       └── init-test-db.sql          # Database schema (NEW)
│   └── main/
│       ├── DatabaseHelper.java      # Implementation under test
│       ├── UserDAO.java            # Interface
│       ├── User.java               # Model
│       ├── LoginForm.java
│       ├── RegistrationForm.java
│       └── ... (other classes)
└── .github/integration-tests/       # Documentation & scripts (NEW)
    ├── integration-test-plan.md
    ├── integration-test-summary.md
    ├── run-layer1-tests.sh
    └── run-all-tests.sh
```

---

## Maven Commands Reference

| Command | Purpose |
|---------|---------|
| `mvn verify` | Run all tests |
| `mvn clean` | Remove build artifacts |
| `mvn test` | Run unit tests only |
| `mvn verify -DskipTests` | Build without tests |
| `mvn help:describe -Dplugin=maven-failsafe-plugin` | Failsafe plugin info |
| `mvn -v` | Show Maven version |

---

## Next Steps

### Enhance Your Tests

1. **Add more test scenarios** - Test edge cases and error conditions
2. **Test with binary data** - Add picture data to some test users
3. **Test concurrent access** - More stress testing
4. **Add performance tests** - Measure response times

### Add More Layers

1. **Layer 2: Smoke Tests** - Docker-compose based smoke tests
2. **Layer 3: Azure Integration** - Test with real Azure SQL
3. **Layer 4: Behavioral Comparison** - Compare old vs new implementations

### Integrate with CI/CD

1. **GitHub Actions** - Run tests on every commit
2. **Azure Pipelines** - Deploy and test to Azure
3. **Jenkins** - Enterprise CI/CD integration

---

## Getting Help

### Documentation Files
- [Integration Test Plan](.github/integration-tests/integration-test-plan.md)
- [Integration Test Summary](.github/integration-tests/integration-test-summary.md)

### External Resources
- [TestContainers Documentation](https://www.testcontainers.org)
- [JUnit 5 Guide](https://junit.org/junit5/docs/current/user-guide)
- [Maven Documentation](https://maven.apache.org)
- [Docker Getting Started](https://docs.docker.com/get-started)

---

**Last Updated**: 2026-04-28  
**Project**: User Management System (Informatics 2025-2026)
