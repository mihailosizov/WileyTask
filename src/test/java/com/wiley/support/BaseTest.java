package com.wiley.support;

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    @BeforeSuite(alwaysRun = true)
    public void setupBeforeSuite(ITestContext context) {
        Driver.initialize();
    }

    @AfterSuite(alwaysRun = true)
    public void setupAfterSuite() {
        Driver.close();
    }
}
