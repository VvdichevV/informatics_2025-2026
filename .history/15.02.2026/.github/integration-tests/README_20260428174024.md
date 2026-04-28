# Integration Testing - Documentation Index

Welcome! Here's where to find what you need:

---

## 🚀 **Just Want to Run Tests?**

```bash
mvn verify
```

Done! Tests run in 30-60 seconds.

---

## 📖 **Documentation Guide**

### ⏱️ I have 5 minutes
👉 **Start Here**: [`INTEGRATION_TESTS_README.md`](../../INTEGRATION_TESTS_README.md)
- Quick overview
- How to run tests
- Basic troubleshooting
- What gets tested

### ⏱️ I have 30 minutes
👉 **Read This**: [`INTEGRATION_TESTING_GUIDE.md`](INTEGRATION_TESTING_GUIDE.md)
- Complete setup guide
- Installation instructions
- Detailed test explanations
- Comprehensive troubleshooting
- Maven commands reference
- CI/CD integration examples

### ⏱️ I want to understand the strategy
👉 **See**: [`integration-test-plan.md`](integration-test-plan.md)
- Testing approach
- Components being tested
- Test scenarios (9 total)
- Validation criteria
- Dependencies

### ⏱️ I want to know what was added
👉 **Check**: [`integration-test-summary.md`](integration-test-summary.md)
- What was implemented
- Test coverage details
- Technology stack
- Test results format
- Future enhancements

### ⏱️ I need quick commands
👉 **Use**: [`QUICK_REFERENCE.md`](QUICK_REFERENCE.md)
- One-line commands
- Test overview table
- Troubleshooting quick fixes
- Prerequisites checklist

### ⏱️ I want to see all files created
👉 **Read**: [`FILES_CREATED.md`](FILES_CREATED.md)
- Complete file listing
- What each file does
- File structure diagram
- Getting started steps
- Learning resources

---

## 🎯 **By Use Case**

### I want to run the tests
```bash
# Simple
mvn verify

# Using script
bash run-layer1-tests.sh

# Specific test
mvn verify -Dtest=DatabaseHelperL1Test#testInsertUser
```
👉 **Guide**: [`QUICK_REFERENCE.md`](QUICK_REFERENCE.md)

### I want to understand the tests
👉 **Check**: [`integration-test-summary.md`](integration-test-summary.md)

### I'm having problems
1. Check [`QUICK_REFERENCE.md`](QUICK_REFERENCE.md) - Troubleshooting section
2. Then read [`INTEGRATION_TESTING_GUIDE.md`](INTEGRATION_TESTING_GUIDE.md) - Troubleshooting section

### I need to install/set up
👉 **Follow**: [`INTEGRATION_TESTING_GUIDE.md`](INTEGRATION_TESTING_GUIDE.md) - Installation section

### I want to learn everything
👉 **Read in order**:
1. [`INTEGRATION_TESTS_README.md`](../../INTEGRATION_TESTS_README.md)
2. [`INTEGRATION_TESTING_GUIDE.md`](INTEGRATION_TESTING_GUIDE.md)
3. [`integration-test-plan.md`](integration-test-plan.md)
4. [`integration-test-summary.md`](integration-test-summary.md)
5. [`QUICK_REFERENCE.md`](QUICK_REFERENCE.md)

### I'm extending/modifying tests
1. Read [`integration-test-plan.md`](integration-test-plan.md) - Testing principles
2. Check test code: `../../src/test/java/DatabaseHelperL1Test.java`
3. Reference [`integration-test-summary.md`](integration-test-summary.md) - Best practices

---

## 📁 **File Structure**

```
docs/
├── README.md (this file) ← You are here
├── QUICK_REFERENCE.md (1 page cheat sheet)
├── INTEGRATION_TESTING_GUIDE.md (comprehensive guide)
├── integration-test-plan.md (testing strategy)
├── integration-test-summary.md (what was built)
├── integration-test-summary.md (implementation details)
├── FILES_CREATED.md (file listing)
├── run-layer1-tests.sh (test runner script)
└── run-all-tests.sh (master runner script)

And at project root:
├── INTEGRATION_TESTS_README.md (quick start)
├── pom.xml (Maven config)
├── src/
│   └── test/
│       ├── java/
│       │   ├── DatabaseHelperL1Test.java (tests)
│       │   └── TestDatabaseHelper.java (test helper)
│       └── resources/
│           └── init-test-db.sql (schema)
```

---

## ✅ **Checklist**

Getting started:
- [ ] Java 11+ installed (`java -version`)
- [ ] Maven 3.6+ installed (`mvn --version`)
- [ ] Docker installed and running (`docker ps`)
- [ ] Read [`INTEGRATION_TESTS_README.md`](../../INTEGRATION_TESTS_README.md)
- [ ] Run `mvn verify` from project directory

Learning:
- [ ] Understand how tests work (read INTEGRATION_TESTING_GUIDE.md)
- [ ] Review test code (src/test/java/DatabaseHelperL1Test.java)
- [ ] Run tests successfully
- [ ] Explore test output

Extending:
- [ ] Add new test method to DatabaseHelperL1Test
- [ ] Run tests again with `mvn verify`
- [ ] Verify new test passes

---

## 🆘 **Troubleshooting**

### Tests won't run
1. Check [`QUICK_REFERENCE.md`](QUICK_REFERENCE.md) - Troubleshooting section
2. Read [`INTEGRATION_TESTING_GUIDE.md`](INTEGRATION_TESTING_GUIDE.md) - Full troubleshooting

### Don't understand something
1. Check table of contents in [`INTEGRATION_TESTING_GUIDE.md`](INTEGRATION_TESTING_GUIDE.md)
2. See if topic is covered
3. Read the relevant section

### Need more examples
1. Look at `src/test/java/DatabaseHelperL1Test.java`
2. See [`integration-test-summary.md`](integration-test-summary.md) for code patterns

---

## 🚀 **Quick Start Commands**

```bash
# Navigate to project
cd /home/vdichev26/Programming/Programming/Java/informatics_2025-2026/15.02.2026

# Run all tests
mvn verify

# Run specific test
mvn verify -Dtest=DatabaseHelperL1Test#testInsertUser

# View results
cat target/surefire-reports/DatabaseHelperL1Test.txt

# Run with details
mvn verify -X

# Clean and rebuild
mvn clean verify

# Run using script
bash .github/integration-tests/run-layer1-tests.sh
```

---

## 📊 **Tests Summary**

**Total Tests**: 9  
**Test File**: `src/test/java/DatabaseHelperL1Test.java`  
**Technology**: JUnit 5 + TestContainers + MySQL

| Test | Purpose |
|------|---------|
| testInsertUser | Save new user |
| testGetUser | Retrieve user |
| testGetUserNotFound | Handle missing user |
| testUpdateUser | Modify user |
| testDeleteUser | Remove user |
| testCheckLogin_SuccessfulLogin | Correct password works |
| testCheckLogin_FailedLogin | Wrong password fails |
| testGetAllUsers | Get multiple users |
| testConcurrentUserOperations | Thread-safety |

---

## 🎓 **Learning Path**

1. **Level 1: Just Run It**
   - Run: `mvn verify`
   - See all tests pass
   - ✓ Done

2. **Level 2: Understand It**
   - Read: [`INTEGRATION_TESTS_README.md`](../../INTEGRATION_TESTS_README.md)
   - Takes: 5 minutes
   - ✓ Understand basics

3. **Level 3: Deep Dive**
   - Read: [`INTEGRATION_TESTING_GUIDE.md`](INTEGRATION_TESTING_GUIDE.md)
   - Takes: 20-30 minutes
   - ✓ Expert knowledge

4. **Level 4: Extend It**
   - Edit: `src/test/java/DatabaseHelperL1Test.java`
   - Add: New test methods
   - Run: `mvn verify`
   - ✓ Customize for your needs

---

## 📞 **Help**

| Question | Answer |
|----------|--------|
| How do I run tests? | `mvn verify` - See QUICK_REFERENCE.md |
| What gets tested? | All DatabaseHelper methods - See integration-test-summary.md |
| How do I fix errors? | See troubleshooting in INTEGRATION_TESTING_GUIDE.md |
| How do I add tests? | See integration-test-plan.md for principles |
| What's TestContainers? | See INTEGRATION_TESTING_GUIDE.md for explanation |
| How long do tests take? | ~45 sec first run, ~30 sec after - See QUICK_REFERENCE.md |

---

## 🎯 **Next Steps**

1. ✅ Run tests: `mvn verify`
2. ✅ Check results (all tests pass?)
3. ✅ Read [`INTEGRATION_TESTS_README.md`](../../INTEGRATION_TESTS_README.md)
4. ✅ Explore test code in `src/test/java/`
5. ✅ Add more test scenarios
6. ✅ Set up CI/CD pipeline

---

## 📚 **All Documentation Files**

- **README.md** (this file) - Documentation index
- **INTEGRATION_TESTS_README.md** (project root) - Quick start
- **QUICK_REFERENCE.md** - Cheat sheet
- **INTEGRATION_TESTING_GUIDE.md** - Complete guide
- **integration-test-plan.md** - Testing strategy
- **integration-test-summary.md** - Implementation details
- **FILES_CREATED.md** - What was added

---

**Last Updated**: 2026-04-28  
**Status**: ✅ Ready to Use  
**First Command**: `mvn verify`  
**Time to First Test**: < 2 minutes
