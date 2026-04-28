# Integration Testing Quick Reference Card

## 🎯 One-Line Commands

```bash
# Run all tests
mvn verify

# Run one test
mvn verify -Dtest=DatabaseHelperL1Test#testInsertUser

# View results
cat target/surefire-reports/DatabaseHelperL1Test.txt

# Clean and rebuild
mvn clean verify

# Skip tests (just build)
mvn verify -DskipTests

# Run with details
mvn verify -X
```

---

## 📊 What Tests Do

| Test Name | What It Tests |
|-----------|---------------|
| `testInsertUser` | Can save new user to database |
| `testGetUser` | Can retrieve user and data is correct |
| `testGetUserNotFound` | Returns null for non-existent user |
| `testUpdateUser` | Can modify user information |
| `testDeleteUser` | Can remove user permanently |
| `testCheckLogin_SuccessfulLogin` | Correct password authenticates |
| `testCheckLogin_FailedLogin` | Wrong password rejects login |
| `testGetAllUsers` | Can retrieve multiple users |
| `testConcurrentUserOperations` | Thread-safe database access |

---

## 🚨 Troubleshooting

| Problem | Solution |
|---------|----------|
| Docker not running | `docker daemon` or open Docker Desktop |
| Maven not found | Install Maven or add to PATH |
| Connection refused | Ensure Docker daemon is running |
| Test hangs | Increase Docker memory to 4GB |
| Tests not found | Check class name ends with `L1Test` |
| Port in use | Kill process: `lsof -i :3306 \| kill -9` |

---

## 📁 Key Files

| File | Purpose |
|------|---------|
| `pom.xml` | Maven configuration |
| `src/test/java/DatabaseHelperL1Test.java` | Integration tests |
| `src/test/java/TestDatabaseHelper.java` | Test database helper |
| `src/test/resources/init-test-db.sql` | Database schema |
| `INTEGRATION_TESTS_README.md` | Quick start guide |
| `.github/integration-tests/INTEGRATION_TESTING_GUIDE.md` | Full guide |

---

## ✅ Prerequisites

- Java 11+
- Maven 3.6+
- Docker (running)
- Git (optional)

**Check**: `java -version && mvn --version && docker ps`

---

## 🔄 Test Lifecycle

```
1. TestContainers starts MySQL container
2. Database schema is initialized
3. Test method runs
4. Database is verified
5. Container cleans up automatically
```

No cleanup needed from you!

---

## 📈 Expected Results

```
Tests run: 9
Failures: 0
Errors: 0
Skipped: 0
Time: ~45 seconds

BUILD SUCCESS ✓
```

---

## 🎓 Learning Path

1. **First**: Read [INTEGRATION_TESTS_README.md](INTEGRATION_TESTS_README.md)
2. **Then**: Run `mvn verify`
3. **Next**: Explore test code in `src/test/java/DatabaseHelperL1Test.java`
4. **Learn**: Read [INTEGRATION_TESTING_GUIDE.md](.github/integration-tests/INTEGRATION_TESTING_GUIDE.md)
5. **Extend**: Add more test scenarios

---

## 💡 Tips

✨ **First run takes longer** (container startup) - ~60 seconds  
✨ **Subsequent runs faster** - ~30-40 seconds  
✨ **Tests are isolated** - Each test gets fresh database  
✨ **No production DB affected** - Tests use container only  
✨ **Real database used** - Not mocked, real MySQL operations  

---

## 🚀 Get Started Now

```bash
cd /home/vdichev26/Programming/Programming/Java/informatics_2025-2026/15.02.2026
mvn verify
```

That's it! Tests will run automatically. ✓

---

## 📞 Help

- **Quick start**: See [INTEGRATION_TESTS_README.md](INTEGRATION_TESTS_README.md)
- **Full guide**: See [INTEGRATION_TESTING_GUIDE.md](.github/integration-tests/INTEGRATION_TESTING_GUIDE.md)
- **Test plan**: See [integration-test-plan.md](.github/integration-tests/integration-test-plan.md)
- **What was added**: See [integration-test-summary.md](.github/integration-tests/integration-test-summary.md)

---

**Created**: 2026-04-28  
**Format**: Quick Reference  
**Status**: Ready to Use ✓
