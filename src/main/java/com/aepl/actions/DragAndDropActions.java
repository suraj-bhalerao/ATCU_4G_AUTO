package com.aepl.actions;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DragAndDropActions {
	private final WebDriver driver;
	private final Actions actions;

	private static final Logger logger = LogManager.getLogger(DragAndDropActions.class);

	public DragAndDropActions(WebDriver driver) {
		if (driver == null) {
			throw new IllegalArgumentException("WebDriver instance cannot be null");
		}
		this.driver = driver;
		this.actions = new Actions(this.driver);
	}

	public void dragAndDrop(WebElement source, WebElement target) {
		if (source == null || target == null) {
			logger.error("Source or target element cannot be null");
			throw new IllegalArgumentException("Source or target WebElement cannot be null");
		}

		try {
			logger.info("Dragging element from '" + source.getText() + "' to '" + target.getText() + "'");
			actions.dragAndDrop(source, target).build().perform();
			logger.info("Drag-and-drop operation completed successfully.");
		} catch (Exception e) {
			logger.error("Error during drag-and-drop operation: " + e.getMessage(), e);
			throw e;
		}
	}

	public void dragAndDropByOffset(WebElement source, int xOffset, int yOffset) {
		if (source == null) {
			logger.error("Source element cannot be null");
			throw new IllegalArgumentException("Source WebElement cannot be null");
		}

		try {
			logger.info("Dragging element '" + source.getText() + "' to offset (" + xOffset + ", " + yOffset + ")");
			actions.dragAndDropBy(source, xOffset, yOffset).build().perform();
			logger.info("Drag-and-drop operation by offset completed successfully.");
		} catch (Exception e) {
			logger.error("Error during drag-and-drop by offset: " + e.getMessage(), e);
			throw e;
		}
	}
}