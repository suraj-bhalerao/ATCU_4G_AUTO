//package com.aepl.tests;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.TimeoutException;
//import org.openqa.selenium.WebElement;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import com.aepl.base.TestBase;
//import com.aepl.constants.Constants;
//import com.aepl.pages.LoginPage;
//import com.aepl.util.ConfigProperties;
//import com.aepl.util.ExcelUtility;
//
//public class LoginPageTest extends TestBase {
//	private ExcelUtility excelUtility;
//	private LoginPage loginPage;
//
//	@BeforeMethod
//	public void initialization() {
//		excelUtility = new ExcelUtility();
//		loginPage = new LoginPage(driver);
//	}
//
//	@DataProvider(name = "loginData")
//	public Object[][] loginData() {
//		return new Object[][] { { "", Constants.VALID_PASSWORD, Constants.EMAIL_ERROR_MESSAGE[0] },
//				{ Constants.VALID_USERNAME, "", Constants.PASSWORD_ERROR_MESSAGE[0] },
//				{ "invalid.email@domain.com", Constants.VALID_PASSWORD, Constants.TOAST_ERROR_MESSAGE[0] },
//				{ Constants.VALID_USERNAME, "invalidpassword", "login Failed due to Incorrect email or password" } };
//	}
//
//	@Test(priority = 1)
//	public void testChromeInstance() {
//		String testCaseName = "Chrome Instance Launched";
//
//		try {
//			String actualTitle = driver.getTitle();
//			Assert.assertEquals(actualTitle, ConfigProperties.getProperty("expected.title"),
//					"Page title does not match expected value.");
//			excelUtility.writeTestDataToExcel(testCaseName, ConfigProperties.getProperty("expected.title"), actualTitle,
//					"Pass");
//		} catch (Exception e) {
//			excelUtility.writeTestDataToExcel(testCaseName, ConfigProperties.getProperty("expected.title"),
//					driver.getTitle(), "Fail");
//		}
//	}
//
//	@Test(priority = 2)
//	public void testEmptyUsernameValidPassword() {
//		String testCaseName = "Empty Username With Valid Password";
//
//		loginPage.enterUsername("").enterPassword(ConfigProperties.getProperty("valid.password")).clickLogin();
//		try {
//			WebElement errorMessage = loginPage.waitForVisibility(By.id("mat-error-0"));
//
//			Assert.assertEquals(errorMessage.getText(), ConfigProperties.getProperty("email.error.message".),
//					"Error message mismatch for empty username with valid password");
//			excelUtility.writeTestDataToExcel(testCaseName, Constants.EMAIL_ERROR_MESSAGE[0], errorMessage.getText(),
//					"Pass");
//		} catch (TimeoutException e) {
//			excelUtility.writeTestDataToExcel(testCaseName, Constants.EMAIL_ERROR_MESSAGE[0], "Error message not found",
//					"Fail");
//			Assert.fail("Error message for empty username was not displayed within timeout.");
//		}
//	}
//
//	@Test(priority = 3)
//	public void testValidUsernameLongPassword() {
//		String testCaseName = "Valid Username With Long Password";
//
//		String longPassword = "a".repeat(16);
//
//		loginPage.enterUsername(Constants.VALID_USERNAME).enterPassword(longPassword).clickLogin();
//
//		try {
//			WebElement errorMessage = loginPage
//					.waitForVisibility(By.xpath("//span[text()='login Failed due to Incorrect email or password']"));
//			Assert.assertNotNull(errorMessage, "Error message should be displayed for invalid credentials");
//			Assert.assertEquals(errorMessage.getText(), Constants.TOAST_ERROR_MESSAGE[1],
//					"Expected login failure message not displayed");
//
//			excelUtility.writeTestDataToExcel(testCaseName, "Error or secure handling of input", errorMessage.getText(),
//					"Pass");
//		} catch (TimeoutException e) {
//			excelUtility.writeTestDataToExcel(testCaseName, "Expected error message for long password",
//					"Error message not found", "Fail");
//			Assert.fail("Error message for long password was not displayed within timeout.");
//		}
//	}
//
//	@Test(priority = 4)
//	public void testInvalidUsernameValidPassword() {
//		String testCaseName = "Invalid Username With Valid Password";
//
//		loginPage.enterUsername("invalid.email@domain.com").enterPassword(Constants.VALID_PASSWORD).clickLogin();
//
//		WebElement errorMessage = loginPage.waitForVisibility(By.xpath("//span[text()='User Not Found']"));
//
//		Assert.assertEquals(errorMessage.getText(), Constants.TOAST_ERROR_MESSAGE[0], "Error message mismatch");
//
//		excelUtility.writeTestDataToExcel(testCaseName, Constants.TOAST_ERROR_MESSAGE[0], errorMessage.getText(),
//				"Pass");
//	}
//
////	@Test(priority = 5)
////	public void testValidUsernameEmptyPassword() {
////		String testCaseName = "Valid Username With Empty Password";
////		loginPage.clearFields();
////		loginPage.enterPassword(Constants.VALID_USERNAME).enterPassword("").clickLogin();
////
////		WebElement errorMessage = loginPage.findElementWithWait(By.xpath("//span[text()='User Not Found']"));
////		System.out.println(errorMessage);
////
////		Assert.assertEquals(errorMessage.getText(), Constants.TOAST_ERROR_MESSAGE[0], "Error message mismatch");
////		excelUtility.writeTestDataToExcel(testCaseName, Constants.TOAST_ERROR_MESSAGE[0], errorMessage.getText(),
////				"Pass");
////	}
////
////	@Test(priority = 6)
////	public void testEmptyUsernameEmptyPassword() {
////		String testCaseName = "Empty Username With Empty Password";
////		loginPage.clearFields();
////		loginPage.enterPassword("").enterPassword("").clickLogin();
////
////		WebElement emailError = loginPage.findElementWithWait(By.id("mat-error-1"));
////		WebElement passwordError = loginPage.findElementWithWait(By.id("mat-error-2"));
////		try {
////
////			Assert.assertEquals(emailError.getText(), Constants.EMAIL_ERROR_MESSAGE[0], "Email error message mismatch");
////			Assert.assertEquals(passwordError.getText(), Constants.PASSWORD_ERROR_MESSAGE[0],
////					"Password error message mismatch");
////
////			excelUtility.writeTestDataToExcel(testCaseName,
////					Constants.EMAIL_ERROR_MESSAGE[0] + " and " + Constants.PASSWORD_ERROR_MESSAGE[0],
////					emailError.getText() + " and " + passwordError.getText(), "Pass");
////		} catch (Exception e) {
////			excelUtility.writeTestDataToExcel(testCaseName,
////					Constants.EMAIL_ERROR_MESSAGE[0] + " and " + Constants.PASSWORD_ERROR_MESSAGE[0],
////					emailError.getText() + " and " + passwordError.getText(), "Fail");
////			Assert.fail("Error message not displayed within timeout");
////		}
////	}
//
////	@Test(priority = 7)
////	public void testInvalidUsernameInvalidPassword() {
////		String testCaseName = "Invalid Username With Invalid Password";
////		login("invalid.email@domain.com", "wrongpassword", testCaseName);
////
////		WebElement errorMessage = findElementWithWait(By.id("mat-error-0"));
////		Assert.assertEquals(errorMessage.getText(), "Invalid credentials", "Error message mismatch");
////		excelUtility.writeTestDataToExcel(testCaseName, "Invalid credentials", errorMessage.getText(), "Pass");
////	}
////
////	@Test(priority = 8)
////	public void testValidUsernameShortPassword() {
////		String testCaseName = "Valid Username With Short Password";
////		login(Constants.VALID_USERNAME, "short", testCaseName);
////
////		WebElement errorMessage = findElementWithWait(By.id("mat-error-1"));
////		Assert.assertEquals(errorMessage.getText(), Constants.PASSWORD_ERROR_MESSAGE[1], "Error message mismatch");
////		excelUtility.writeTestDataToExcel(testCaseName, Constants.PASSWORD_ERROR_MESSAGE[1], errorMessage.getText(),
////				"Pass");
////	}
////
////	@Test(priority = 9)
////	public void testSQLInjectionInPassword() {
////		String testCaseName = "Valid Username With SQL Injection Password";
////		String sqlInjection = "' OR '1'='1";
////		login(Constants.VALID_USERNAME, sqlInjection, testCaseName);
////
////		WebElement errorMessage = findElementWithWait(By.id("mat-error-0"));
////		Assert.assertNotEquals(driver.getCurrentUrl(), Constants.DASHBOARD_URL, "SQL injection should not allow login");
////
////		String actualErrorMessage = errorMessage != null ? errorMessage.getText() : "No error message displayed";
////		excelUtility.writeTestDataToExcel(testCaseName, "Error or secure handling of input", actualErrorMessage,
////				"Pass");
////	}
////
////	@Test(priority = 10)
////	public void testXSSInPassword() {
////		String testCaseName = "Valid Username With XSS Attempt Password";
////		String xssAttack = "<script>alert('XSS');</script>";
////		login(Constants.VALID_USERNAME, xssAttack, testCaseName);
////
////		WebElement errorMessage = findElementWithWait(By.id("mat-error-0"));
////		Assert.assertNotEquals(driver.getCurrentUrl(), Constants.DASHBOARD_URL, "XSS attempt should not allow login");
////
////		String actualErrorMessage = errorMessage != null ? errorMessage.getText() : "No error message displayed";
////		excelUtility.writeTestDataToExcel(testCaseName, "Error or secure handling of input", actualErrorMessage,
////				"Pass");
////	}
////
////	@Test(priority = 11)
////	public void testWhiteSpacesInPassword() {
////		String testCaseName = "Valid Username With White Spaces Password";
////		login(Constants.VALID_USERNAME, "      ", testCaseName);
////
////		WebElement errorMessage = findElementWithWait(By.id("mat-error-1"));
////		Assert.assertEquals(errorMessage.getText(), Constants.PASSWORD_ERROR_MESSAGE, "Error message mismatch");
////		excelUtility.writeTestDataToExcel(testCaseName, Constants.PASSWORD_ERROR_MESSAGE, errorMessage.getText(),
////				"Pass");
////	}
////
////	@Test(priority = 12)
////	public void testValidUsernameValidPassword() {
////		String testCaseName = "Valid Username With Valid Password";
////
////		login(Constants.VALID_USERNAME, Constants.VALID_PASSWORD, testCaseName);
////
////		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
////		boolean actDashboardUrl = wait.until(ExpectedConditions.urlContains(Constants.DASHBOARD_URL));
////		System.out.println(actDashboardUrl);
////		String result = actDashboardUrl ? "Pass" : "Fail";
////
////		excelUtility.writeTestDataToExcel(testCaseName, "Login should be successful", driver.getCurrentUrl(), result);
////		Assert.assertTrue(loginSuccess, "Login failed with valid credentials");
////	}
////
////	@Test(priority = 13)
////	public void testRememberMeFunctionality() {
////		String testCaseName = "Valid Username and Password with Remember Me Checked";
////		loginWithRememberMe(Constants.VALID_USERNAME, Constants.VALID_PASSWORD, testCaseName);
////
////		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
////		boolean loginSuccess = wait.until(ExpectedConditions.urlContains(Constants.DASHBOARD_URL));
////		String result = loginSuccess ? "Pass" : "Fail";
////
////		excelUtility.writeTestDataToExcel(testCaseName, "Login should be successful", driver.getCurrentUrl(), result);
////		Assert.assertTrue(loginSuccess, "Login failed with Remember Me checked");
////	}
////
////	private void loginWithRememberMe(String username, String password, String testCaseName) {
////		try {
////			WebElement emailField = findElementWithWait(By.cssSelector("input[formcontrolname='email']"));
////			WebElement passwordField = findElementWithWait(By.cssSelector("input[formcontrolname='password']"));
////			WebElement rememberMeCheckbox = findElementWithWait(By.cssSelector("input[formcontrolname='rememberMe']"));
////			WebElement signInButton = findElementWithWait(By.cssSelector("button[type='button']"));
////
////			emailField.sendKeys(username);
////			passwordField.sendKeys(password);
////			rememberMeCheckbox.click();
////			signInButton.click();
////		} catch (Exception e) {
////			excelUtility.writeTestDataToExcel(testCaseName, "Login should be successful",
////					"Exception: " + e.getMessage(), "Fail");
////			Assert.fail("Exception occurred during login: " + e.getMessage());
////		}
////	}
////
////	private void login(String username, String password, String testCaseName) {
////		try {
////			WebElement emailField = findElementWithWait(By.cssSelector("input[formcontrolname='email']"));
////			WebElement passwordField = findElementWithWait(By.cssSelector("input[formcontrolname='password']"));
////			WebElement signInButton = findElementWithWait(By.cssSelector("button[type='button']"));
////
////			emailField.sendKeys(username);
////			passwordField.sendKeys(password);
////			signInButton.click();
////		} catch (Exception e) {
////			excelUtility.writeTestDataToExcel(testCaseName, "Login should be successful",
////					"Exception: " + e.getMessage(), "Fail");
////			Assert.fail("Exception occurred during login: " + e.getMessage());
////		}
////	}
////
//}
