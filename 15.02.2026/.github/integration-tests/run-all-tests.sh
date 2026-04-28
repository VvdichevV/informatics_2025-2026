#!/bin/bash

# Integration Test Runner Script - All Layers
# Runs all available integration tests

set -e

echo "====================================="
echo "User Management System"
echo "Integration Test Suite Runner"
echo "====================================="
echo ""

# Check prerequisites
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed. Please install Maven first."
    exit 1
fi

if ! docker ps > /dev/null 2>&1; then
    echo "Error: Docker is not running. Please start Docker before running tests."
    exit 1
fi

echo "Environment Check:"
echo "  Maven: $(mvn --version | head -1)"
echo "  Docker: $(docker --version)"
echo ""

# Get the project directory
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
echo "Project Directory: $PROJECT_DIR"
echo ""

# Run Maven verify with all integration tests
echo "Running integration tests..."
echo ""

cd "$PROJECT_DIR"
mvn clean verify -DskipITs=false

EXIT_CODE=$?

echo ""
echo "====================================="
if [ $EXIT_CODE -eq 0 ]; then
    echo "✓ All Integration Tests PASSED"
    echo ""
    echo "Test Summary:"
    echo "  - Layer 1: Local Integration Tests"
    echo "  - Components Tested: DatabaseHelper, UserDAO, User Model"
    echo "  - Test Count: 9 tests"
else
    echo "✗ Integration Tests FAILED (exit code: $EXIT_CODE)"
fi
echo "====================================="

exit $EXIT_CODE
