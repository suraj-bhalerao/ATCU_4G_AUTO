package com.aepl.actions;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LocationActions {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final Logger logger = LogManager.getLogger(LocationActions.class);

    public LocationActions(WebDriver driver) {
        if (driver == null) {
            logger.error("WebDriver instance is null. Cannot initialize LocationActions.");
            throw new IllegalArgumentException("WebDriver instance cannot be null");
        }

        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        logger.info("LocationActions instance initialized successfully.");
    }
    
    public void selectDynamicLocation(By inputLocator, By suggestionsLocator, String locationToSelect) {
        logger.info("Attempting to select location: " + locationToSelect);

        try {
            logger.info("Waiting for the input field to be clickable...");
            WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(inputLocator));
            logger.info("Input field located. Typing the first few characters of the location...");
            inputField.sendKeys(locationToSelect.substring(0, 3));

            logger.info("Waiting for location suggestions to appear...");
            List<WebElement> suggestions = wait
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(suggestionsLocator));

            logger.info("Processing location suggestions...");
            for (WebElement suggestion : suggestions) {
                logger.info("Suggestion found: " + suggestion.getText());
                if (suggestion.getText().equalsIgnoreCase(locationToSelect)) {
                    logger.info("Matching location found. Clicking on suggestion: " + locationToSelect);
                    suggestion.click();
                    logger.info("Location selected successfully: " + locationToSelect);
                    return;
                }
            }

            logger.warn("Location not found in suggestions: " + locationToSelect);
            throw new RuntimeException("Location not found in suggestions: " + locationToSelect);

        } catch (Exception e) {
            logger.error("Error occurred while selecting location: " + locationToSelect, e);
            throw e;
        }
    }
}
