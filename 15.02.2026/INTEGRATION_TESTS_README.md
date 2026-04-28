# User Management System - Integration Testing Setup

## 📋 Overview

Your project now includes **Layer 1 Local Integration Tests** using **TestContainers** and **JUnit 5**. These tests automatically:

- ✅ Spin up a real MySQL database in Docker
- ✅ Test all database operations (CRUD)
- ✅ Validate authentication logic
- ✅ Clean up automatically after tests

**No installation needed!** Just run the tests.

---

## 🚀 Quick Start (5 minutes)

### Prerequisites Check
```bash
# Verify you have what you need
java -version          # Should be 11+
mvn --version         # Should be 3.6+
docker --version      # Should be installed
docker ps             # Docker daemon should be running
```

### Run Tests
```bash
cd /home/vdichev26/Programming/Programming/Java/informatics_2025-2026/15.02.2026
mvn verify
```

✓ Done! Tests will run in 30-60 seconds.

---

## 📊 What Gets Tested

| Test | Purpose |
|------|---------|
| **Insert User** | Create and store new user |
| **Get User** | Retrieve user by username |
| **Get Non-Existent User** | Handle missing users gracefully |
| **Update User** | Modify user information |
| **Delete User** | Remove user permanently |
| **Login Success** | Authenticate with correct password |
| **Login Failure** | Reject wrong password |
| **Get All Users** | Retrieve multiple users |
| **Concurrent Operations** | Test thread-safety |

**Total: 9 Tests** covering all DatabaseHelper functionality

---

## 📁 Project Structure

```
15.02.2026/
├── pom.xml                          # ← Maven config (NEW)
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   └── DatabaseHelperL1Test.java  # ← Integration tests (NEW)
│   │   └── resources/
│   │       └── init-test-db.sql          # ← DB schema (NEW)
│   └── main/
│       ├── DatabaseHelper.java
│       ├── UserDAO.java
│       ├── User.java
│       └── ... (other classes)
├── .github/integration-tests/       # ← Documentation (NEW)
│   ├── INTEGRATION_TESTING_GUIDE.md
│   ├── integration-test-plan.md
│   ├── integration-test-summary.md
│   ├── run-layer1-tests.sh
│   └── run-all-tests.sh
└── target/                          # ← Test results
    └── surefire-reports/
```

---

## 📖 How to Use

### 1️⃣ Run All Tests
```bash
mvn verify
```

### 2️⃣ Run Specific Test
```bash
mvn verify -Dtest=DatabaseHelperL1Test#testInsertUser
```

### 3️⃣ Run Using Shell Script
```bash
chmod +x .github/integration-tests/run-layer1-tests.sh
./.github/integration-tests/run-layer1-tests.sh
```

### 4️⃣ View Test Results
```bash
cat target/surefire-reports/DatabaseHelperL1Test.txt
```

### 5️⃣ Clean Up Containers
```bash
mvn clean
docker container prune
```

---

## 🔍 Understanding the Tests

### How They Work

```
Your Test Code
     ↓
Requests DatabaseHelper.insertUser()
     ↓
Connects to TestContainers MySQL (NOT production DB)
     ↓
Database operation succeeds
     ↓
Test verifies result
     ↓
Container cleans up automatically
```

### Test Example
```java
@Test
void testInsertUser() {
    // Create test data
    User user = new User("johndoe", "pass", "John", "Doe", ...);
    
    // Call the method being tested
    boolean result = databaseHelper.insertUser(user);
    
    // Verify it worked
    assertTrue(result);
    assertEquals("John", databaseHelper.getUser("johndoe").getFirstName());
}
```

---

## ⚠️ Troubleshooting

### Problem: "Docker daemon is not running"
```bash
# Start Docker
docker daemon    # Linux
# OR open Docker Desktop app (Mac/Windows)
```

### Problem: Tests hang or timeout
```bash
# Check Docker resources
docker stats

# If needed, increase Docker memory to 4GB+ via Docker Desktop settings
```

### Problem: "Connection refused"
```bash
# Ensure no other MySQL on port 3306
lsof -i :3306

# TestContainers will find a free port automatically
```

### Problem: Tests not found
```bash
# Verify test class name ends with L1Test
# Ensure Maven can find the test
mvn test -Dtest=DatabaseHelperL1Test

# Clean and rebuild
mvn clean verify
```

---

## 📚 Documentation Files

| File | Purpose |
|------|---------|
| [INTEGRATION_TESTING_GUIDE.md](.github/integration-tests/INTEGRATION_TESTING_GUIDE.md) | Complete setup and usage guide |
| [integration-test-plan.md](.github/integration-tests/integration-test-plan.md) | Testing strategy and scenarios |
| [integration-test-summary.md](.github/integration-tests/integration-test-summary.md) | What was added and how it works |

---

## 🎯 Expected Results

When you run `mvn verify`, you should see:

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running DatabaseHelperL1Test
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
[INFO] -------------------------------------------------------
[INFO] BUILD SUCCESS
```

✓ All 9 tests should **PASS**

---

## 💡 Key Features

✅ **Real Database** - Tests use actual MySQL, not mocks  
✅ **Automatic Cleanup** - Docker containers are cleaned up automatically  
✅ **Isolated Tests** - Each test gets a fresh database  
✅ **No Setup Needed** - Just run Maven  
✅ **Fast** - Container startup is cached, tests run in ~30-60 seconds  
✅ **Thread-Safe** - Tests verify concurrent operations work correctly  
✅ **Comprehensive** - All CRUD operations + authentication tested  

---

## 🔧 Maven Commands

```bash
# Run all tests
mvn verify

# Run and skip tests
mvn verify -DskipTests

# Run only integration tests
mvn verify -Dgroups="Layer1"

# Run specific test class
mvn verify -Dtest=DatabaseHelperL1Test

# Run specific test method
mvn verify -Dtest=DatabaseHelperL1Test#testInsertUser

# Clean everything
mvn clean verify

# Verbose output
mvn verify -X
```

---

## 📋 Checklist

- [ ] Java 11+ installed (`java -version`)
- [ ] Maven 3.6+ installed (`mvn --version`)
- [ ] Docker installed and running (`docker ps`)
- [ ] Run first test (`mvn verify`)
- [ ] Check results in `target/surefire-reports/`

---

## 📞 Need Help?

1. **Check troubleshooting section above** ⬆️
2. **Read full guide**: [INTEGRATION_TESTING_GUIDE.md](.github/integration-tests/INTEGRATION_TESTING_GUIDE.md)
3. **View test plan**: [integration-test-plan.md](.github/integration-tests/integration-test-plan.md)
4. **Check test summary**: [integration-test-summary.md](.github/integration-tests/integration-test-summary.md)

---

## 🚀 Next Steps

### Immediate
1. ✅ Run `mvn verify` to see tests pass
2. ✅ Review test output in `target/surefire-reports/`
3. ✅ Explore the test code in `src/test/java/DatabaseHelperL1Test.java`

### Soon
- Add more test scenarios
- Extend tests with Layer 2 (smoke tests)
- Set up CI/CD pipeline (GitHub Actions, Azure Pipelines)

### Future
- Layer 3: Azure SQL integration tests
- Layer 4: Behavioral comparison tests
- Performance and load testing

---

**Created**: 2026-04-28  
**Status**: ✅ Ready to Use  
**First Command**: `mvn verify`
