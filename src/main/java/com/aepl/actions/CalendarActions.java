//package com.aepl.actions;
//
//import java.time.Duration;
//
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class CalendarActions {
//	private final WebDriver driver;
//	private final WebDriverWait wait;
//
//	private static final Logger logger = LogManager.getLogger(CalendarActions.class);
//
//	public CalendarActions(WebDriver driver) {
//	    if (driver == null) {
//	        throw new IllegalArgumentException("WebDriver instance cannot be null");
//	    }
//	    this.driver = driver;
//	    this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
//	}
//	
//	public void selectDate(By calendarLocator, String targetDate) {
//	    if (calendarLocator == null || targetDate == null || targetDate.isEmpty()) {
//	        logger.error("Invalid calendarLocator or targetDate provided. Both must be non-null and targetDate must be non-empty.");
//	        throw new IllegalArgumentException("calendarLocator and targetDate cannot be null or empty");
//	    }
//
//	    logger.info("Attempting to select date: " + targetDate);
//
//	    try {
//	        logger.info("Waiting for the calendar element to become clickable...");
//	        WebElement calendarElement = wait.until(ExpectedConditions.elementToBeClickable(calendarLocator));
//	        logger.info("Calendar element found. Clicking to open the date picker.");
//	        calendarElement.click();
//
//	        logger.info("Parsing target date: " + targetDate);
//	        String[] dateParts = targetDate.split("-");
//	        String day = dateParts[0];
//	        String month = dateParts[1];
//	        String year = dateParts[2];
//	        logger.info("Parsed date: Day=" + day + ", Month=" + month + ", Year=" + year);
//
//	        while (true) {
//	            logger.info("Checking displayed month and year...");
//	            WebElement displayedMonthYearElement = driver.findElement(By.cssSelector("selector-for-month-year"));
//	            String displayedMonthYear = displayedMonthYearElement.getText();
//	            logger.info("Displayed month and year: " + displayedMonthYear);
//
//	            if (displayedMonthYear.contains(month) && displayedMonthYear.contains(year)) {
//	                logger.info("Target month and year found: " + displayedMonthYear);
//	                break;
//	            }
//
//	            logger.info("Target month and year not found. Clicking next to navigate...");
//	            driver.findElement(By.cssSelector("selector-for-next-month")).click();
//	        }
//
//	        By dayLocator = By.xpath("//td[text()='" + day + "']");
//	        logger.info("Waiting for the target day (" + day + ") to become clickable...");
//	        WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(dayLocator));
//	        logger.info("Target day found. Clicking to select.");
//	        dayElement.click();
//
//	        logger.info("Date selection completed successfully: " + targetDate);
//
//	    } catch (Exception e) {
//	        logger.error("Error occurred while selecting date: " + targetDate, e);
//	        throw e; 
//	    }
//	}
//}
//

package com.aepl.actions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CalendarActions {
	private final WebDriver driver;
	private final WebDriverWait wait;
	private static final Logger logger = LogManager.getLogger(CalendarActions.class);

	// Constructor
	public CalendarActions(WebDriver driver) {
		if (driver == null) {
			throw new IllegalArgumentException("WebDriver instance cannot be null");
		}
		this.driver = driver;
		this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
	}

	public void selectDate(By calendarLocator, String targetDate) {
		if (calendarLocator == null || targetDate == null || targetDate.isEmpty()) {
			logger.error(
					"Invalid calendarLocator or targetDate. Both must be non-null and targetDate must be non-empty.");
			throw new IllegalArgumentException("calendarLocator and targetDate cannot be null or empty");
		}

		logger.info("Attempting to select date: " + targetDate);

		try {
			WebElement calendarElement = wait.until(ExpectedConditions.elementToBeClickable(calendarLocator));
			calendarElement.click();
			logger.info("Calendar opened successfully.");

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate targetLocalDate = LocalDate.parse(targetDate, formatter);
			String targetDay = String.valueOf(targetLocalDate.getDayOfMonth());
			String targetMonth = targetLocalDate.getMonth().name().substring(0, 3).toLowerCase(); // First 3 letters in
																									// lowercase
			String targetYear = String.valueOf(targetLocalDate.getYear());

			System.out.println(
					"Target date details: Day=" + targetDay + ", Month=" + targetMonth + ", Year=" + targetYear);

			// Open year selection dropdown
			WebElement dropdown = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]")));
			dropdown.click();

			// Select year
			WebElement yearElement = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), '" + targetYear + "')]")));
			yearElement.click();

			// Select month
			WebElement monthElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//div[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
							+ targetMonth + "')]")));
			monthElement.click();

			// Select day
			WebElement dayElement = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), '" + targetDay
							+ "') and contains(@class, 'mat-calendar-body-cell-content')]")));
			dayElement.click();
		} catch (Exception e) {
			logger.error("Error selecting date: " + targetDate, e);
			throw new RuntimeException("Failed to select date: " + targetDate, e);
		}
	}

	// To select range of dates
	public void selectDateRange(By fromDateLocator, String fromDate, By toDateLocator, String toDate) {
		logger.info("Selecting date range: From " + fromDate + " to " + toDate);
		selectDate(fromDateLocator, fromDate);
		selectDate(toDateLocator, toDate);
		logger.info("Date range selection completed.");
	}
}
