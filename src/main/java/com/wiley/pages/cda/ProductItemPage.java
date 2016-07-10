package com.wiley.pages.cda;

import com.wiley.support.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductItemPage extends SearchResultsPage {
    private String titleAtSearchResults;
    private WebDriver driver;

    @FindBy(xpath = "//div[@class='product-biblio']/h1[@class='productDetail-title']")
    @CacheLookup
    private WebElement productItemTitle;

    public ProductItemPage() {
        this.driver = Driver.getInstance();
        PageFactory.initElements(driver, this);
    }

    public WebElement getProductItemTitle() {
        return productItemTitle;
    }

    public String getTitleAtSearchResults() {
        return titleAtSearchResults;
    }

    public void setTitleAtSearchResults(String titleAtSearchResults) {
        this.titleAtSearchResults = titleAtSearchResults;
    }
}
