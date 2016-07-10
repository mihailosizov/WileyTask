package com.wiley.pages.cda;

import com.wiley.support.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.wiley.support.Log.LOGGER;
import static com.wiley.utils.Common.*;

public class StudentsPage extends BaseCdaPage {
    public static final String URL = "http://www.wiley.com/WileyCDA/Section/id-404702.html";

    private static final String RESOURCES_FOR_MENU_ITEM_XPATH = "//div[@id='sidebar']/div[@class='autonav']/ul/li/ul[@class='autonavLevel1']/li[@class]";

    private static WebDriver driver;

    @FindBy(xpath = "//div[@id='page-title']/h1[text()='Students']")
    @CacheLookup
    private WebElement pageHeader;

    @FindAll(@FindBy(xpath = RESOURCES_FOR_MENU_ITEM_XPATH))
    private List<WebElement> resourcesForMenuItems;

    @FindAll(@FindBy(xpath = RESOURCES_FOR_MENU_ITEM_XPATH + "//a[@href]"))
    private List<WebElement> resourcesForMenuAllActiveLinks;

    @FindAll(@FindBy(xpath = RESOURCES_FOR_MENU_ITEM_XPATH + "/*[1]"))
    private List<WebElement> resourcesForMenuFirstLevelText;

    @FindBy(xpath = RESOURCES_FOR_MENU_ITEM_XPATH + "/span")
    private WebElement resourcesForMenuSelectedItem;

    public StudentsPage() {
        this.driver = Driver.getInstance();
        PageFactory.initElements(driver, this);
    }

    public WebElement getPageHeader() {
        return pageHeader;
    }

    public List<WebElement> getResourcesForMenuItems() {
        return resourcesForMenuItems;
    }

    public List<WebElement> getResourcesForMenuAllActiveLinks() {
        return resourcesForMenuAllActiveLinks;
    }

    public List<String> getResourcesForMenuFirstLevelTexts() {
        return getTextFor(resourcesForMenuFirstLevelText);
    }

    public WebElement getResourcesForMenuSelectedItem() {
        return resourcesForMenuSelectedItem;
    }

    public WebElement getResourcesForMenuItemWithText(String itemText) {
        for (WebElement element : resourcesForMenuItems) {
            if (element.findElement(By.xpath("./*[1]")).getText().equals(itemText))
                return element;
        }
        LOGGER.error("Element with text '" + itemText + "' was not found");
        return null;
    }
}
