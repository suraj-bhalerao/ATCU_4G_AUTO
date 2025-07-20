package com.aepl.pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private final WebDriver driver;
	private static final Logger logger = LogManager.getLogger(LoginPage.class);

	// Locators
	private final By usernameField = By.cssSelector("input[formcontrolname='email']");
	private final By passwordField = By.cssSelector("input[formcontrolname='password']");
	private final By loginButton = By.cssSelector("button[type='button']");
	private final By errorMessageLocator = By.tagName("mat-error");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public LoginPage enterUsername(String username) {
		WebElement usernameInput = waitForVisibility(usernameField);
		usernameInput.clear();
		usernameInput.sendKeys(username);
		logger.info("Entered username: " + username);
		return this;
	}

	public LoginPage enterPassword(String password) {
		WebElement passwordInput = waitForVisibility(passwordField);
		passwordInput.clear();
		passwordInput.sendKeys(password);
		logger.info("Entered password: " + password);
		return this;
	}

	public LoginPage clickLogin() {
		waitForVisibility(loginButton).click();
		logger.info("Clicked Login button.");
		return this;
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public WebElement waitForVisibility(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public String getErrorMessage() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator));
		return errorMessage.getText();
	}
}
