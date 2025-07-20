package com.aepl.reports;

import java.io.File;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports createInstance() {
        if (extent != null) {
            throw new IllegalStateException("ExtentReports instance already created. Use getInstance() instead.");
        }

        String filePath = System.getProperty("user.dir") + "/test-results/ExtentReport.html";

        File reportFile = new File(filePath);
        if (!reportFile.getParentFile().exists() && !reportFile.getParentFile().mkdirs()) {
            throw new IllegalStateException("Failed to create directories for report file: " + filePath);
        }

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(filePath);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Execution Report");
        sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD); 

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", System.getenv("ENVIRONMENT") != null ? System.getenv("ENVIRONMENT") : "QA");
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setAnalysisStrategy(AnalysisStrategy.TEST); 

        return extent;
    }

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    public static void flush() {
        if (extent == null) {
            throw new IllegalStateException("ExtentReports instance is null. Cannot flush reports.");
        }
        extent.flush();
    }
}
