package com.aepl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	private Workbook workbook;
	private Sheet sheet;
	private String filePath;

	public void initializeExcel(String sheetName) {
		filePath = System.getProperty("user.dir") + "/test-results/" + sheetName + ".xlsx";
		try {
			File file = new File(filePath);

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			if (file.exists() && file.length() > 0) {
				try (FileInputStream fis = new FileInputStream(file)) {
					workbook = new XSSFWorkbook(fis);
					sheet = workbook.getSheetAt(0);
				}
			} else {
				workbook = new XSSFWorkbook();
				sheet = workbook.createSheet("Test Results");
				createHeaderRow();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to initialize Excel file: " + filePath);
		}
	}

	private void createHeaderRow() {
		Row headerRow = sheet.createRow(0);
		CellStyle headerStyle = workbook.createCellStyle();
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		headerStyle.setFont(headerFont);

		String[] headers = { "Test Case Name", "Expected Message", "Actual Message", "Status" };
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerStyle);
		}
	}

	public void writeTestDataToExcel(String testCaseName, String expected, String actual, String status) {
		if (workbook == null || sheet == null) {
			throw new IllegalStateException("Excel file is not initialized. Call initializeExcel() first.");
		}

		try {
			int rowCount = sheet.getPhysicalNumberOfRows();
			Row row = sheet.createRow(rowCount);

			row.createCell(0).setCellValue(testCaseName);
			row.createCell(1).setCellValue(expected);
			row.createCell(2).setCellValue(actual);

			Cell statusCell = row.createCell(3);
			statusCell.setCellValue(status);

			CellStyle statusStyle = workbook.createCellStyle();
			if ("Fail".equalsIgnoreCase(status)) {
				Font failFont = workbook.createFont();
				failFont.setColor(IndexedColors.RED.getIndex());
				statusStyle.setFont(failFont);
			}
			statusCell.setCellStyle(statusStyle);

			try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
				workbook.write(fileOut);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to write data to Excel file.");
		}
	}
}
