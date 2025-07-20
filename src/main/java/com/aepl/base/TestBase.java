package com.aepl.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.aepl.util.ConfigProperties;
import com.aepl.util.WebDriverFactory;

public class TestBase {

	protected WebDriver driver;
	protected final Logger logger = LogManager.getLogger(TestBase.class);

	@BeforeMethod
	public void zoomChrome() {
		if (driver != null) {
			((JavascriptExecutor) driver).executeScript("document.body.style.zoom='75%'");
		}
	}

	@BeforeClass
	public void setUp() {
		ConfigProperties.initialize("qa");
		String browserType = ConfigProperties.getProperty("browser.type");
		logger.info("Setting up WebDriver for " + browserType + " browser.");
		driver = WebDriverFactory.getWebDriver(browserType);
		driver.manage().window().maximize();
		driver.get(ConfigProperties.getProperty("base.url"));
		logger.info("Navigated to: " + ConfigProperties.getProperty("base.url"));
	}

//	@AfterClass
	public void tearDown() {
		if (driver != null) {
			logger.info("Closing the browser after all test classes and suite.");
			driver.quit();
		} else {
			logger.warn("Driver was null; no browser to close.");
		}
	}
}
