package com.aepl.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {
	private static final Logger logger = LogManager.getLogger(WebDriverFactory.class);

	public static WebDriver getWebDriver(String browserName) {
		WebDriver driver;
		switch (browserName.toLowerCase()) {
		case "chrome":
			driver = getChromeDriver();
			break;
		case "firefox":
			driver = getFirefoxDriver();
			break;
		case "edge":
			driver = getEdgeDriver();
		case "brave":
			driver = getBraveDriver();
		default:
			throw new IllegalArgumentException("Browser not supported: " + browserName);
		}
		logger.info(browserName + " WebDriver initialized.");
		return driver;
	}

	private static WebDriver getChromeDriver() {
		String specificVersion = "133.0.6943.54";
		try {
			ChromeOptions options = new ChromeOptions();

			options.addArguments("--allow-running-insecure-content");
			options.addArguments("--safebrowsing-disable-download-protection");
			options.addArguments("--disable-popup-blocking");

			Map<String, Object> prefs = new HashMap<>();
			prefs.put("download.default_directory", "C:\\Users\\Suraj Bhaleroa\\Downloads");
			prefs.put("download.prompt_for_download", false);
			prefs.put("download.directory_upgrade", true);
			prefs.put("safebrowsing.enabled", true);
			prefs.put("profile.default_content_settings.popups", 0);

			options.setExperimentalOption("prefs", prefs);

			WebDriverManager.chromedriver().driverVersion(specificVersion).setup();
			logger.info("Setting up ChromeDriver version: " + specificVersion);

			return new ChromeDriver(options);

		} catch (Exception e) {
			logger.error("Error initializing ChromeDriver version: " + specificVersion, e);
			throw new RuntimeException("Failed to initialize ChromeDriver version: " + specificVersion, e);
		}
	}

	private static WebDriver getFirefoxDriver() {
		WebDriverManager.firefoxdriver().setup();
		return new FirefoxDriver();
	}

	private static WebDriver getEdgeDriver() {
		WebDriverManager.edgedriver().setup();
		return new EdgeDriver();
	}

	private static WebDriver getBraveDriver() {
		try {
			WebDriverManager.chromedriver().setup();
			logger.info("Setting up Brave Browser WebDriver.");

			String braveExecutablePath = "C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe";

			ChromeOptions options = new ChromeOptions();
			options.setBinary(braveExecutablePath);
			options.addArguments("--remote-allow-origins=*");
			options.addArguments("--start-maximized");

			return new ChromeDriver(options);
		} catch (Exception e) {
			logger.error("Error initializing Brave WebDriver", e);
			throw new RuntimeException("Failed to initialize Brave WebDriver.", e);
		}
	}

}
