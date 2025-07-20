package com.aepl.tests;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aepl.base.TestBase;
import com.aepl.pages.ChangeMobilePage;
import com.aepl.pages.LoginPage;
import com.aepl.util.CommonMethod;
import com.aepl.util.ConfigProperties;
import com.aepl.util.ExcelUtility;

public class ChangeMobilePageTest extends TestBase {
	private LoginPage loginPage;
	private ExcelUtility excelUtility;
	private ChangeMobilePage changeMobile; 
	private CommonMethod commonMethod;

	@Override
	@BeforeClass
	public void setUp() {
		super.setUp();
		this.loginPage = new LoginPage(driver);
		this.changeMobile = new ChangeMobilePage(driver);
		this.excelUtility = new ExcelUtility();
		this.commonMethod = new CommonMethod(driver);
		excelUtility.initializeExcel("Change_Mobile_Test");
	}

	@Test(priority = 1)
	public void testLogin() {
		loginPage.enterUsername(ConfigProperties.getProperty("valid.username"))
				.enterPassword(ConfigProperties.getProperty("valid.password")).clickLogin();
	}

	@Test(priority = 2)
	public void testClickNavBar() {
		String testCaseName = "Test Navbar Links";
		String expectedURL = ConfigProperties.getProperty("dashboard.url");
		String actualURL = "";
		String result;
		logger.info("Executing the testClickNavBar");
		try {
			logger.info("Trying to click on the nav bar links");
			changeMobile.clickNavBar();
			actualURL = driver.getCurrentUrl();
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		} catch (Exception e) {
			logger.warn("Error");
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
			commonMethod.captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Successfully clicked on the Device Utility.");
	}

	@Test(priority = 3)
	public void testClickOnChangeMobile() {
		String testCaseName = "Test Change Mobile Link";
		String expectedURL = ConfigProperties.getProperty("change.mobile");
		String result = "";

		logger.info("Executing test case: {}", testCaseName);

		try {
			String actualURL = changeMobile.clickDropDownOption();
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Test case '{}' completed successfully. Expected URL: {}, Actual URL: {}", testCaseName,
					expectedURL, actualURL);
		} catch (Exception e) {
			logger.error("Error encountered in test case '{}'.", testCaseName, e);
			commonMethod.captureScreenshot(testCaseName);
			result = "FAIL";
		} finally {
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL,
					result.equals("PASS") ? expectedURL : "Error occurred", result);
		}
	}

	@Test(priority = 4)
	public void testSearchBoxFunction() {
		String testCaseName = "Testing the search box functionality";
		String iccidToSearch = "89916490634626389138"; // ICCID entered for the test case
		List<String> expectedHeaders = Arrays.asList("Lot Id", "ICCID", "Mobile 1", "Mobile 2", "Service Provider 1",
				"Service Provider 2", "Action");

		logger.info("Executing test case: {}", testCaseName);

		String result = "";
		try {
			boolean headersMatch = changeMobile.checkSearchBox(iccidToSearch, expectedHeaders);
			result = headersMatch ? "PASS" : "FAIL";
			logger.info("Search functionality executed. Headers match status: {}", headersMatch);
		} catch (Exception e) {
			logger.error("Error encountered in test case: {}", testCaseName, e);
			commonMethod.captureScreenshot(testCaseName);
			result = "FAIL";
		} finally {
			excelUtility.writeTestDataToExcel(testCaseName, String.join(", ", expectedHeaders),
					result.equals("PASS") ? "Headers Match" : "Headers Mismatch", result);
		}
	}

	@Test(priority = 5)
	public void testEyeButtons() {
		String testCaseName = "Test Eye Action Button";
		String expectedResult = "Successfully viewed item and navigated back";
		String actualResult = "";
		String result = "";

		logger.info("Executing test case: " + testCaseName);
		try {
			changeMobile.clickEyeActionButton();
			actualResult = "Successfully viewed item and navigated back";
			result = "PASS";
		} catch (Exception e) {
			actualResult = "Failed to view item and navigate back: " + e.getMessage();
			result = "FAIL";
			logger.error("Test failed: " + e.getMessage());

			commonMethod.captureScreenshot(testCaseName);
			logger.error("Screenshot captured for failure: ");
		}

		logger.info("Writing results to Excel...");
		excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
	}

	@Test(priority = 6)
	public void testDeleteButtons() {
		String testCaseName = "Test Delete Action Button";
		String expectedResult = "Alert handled successfully";
		String actualResult;
		String result;

		logger.info("Executing test case: " + testCaseName);

		try {
			if (changeMobile.clickDeleteActionButton()) {
				actualResult = "Alert handled successfully";
				result = "PASS";
			} else {
				actualResult = "Failed to handle alert or delete button";
				result = "FAIL";
			}
		} catch (Exception e) {
			actualResult = "Exception occurred: " + e.getMessage();
			result = "FAIL";
			logger.error("An error occurred during the test: " + e.getMessage());
			commonMethod.captureScreenshot(testCaseName);
			logger.info("Screenshot captured for failure: ");
		}

		logger.info("Writing results to Excel...");
		excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
	}

	@Test(priority = 7)
	public void testPaginationButtons() {
		String testCaseName = "Test Pagination Buttons";
		String expectedResult = "Successfully navigated using pagination buttons";
		String actualResult = "";
		String result = "";

		logger.info("Executing test case: " + testCaseName);
		try {
			changeMobile.checkPagination();
			actualResult = "Successfully navigated using pagination buttons";
			result = "PASS";
		} catch (Exception e) {
			actualResult = "Failed to navigate using pagination buttons: " + e.getMessage();
			result = "FAIL";
			commonMethod.captureScreenshot(testCaseName);
			logger.error("Screenshot captured for failure: ");
		}

		logger.info("Writing results to Excel...");
		excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
	}

}
