package com.aepl.pages;

import java.time.Duration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aepl.util.CommonMethod;

public class TicketDashboardPage {
	// Global Variable and instances
	private final WebDriver driver;
	private final WebDriverWait wait;
	@SuppressWarnings("unused")
	private final CommonMethod commmethod;
	private static final Logger logger = LogManager.getLogger(TicketDashboardPage.class);

	// Constructor
	public TicketDashboardPage(WebDriver driver) {
		super();
		this.driver = driver;
		this.commmethod = new CommonMethod(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	// Locators goes here
	private final By navBarLink = By.xpath("//span[@class=\"headers_custom color_3D5772\"]");
	private final By ticketDash = By.xpath("//a[@class='dropdown-item ng-star-inserted'][3]");

	// Methods goes here
}
