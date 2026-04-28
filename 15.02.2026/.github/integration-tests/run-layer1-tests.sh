#!/bin/bash

# Integration Test Runner Script - Layer 1
# Runs all Layer 1 local integration tests using Maven

set -e

echo "================================"
echo "Layer 1: Local Integration Tests"
echo "================================"
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed. Please install Maven first."
    exit 1
fi

# Check if Docker is running
if ! docker ps > /dev/null 2>&1; then
    echo "Error: Docker is not running. Please start Docker before running tests."
    exit 1
fi

echo "Maven version:"
mvn --version
echo ""

echo "Running Layer 1 integration tests..."
echo ""

# Run the tests
mvn verify -DskipITs=false -Dgroups="Layer1" 2>&1

# Capture exit code
EXIT_CODE=$?

echo ""
echo "================================"
if [ $EXIT_CODE -eq 0 ]; then
    echo "✓ All Layer 1 tests PASSED"
else
    echo "✗ Layer 1 tests FAILED (exit code: $EXIT_CODE)"
fi
echo "================================"

exit $EXIT_CODE
