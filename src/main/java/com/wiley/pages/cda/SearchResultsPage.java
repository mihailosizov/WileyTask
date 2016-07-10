package com.wiley.pages.cda;

import com.wiley.support.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

public class SearchResultsPage extends BaseCdaPage {
    private WebDriver driver;
    private Random random;
    private static final String SEARCH_RESULTS_XPATH = "//div[@id='search-results']/div[contains(@class, 'product-listing')]";

    @FindAll(@FindBy(xpath = SEARCH_RESULTS_XPATH))
    private List<WebElement> searchResultsItems;

    @FindAll(@FindBy(xpath = SEARCH_RESULTS_XPATH + "/div[@class='product-title']/a[@href]"))
    private List<WebElement> searchResultsTitleLinks;

    public SearchResultsPage() {
        this.driver = Driver.getInstance();
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> getSearchResultsItems() {
        return searchResultsItems;
    }

    public ProductItemPage clickRandomSearchResultAndRememberTitle() {
        random = new Random();
        WebElement targetLink = searchResultsTitleLinks.get(random.nextInt(searchResultsItems.size()));
        String titleAtSearchResults = targetLink.getText();
        targetLink.click();
        ProductItemPage productItemPage = new ProductItemPage();
        productItemPage.setTitleAtSearchResults(titleAtSearchResults);
        return productItemPage;
    }
}
