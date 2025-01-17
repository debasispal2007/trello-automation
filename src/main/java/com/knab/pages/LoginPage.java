package com.knab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;

public class LoginPage extends BasePage {
    
    private final By loginLink = By.linkText("Log in");
    private final By emailInput = By.id("username");
    private final By continueButton = By.id("login-submit");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-submit");
    private final By boardsLink = By.cssSelector("[data-testid='home-team-boards-tab']");

    public void clickLoginLink() {
        try {
            waitForElementClickable(loginLink);
            click(loginLink);
        } catch (TimeoutException e) {
            throw new RuntimeException("Login link not clickable or login container not visible", e);
        }
    }
    
    public void enterEmail(String email) {
        try {
            waitForElementVisible(emailInput);
            type(emailInput, email);
            waitForElementClickable(continueButton);
            click(continueButton);
        } catch (TimeoutException e) {
            throw new RuntimeException("Email input field not visible or continue button not clickable", e);
        }
    }
    
    public void enterPassword(String password) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
            type(passwordInput, password);
            waitForElementClickable(loginButton);
        } catch (TimeoutException e) {
            throw new RuntimeException("Password input field not visible", e);
        }
    }
    
    public void clickLoginButton() {
        try {
            click(loginButton);
        } catch (TimeoutException e) {
            throw new RuntimeException("Login button not clickable", e);
        }
    }
    
    public boolean isLoggedIn() {
        try {
            return waitForElementVisible(boardsLink).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    public void loginToTrello(String email, String password) {
        try {
            clickLoginLink();
            enterEmail(email);
            enterPassword(password);
            clickLoginButton();
            wait.until(ExpectedConditions.visibilityOfElementLocated(boardsLink));
        } catch (Exception e) {
            throw new RuntimeException("Failed to login to Trello: " + e.getMessage(), e);
        }
    }
}