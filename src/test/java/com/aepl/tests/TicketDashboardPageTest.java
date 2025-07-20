package com.aepl.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aepl.base.TestBase;
import com.aepl.pages.LoginPage;
import com.aepl.pages.TicketDashboardPage;
import com.aepl.util.ConfigProperties;
import com.aepl.util.ExcelUtility;

public class TicketDashboardPageTest extends TestBase {
    private LoginPage loginPage;
    private ExcelUtility excelUtility;
    private TicketDashboardPage ticketDashboard;

    @Override
    @BeforeClass
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driver);
        ticketDashboard = new TicketDashboardPage(driver);
        excelUtility = new ExcelUtility();
        excelUtility.initializeExcel("Ticket_Dash_Test");
    }

    @Test(priority = 1)
    public void login() {
        loginPage.enterUsername(ConfigProperties.getProperty("valid.username"))
                .enterPassword(ConfigProperties.getProperty("valid.password")).clickLogin();
    }
}
