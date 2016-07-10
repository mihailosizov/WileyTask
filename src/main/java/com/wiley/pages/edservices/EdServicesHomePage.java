package com.wiley.pages.edservices;

import com.wiley.support.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EdServicesHomePage {
    public static final String URL = "https://edservices.wiley.com/";

    private WebDriver driver;

    @FindBy(xpath = "//div[@id='header_main']//img[@alt='Wiley Education Services']")
    @CacheLookup
    private WebElement pageHeader;

    public EdServicesHomePage() {
        this.driver = Driver.getInstance();
        PageFactory.initElements(driver, this);
    }

    public WebElement getPageHeader() {
        return pageHeader;
    }
}
