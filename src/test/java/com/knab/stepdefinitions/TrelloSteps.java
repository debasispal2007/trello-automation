package com.knab.stepdefinitions;

import com.knab.api.TrelloAPIClient;
import com.knab.pages.BoardPage;
import com.knab.pages.LoginPage;
import com.knab.hooks.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import java.util.Properties;

public class TrelloSteps {
	private final TrelloAPIClient apiClient;
	private Response apiResponse;
	private String boardId;
	private static final String INVALID_TOKEN = "invalid_token_123";
	private static final String INVALID_BOARD_ID = "invalid123";

	// UI components
	private final WebDriver driver;
	private final Properties properties;
	private final LoginPage loginPage;
	private final BoardPage boardPage;

	public TrelloSteps() {
		// Initialize API components
		this.apiClient = new TrelloAPIClient();

		// Initialize UI components
		this.driver = Hooks.getDriver();
		this.properties = Hooks.getProperties();
		this.loginPage = new LoginPage();
		this.boardPage = new BoardPage();
	}

	@Given("I have valid API credentials")
	public void i_have_valid_api_credentials() {
		Response response = apiClient.verifyApiCredentials();
		Assert.assertEquals(200, response.getStatusCode());
	}

	@When("I create a new board with name {string}")
	public void iCreateNewBoard(String boardName) {
		apiResponse = apiClient.createBoard(boardName);
		if (apiResponse.getStatusCode() == 200) {
			boardId = apiResponse.jsonPath().getString("id");
		}
	}

	@Then("the board should be created successfully")
	public void boardShouldBeCreated() {
		Assert.assertEquals(200, apiResponse.getStatusCode());
		Assert.assertNotNull("Board ID should not be null", boardId);
	}

	@Then("I should see the board details in the response")
	public void iShouldSeeBoardDetails() {
		Assert.assertNotNull(apiResponse.jsonPath().getString("id"));
		Assert.assertNotNull(apiResponse.jsonPath().getString("name"));
	}

	@Given("I have a board created via API")
	public void iHaveBoardCreated() {
		apiResponse = apiClient.createBoard("Test Board");
		Assert.assertEquals(200, apiResponse.getStatusCode());
		boardId = apiResponse.jsonPath().getString("id");
		Assert.assertNotNull("Board creation failed", boardId);
	}

	@When("I update the board name to {string}")
	public void iUpdateBoardName(String newName) {
		apiResponse = apiClient.updateBoard(boardId, newName);
	}

	@Then("the board name should be updated successfully")
	public void boardNameShouldBeUpdated() {
		Assert.assertEquals(200, apiResponse.getStatusCode());
		Assert.assertEquals("Updated Board", apiResponse.jsonPath().getString("name"));
	}

	@Then("I clean up the test board")
	public void iCleanUpTestBoard() {
		if (boardId != null) {
			Response deleteResponse = apiClient.deleteBoard(boardId);
			Assert.assertEquals(200, deleteResponse.getStatusCode());
		}
	}

	@Given("I have invalid API token")
	public void i_have_invalid_api_token() {
		// Using invalid token for negative testing
		apiClient.setInvalidToken(INVALID_TOKEN);
	}

	@When("I try to create a board with name {string}")
	public void i_try_to_create_board_invalid_token(String boardName) {
		apiResponse = apiClient.createBoard(boardName);
	}

	@Then("I should get unauthorized error response")
	public void i_should_get_unauthorized_error() {
		Assert.assertEquals("Expected unauthorized error code", 401, apiResponse.getStatusCode());
		String actualErrorMessage = apiResponse.getBody().asString().trim();
		Assert.assertTrue("Unexpected error message: " + actualErrorMessage,
				actualErrorMessage.contains("invalid") && actualErrorMessage.contains("token"));
	}

	@When("I try to update board with invalid id {string} to name {string}")
	public void i_try_to_update_invalid_board(String invalidBoardId, String newName) {
		apiResponse = apiClient.updateBoard(invalidBoardId, newName);
	}

	@Then("I should get not found error response")
	public void i_should_get_not_found_error() {
		Assert.assertEquals("Expected not found error", 400, apiResponse.getStatusCode());
	}

	// UI Steps
	@Given("I am logged into Trello")
	public void i_am_logged_into_trello() {
		String email = properties.getProperty("trello.ui.email");
		String password = properties.getProperty("trello.ui.password");
		loginPage.loginToTrello(email, password);
		Assert.assertTrue("Login failed", loginPage.isLoggedIn());
	}

	@When("I click on create new board button")
	public void i_click_on_create_new_board_button() {
		boardPage.clickCreateBoard();
	}

	@When("I enter board name {string}")
	public void i_enter_board_name(String boardName) {
		boardPage.enterBoardTitle(boardName);
	}

	@When("I click on create button")
	public void i_click_on_create_button() {
		boardPage.submitBoardCreation();
	}

	@Then("I should see the board created successfully")
	public void i_should_see_the_board_created_successfully() {
		Assert.assertTrue("Board creation verification failed", boardPage.isBoardCreated("UI Test Board"));
	}

	@Then("I clean up the created board by UI")
	public void i_clean_up_created_board_by_ui() {
		try {
			boardPage.deleteBoard();
			// Wait for redirect to boards page
			Assert.assertTrue("Board was not deleted successfully", driver.getCurrentUrl().contains("board"));
		} catch (Exception e) {
			System.out.println("Failed to delete board via UI: " + e.getMessage());
		}
	}

	@When("I click on add list button")
	public void i_click_on_add_list_button() {
		boardPage.clickAddList();
	}

	@When("I enter list name {string}")
	public void i_enter_list_name(String listName) {
		boardPage.enterListTitle(listName);
	}

	@When("I click on add list submit button")
	public void i_click_on_add_list_submit_button() {
		boardPage.submitListCreation();
	}

	// needs to be updated
	@Then("the list {string} should be created successfully")
	public void listShouldBeCreated(String listName) {
		Assert.assertTrue("List creation verification failed", boardPage.isListCreated(listName));
	}

	@When("I create a list named {string} on the board")
	public void iCreateList(String listName) {
		apiResponse = apiClient.createList(boardId, listName);
		Assert.assertEquals(200, apiResponse.getStatusCode());
		String listId = apiResponse.jsonPath().getString("id");
		Assert.assertNotNull("List creation failed", listId);
	}
}