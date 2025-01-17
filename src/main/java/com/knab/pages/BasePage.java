package com.knab.pages;

import com.knab.hooks.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;

	public BasePage() {
		this.driver = Hooks.getDriver();
		if (this.driver != null) {
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		}
	}

	protected WebElement waitForElementVisible(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return driver.findElement(locator);
		} catch (Exception e) {
			System.out.println("Failed to find element with locator: " + locator);
			throw e;
		}
	}

	protected WebElement waitForElementClickable(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	protected void click(By locator) {
		waitForElementClickable(locator).click();
	}

	protected void type(By locator, String text) {
		waitForElementVisible(locator).sendKeys(text);
	}

	protected String getText(By locator) {
		return waitForElementVisible(locator).getText();
	}

	protected boolean isElementDisplayed(By locator) {
		try {
			return waitForElementVisible(locator).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}