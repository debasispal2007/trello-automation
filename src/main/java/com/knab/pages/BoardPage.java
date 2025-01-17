package com.knab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BoardPage extends BasePage {

	// Board creation locators
	private final By createBoardButton = By.xpath("//*[@data-testid='header-create-board-button']");
	private final By boardTitleInput = By.cssSelector("[data-testid='create-board-title-input']");
	private final By createBoardSubmit = By.cssSelector("[data-testid='create-board-submit-button']");

	// Board elements locators
	private final By boardTitle = By.xpath("//*[@data-testid='board-name-container']");
	private final By addListLink = By.xpath("//*[@data-testid='list-composer-button']");
	private final By listTitleInput = By.xpath("//*[@data-testid='list-name-textarea' and @name='Enter list name…']");
	private final By addListSubmit = By.xpath("//*[@data-testid='list-composer-add-list-button']");

	private final By boardMenuButton = By.cssSelector("button[aria-label='Show menu']");
	private final By closeBoardButton = By.xpath("//span[@data-testid='RemoveIcon']/ancestor::button");
	private final By confirmCloseButton = By.xpath("//button[@data-testid='popover-close-board-confirm']");
	private final By permanentlyDeleteButton = By.cssSelector("button[data-testid='close-board-delete-board-button']");
	private final By confirmDeleteButton = By
			.cssSelector("button[data-testid='close-board-delete-board-confirm-button']");

	// Navigation locators
	private final By boardsMenu = By.xpath("//*[@data-testid='header-create-menu-button']");

	public void clickCreateBoard() {
		// First ensure we're on the boards page
		waitForElementClickable(boardsMenu);
		click(boardsMenu);
		waitForElementClickable(createBoardButton);
		click(createBoardButton);
	}

	public void enterBoardTitle(String title) {
		waitForElementVisible(boardTitleInput);
		type(boardTitleInput, title);
	}

	public void submitBoardCreation() {
		click(createBoardSubmit);
	}

	public void clickAddList() {
		click(addListLink);
	}

	public void enterListTitle(String title) {
		waitForElementVisible(listTitleInput);
		type(listTitleInput, title);
	}

	public void submitListCreation() {
		click(addListSubmit);
	}

	public boolean isBoardCreated(String expectedTitle) {
		try {
			waitForElementVisible(boardTitle);
			return getText(boardTitle).equals(expectedTitle);
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isListCreated(String listName) {
		By listLocator = By.xpath(String.format("//h2[@data-testid='list-name' and text()='%s']", listName));
		try {
			return waitForElementVisible(listLocator).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void waitForBoardPage() {
		wait.until(ExpectedConditions.or(ExpectedConditions.urlContains("boards"),
				ExpectedConditions.presenceOfElementLocated(boardTitle)));
	}

	public void createBoard(String boardName) {
		clickCreateBoard();
		enterBoardTitle(boardName);
		submitBoardCreation();
		waitForBoardPage();
	}

	public void createList(String listName) {
		clickAddList();
		enterListTitle(listName);
		submitListCreation();
	}

	public void deleteBoard() {
		click(boardMenuButton);
		click(closeBoardButton);
		click(confirmCloseButton);
		click(permanentlyDeleteButton);
		click(confirmDeleteButton);
	}
}