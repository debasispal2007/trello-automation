# Trello Test Automation Framework

## Overview
This project is a test automation framework for Trello, implementing both UI and API tests using Java, Selenium WebDriver, Rest Assured, and Cucumber BDD framework.

## Prerequisites
- Java JDK 11 or higher
- Maven
- Chrome browser
- Docker (optional, for running tests in containers)

## Running Tests

### Local Execution

Run specific test types:

# Run UI tests only
```bash
mvn test -Dcucumber.filter.tags="@ui"
```

# Run API tests only
```bash
mvn test -Dcucumber.filter.tags="@api"
```

# Run all tests
```bash
mvn test
```

#Other useful commands:

### Clean and install dependencies, then run tests
```bash
mvn clean install test
```

### Generate detailed reports
```bash
mvn clean verify
```

### Docker Execution
```bash
# Build and run tests in Docker
docker compose up --build
```

### Test Reports
```bash
After test execution, you can find the HTML report at:
target/cucumber-reports/cucumber-pretty.html
```

## Project Features
✅ BDD approach using Cucumber  
✅ API testing using Rest Assured  
✅ UI testing using Selenium WebDriver  
✅ Page Object Model design pattern  
✅ Docker support for containerized testing  
✅ Detailed HTML reports  
✅ Automatic screenshot capture for failed tests  
✅ Both UI and API test coverage  

## Adding New Tests

### General Steps
1. Add new feature files in `src/test/resources/features/`
2. Create step definitions in `src/test/java/com/knab/stepdefinitions/`

### For UI Tests
1. Add new page objects in `src/main/java/com/knab/pages/`
2. Use `@ui` tag in feature file

### For API Tests
1. Add new API methods in `TrelloAPIClient.java`
2. Use `@api` tag in feature file