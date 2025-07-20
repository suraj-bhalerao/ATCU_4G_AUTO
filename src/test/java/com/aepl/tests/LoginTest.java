package com.aepl.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aepl.base.TestBase;
import com.aepl.pages.LoginPage;
import com.aepl.util.ConfigProperties;
import com.aepl.util.ExcelUtility;

public class LoginTest extends TestBase {
	private LoginPage loginPage;
	private ExcelUtility excelUtility;

	@Override
	@BeforeClass
	public void setUp() {
		super.setUp();
		loginPage = new LoginPage(driver);
		excelUtility = new ExcelUtility();
		excelUtility.initializeExcel("Login_Page_Test");
	}

	@Test(dataProvider = "loginData")
	public void testLogin(String username, String password, String expectedErrorMessage, String testCaseName) {
		logger.warn("Executing test case: " + testCaseName);

		loginPage.enterUsername(username).enterPassword(password).clickLogin();

		try {
			if (expectedErrorMessage.isEmpty()) {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				boolean isDashboardURL = wait.until(ExpectedConditions.urlContains("dashboard"));
				Assert.assertTrue(isDashboardURL, "Login did not redirect to dashboard.");
			} else {
				By errorLocator = getErrorLocator(expectedErrorMessage);
				WebElement errorMessage = loginPage.waitForVisibility(errorLocator);
				Assert.assertEquals(errorMessage.getText(), expectedErrorMessage, "Error message mismatch.");
			}

			excelUtility.writeTestDataToExcel(testCaseName, expectedErrorMessage, "Login success", "Pass");
		} catch (TimeoutException e) {
			logger.error("Timeout occurred waiting for dashboard or error message.", e);
//			captureScreenshot(testCaseName);
			excelUtility.writeTestDataToExcel(testCaseName, expectedErrorMessage, "Page did not load as expected",
					"Fail");
			Assert.fail("Page did not load as expected.");
		} catch (Exception e) {
			logger.error("Unexpected error in test case: " + testCaseName, e);
//			captureScreenshot(testCaseName);
			excelUtility.writeTestDataToExcel(testCaseName, expectedErrorMessage, "Unexpected error: " + e.getMessage(),
					"Fail");
			Assert.fail("Unexpected error: " + e.getMessage());
		}
	}

	private By getErrorLocator(String expectedErrorMessage) {
		if (expectedErrorMessage.equals(ConfigProperties.getProperty("email.error_msg_01"))
				|| expectedErrorMessage.equals(ConfigProperties.getProperty("email.error_msg_02"))) {
			return By.xpath("//mat-error[contains(text(), '" + expectedErrorMessage + "')]");
		} else if (expectedErrorMessage.equals(ConfigProperties.getProperty("password.error_msg_01"))
				|| expectedErrorMessage.equals(ConfigProperties.getProperty("password.error_msg_02"))) {
			return By.xpath("//mat-error[contains(text(), '" + expectedErrorMessage + "')]");
		} else if (expectedErrorMessage.equals(ConfigProperties.getProperty("toast.error_msg_01"))
				|| expectedErrorMessage.equals(ConfigProperties.getProperty("toast.error_msg_02"))) {
			return By.xpath("//span[text()='" + expectedErrorMessage + "']");
		} else {
			throw new IllegalArgumentException("Unknown error message: " + expectedErrorMessage);
		}
	}

	@DataProvider(name = "loginData", parallel = false)
	public Object[][] loginData() {
		return new Object[][] {
				// Empty user valid pass
				{ " ", ConfigProperties.getProperty("valid.password"),
						ConfigProperties.getProperty("email.error_msg_02"), "Empty Username With Valid Password" },

				// Valid user long pass
				{ ConfigProperties.getProperty("valid.username"), "a".repeat(16),
						ConfigProperties.getProperty("toast.error_msg_02"), "Valid Username With Long Password" },

				// Valid user empty pass
				{ ConfigProperties.getProperty("valid.username"), " ",
						ConfigProperties.getProperty("password.error_msg_02"), "Valid Username With Empty Password" },

				// Invalid user valid pass
				{ "invalid.email@domain.com", ConfigProperties.getProperty("valid.password"),
						ConfigProperties.getProperty("toast.error_msg_01"), "Invalid Username With Valid Password" },

				// Empty user empty pass
				{ " ", " ", ConfigProperties.getProperty("password.error_msg_02"), "Empty Username With Empty Password" },

				// Invalid user invalid pass
				{ "invalid.email@domain.com", "invalid", ConfigProperties.getProperty("toast.error_msg_01"),
						"Invalid Username With Invalid Password" },

				// Valid user short pass
				{ ConfigProperties.getProperty("valid.username"), "short",
						ConfigProperties.getProperty("password.error_msg_02"), "Valid Username With Short Password" },

				// Valid user with white space
				{ ConfigProperties.getProperty("valid.username"), "       ",
						ConfigProperties.getProperty("toast.error_msg_02"),
						"Valid Username With White Spaces in Password" },

				// Valid user with sql injection
				{ ConfigProperties.getProperty("valid.username"), "' OR '1'='1",
						ConfigProperties.getProperty("toast.error_msg_02"), "SQL Injection in Password" },

				// Valid user with xss
				{ ConfigProperties.getProperty("valid.username"), "<script>alert('XSS');</script>",
						ConfigProperties.getProperty("toast.error_msg_02"), "XSS Attempt in Password" },

				// Valid user valid pass
				{ ConfigProperties.getProperty("valid.username"), ConfigProperties.getProperty("valid.password"), "",
						"Valid Username With Valid Password" }, };
	}
}
