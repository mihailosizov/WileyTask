package com.wiley.pages.cda;

import com.wiley.support.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.TestException;

import java.util.List;

import static com.wiley.utils.Common.clickLinkFromCollection;
import static com.wiley.utils.Common.getTextFor;

public abstract class BaseCdaPage {
    private WebDriver driver;

    @FindAll(@FindBy(xpath = "//*[@id='links-site']/ul/li/a[@href]"))
    @CacheLookup
    protected List<WebElement> topNavigationMenuLinks;

    @FindBy(xpath = "//form[@class='search-form']//input[@for='search']")
    @CacheLookup
    private WebElement searchField;

    @FindBy(xpath = "//form[@class='search-form']//input[@type='submit']")
    @CacheLookup
    private WebElement searchSubmitButton;

    protected BaseCdaPage() {
        this.driver = Driver.getInstance();
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> getTopNavigationMenuLinks() {
        return topNavigationMenuLinks;
    }

    public List<String> getTopNavigationMenuLinksText() {
        return getTextFor(topNavigationMenuLinks);
    }

    public HomePage topNavMenuClickHome() {
        if (clickLinkFromCollection(topNavigationMenuLinks, "Home"))
            return new HomePage();
        else throw new TestException("Unable to open web page");
    }

    public void inputTextToSearchField(String textToSearch) {
        searchField.sendKeys(textToSearch);
    }

    public SearchResultsPage submitSearch() {
        searchSubmitButton.click();
        return new SearchResultsPage();
    }
}
