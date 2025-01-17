Feature: Trello Board Operations

@api @regression
Scenario: Create a new board via API
    Given I have valid API credentials
    When I create a new board with name "Test Board API"
    Then the board should be created successfully
    And I should see the board details in the response
    And I clean up the test board

@api @regression
Scenario: Update a board name via API
    Given I have a board created via API
    When I update the board name to "Updated Board"
    Then the board name should be updated successfully
    And I clean up the test board
    
@api @regression
Scenario: Try to create a board with invalid API token
    Given I have invalid API token
    When I try to create a board with name "Test Board API"
    Then I should get unauthorized error response

@api @regression
Scenario: Try to update non-existent board
    Given I have valid API credentials
    When I try to update board with invalid id "invalid123" to name "Updated Board"
    Then I should get not found error response

@ui @regression
Scenario: Create a new board via UI
    Given I am logged into Trello
    When I click on create new board button
    And I enter board name "UI Test Board"
    And I click on create button
    Then I should see the board created successfully
    And I clean up the created board by UI

@ui @regression
Scenario: Add a list to board via UI
    Given I am logged into Trello
    When I click on create new board button
    And I enter board name "UI Test Board"
    And I click on create button
    And I click on add list button
    And I enter list name "Knab Tasks"
    And I click on add list submit button
    Then the list "Knab Tasks" should be created successfully
    And I clean up the created board by UI