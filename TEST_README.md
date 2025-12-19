# Unit Tests Documentation

This project includes comprehensive unit tests using JUnit 5.

## Running Tests

### Using Maven
```bash
mvn test
```

### Running specific test class
```bash
mvn test -Dtest=PlayerTest
mvn test -Dtest=PlayerManagerTest
```

### Running all tests in a package
```bash
mvn test -Dtest=com.mojo.plstats.core.*
```

## Test Coverage

### Core Domain Tests
- **PlayerTest**: Tests validation, setters, getters, and business logic for the Player class
  - Name validation (null, empty)
  - Age validation (negative, minimum age)
  - Club validation
  - Position validation (all valid positions)
  - Goals/Assists validation (negative values)
  - Height validation (range 100-250cm)
  - Nationality validation
  - Preferred foot validation (L/R)
  - Market value validation
  - Update methods (goals, assists, market value, availability)
  - Equals and hashCode methods

- **GoalkeeperTest**: Tests goalkeeper-specific functionality
  - Clean sheets management
  - Display info formatting

- **OutfieldPlayerTest**: Tests outfield player creation and validation

### Application Layer Tests
- **PlayerManagerTest**: Tests all CRUD operations and business logic
  - Add player (success, null, duplicates)
  - Remove player (by name, case-insensitive)
  - Search player (by name, case-insensitive)
  - Transfer player
  - Highest scorers (top 10 limit)
  - Most assists (top 10 limit)
  - Filter players (by club, position, age, combinations)
  - Sort players (by age, goals, market value, etc.)
  - Compare players
  - Edge cases (empty lists, not found, etc.)

### Infrastructure Tests
- **PlayerFileHandlerTest**: Tests file I/O operations
  - Save players to file
  - Load players from file
  - Data preservation (round-trip)
  - Error handling (invalid lines, empty files, non-existent files)
  - Special characters handling
  - Goalkeeper data preservation

## Test Statistics

- **Total Test Classes**: 5
- **Total Test Methods**: ~60+
- **Coverage Areas**: 
  - Core domain validation
  - Business logic
  - File I/O operations
  - Edge cases and error handling

## Best Practices Used

1. **@DisplayName**: All tests use descriptive display names
2. **@BeforeEach**: Setup methods for test data initialization
3. **@TempDir**: Temporary directories for file tests (JUnit 5)
4. **Assertions**: Comprehensive assertions using JUnit 5 assertions
5. **Test Isolation**: Each test is independent
6. **Edge Cases**: Tests cover boundary conditions and error scenarios

## Running Tests in IDE

### IntelliJ IDEA
1. Right-click on `src/test/java` folder
2. Select "Run All Tests"
3. Or right-click on individual test classes/methods

### Eclipse
1. Right-click on test class
2. Select "Run As" → "JUnit Test"

### VS Code
1. Install Java Test Runner extension
2. Tests will appear with play buttons next to them

## Continuous Integration

These tests can be integrated into CI/CD pipelines:
```yaml
# Example GitHub Actions
- name: Run tests
  run: mvn test
```

