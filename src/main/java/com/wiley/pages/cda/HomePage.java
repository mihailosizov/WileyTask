package com.wiley.pages.cda;

import com.wiley.pages.edservices.EdServicesHomePage;
import com.wiley.support.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.TestException;

import java.util.List;

import static com.wiley.utils.Common.*;

public class HomePage extends BaseCdaPage {
    public static final String URL = "http://www.wiley.com/WileyCDA/";

    private static final String SIGN_UP_TO_WILEY_INPUT_FIELD_XPATH = "//div[@class='portletBox portletBoxGrey homepage-sign-up-form-container']";

    private WebDriver driver;

    @FindAll(@FindBy(xpath = "//*[@id='homepage-links']/ul/li/a[@href]"))
    @CacheLookup
    private List<WebElement> resourcesMenuLinks;

    @FindAll(@FindBy(xpath = "//*[@id='homepage-links']/ul/li[@class]"))
    @CacheLookup
    private List<WebElement> resourcesMenuItems;

    @FindBy(xpath = SIGN_UP_TO_WILEY_INPUT_FIELD_XPATH + "//input[@id='EmailAddress']")
    @CacheLookup
    private WebElement signUpToWileyEmailInputField;

    @FindBy(xpath = SIGN_UP_TO_WILEY_INPUT_FIELD_XPATH + "//button[@name='submitButton']")
    @CacheLookup
    private WebElement signUpToWileyEmailSubmitButton;

    public HomePage() {
        this.driver = Driver.getInstance();
        PageFactory.initElements(driver, this);
    }

    public static HomePage openHomePage() {
        Driver.getInstance().navigate().to(URL);
        return new HomePage();
    }

    public List<WebElement> getResourcesMenuItems() {
        return resourcesMenuItems;
    }

    public List<WebElement> getResourcesMenuLinks() {
        return resourcesMenuLinks;
    }

    public List<String> getResourcesMenuLinksText() {
        return getTextFor(resourcesMenuLinks);
    }

    public StudentsPage clickStudentsResourceLink() {
        if (clickLinkFromCollection(resourcesMenuLinks, "Students"))
            return new StudentsPage();
        else throw new TestException("Unable to open web page");
    }

    public EdServicesHomePage clickInstitutionsResourceLink() {
        if (clickLinkFromCollection(resourcesMenuLinks, "Institutions"))
            return new EdServicesHomePage();
        else throw new TestException("Unable to open web page");
    }

    public void clickSignUpToWileySubmitButton() {
        signUpToWileyEmailSubmitButton.click();
    }

    public void inputTextToSignUpToWiley(String inputText) {
        signUpToWileyEmailInputField.sendKeys(inputText);
    }
}
