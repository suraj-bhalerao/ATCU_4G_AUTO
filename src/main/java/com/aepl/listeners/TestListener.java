package com.aepl.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aepl.base.TestBase;
import com.aepl.reports.ExtentManager;
import com.aepl.reports.ExtentTestManager;
import com.aepl.util.CommonMethod;
import com.aventstack.extentreports.Status;

public class TestListener extends TestBase implements ITestListener {
	CommonMethod commonMethod;

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentTestManager.startTest(testName);
        ExtentTestManager.getTest().log(Status.INFO, "Test Started: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentTestManager.getTest().log(Status.PASS, "Test Passed: " + testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();

        ExtentTestManager.getTest().log(Status.FAIL, "Test Failed: " + testName);
        ExtentTestManager.getTest().log(Status.FAIL, "Cause: " + (throwable != null ? throwable.getMessage() : "Unknown"));

        try {
        	commonMethod.captureScreenshot(testName);
            ExtentTestManager.getTest().log(Status.FAIL, "Test Failed: " + testName);
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped: " + testName);

        try {
        	commonMethod.captureScreenshot(testName);
            ExtentTestManager.getTest().log(Status.FAIL, "Test Failed: " + testName);
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
        }
    }
    
    @Override
    public void onStart(ITestContext context) {
    	if(context.getName().isEmpty()) {
    		throw new NullPointerException();
    	}
    	
        ExtentManager.createInstance();
//        ExtentTestManager.getTest().log(Status.INFO, "Test Suite Started: " + context.getName());
        this. commonMethod = new CommonMethod(driver);
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentTestManager.getTest().log(Status.INFO, "Test Suite Finished: " + context.getName());
        ExtentManager.flush();
    }
}
