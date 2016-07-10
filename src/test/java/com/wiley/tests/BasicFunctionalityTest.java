package com.wiley.tests;

import com.wiley.pages.cda.HomePage;
import com.wiley.pages.cda.ProductItemPage;
import com.wiley.pages.cda.SearchResultsPage;
import com.wiley.pages.cda.StudentsPage;
import com.wiley.pages.edservices.EdServicesHomePage;
import com.wiley.support.BaseTest;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.TestException;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.wiley.support.Driver.*;
import static com.wiley.utils.Common.*;
import static org.hamcrest.Matchers.*;
import static com.wiley.support.Assert.*;

public class BasicFunctionalityTest extends BaseTest {
    private HomePage homePage;
    private StudentsPage studentsPage;
    private List<WebElement> searchResults;
    private SearchResultsPage searchResultsPage;
    private ProductItemPage productItemPage;
    private EdServicesHomePage edServicesHomePage;

    private static final List<String> EXPECTED_TOP_NAV_TEXT = Arrays.asList("Home", "Subjects", "About Wiley", "Contact Us", "Help");
    private static final List<String> EXPECTED_RESOURCES_MENU_TEXT = Arrays.asList("Students", "Authors", "Instructors", "Librarians",
            "Societies", "Conferences", "Booksellers", "Corporations", "Institutions");
    private static final List<String> EXPECTED_RESOURCES_FOR_MENU_1ST_LEVEL_TEXT = Arrays.asList("Authors", "Librarians",
            "Booksellers", "Instructors", "Students", "Government Employees", "Societies", "Corporate Partners");
    private static final String EXPECTED_STUDENTS_URL_PATTERN = "http.*(wiley.com.+)";

    @Test(testName = "Top navigation menu links check")
    public void checkTopNavigationMenu() {
        homePage = HomePage.openHomePage();
        List<String> actualLinkTexts = homePage.getTopNavigationMenuLinksText();
        assertThat("Top navigation menu contains correct links", actualLinkTexts, is(equalTo(EXPECTED_TOP_NAV_TEXT)));
    }

    @Test(testName = "Resources sub-header check", priority = 1)
    public void checkResourcesSubHeader() {
        List<WebElement> actualResourcesMenuItems = homePage.getResourcesMenuItems();
        List<String> actualResourcesMenuTexts = homePage.getResourcesMenuLinksText();
        assertThat("Item names are correct in 'Resources' sub-header", actualResourcesMenuTexts,
                is(equalTo(EXPECTED_RESOURCES_MENU_TEXT)));
        assertThat("Number of items is correct in 'Resources' sub-header", actualResourcesMenuItems.size(),
                is(equalTo(EXPECTED_RESOURCES_MENU_TEXT.size())));
    }

    @Test(testName = "Students link check", priority = 2)
    public void checkStudentsResourceLink() {
        studentsPage = homePage.clickStudentsResourceLink();
        WebElement header = studentsPage.getPageHeader();
        String actualCurrentUrl = getInstance().getCurrentUrl();
        String expectedCurrentUrl = getSubstringByRegexPatternGroup(StudentsPage.URL, EXPECTED_STUDENTS_URL_PATTERN);
        assertThat("'Students' header is displayed", header.isDisplayed());
        assertThat("'Students' URL is correct", actualCurrentUrl, containsString(expectedCurrentUrl));
    }

    @Test(testName = "Resources For menu check", priority = 3)
    public void checkResourcesForMenu() {
        List<WebElement> actualResourcesForMenuItems = studentsPage.getResourcesForMenuItems();
        List<String> actualResourcesForMenuFirstLevelTexts = studentsPage.getResourcesForMenuFirstLevelTexts();
        assertThat("Item names are correct in 'Resources For' menu", actualResourcesForMenuFirstLevelTexts,
                is(equalTo(EXPECTED_RESOURCES_FOR_MENU_1ST_LEVEL_TEXT)));
        assertThat("Number of items is correct in 'Resources For' menu", actualResourcesForMenuItems.size(),
                is(equalTo(EXPECTED_RESOURCES_FOR_MENU_1ST_LEVEL_TEXT.size())));
    }

    @Test(testName = "Resources For menu selected item check", priority = 4)
    public void checkResourcesForSelectedItem() {
        String notSelectedItemText = "Authors";
        String selectedItemExpectedText = "Students";
        String bckgColorCssProp = "background-color";
        String colorCssProp = "color";

        WebElement actualSelectedItem = studentsPage.getResourcesForMenuSelectedItem();
        WebElement notSelectedItem = studentsPage.getResourcesForMenuItemWithText(notSelectedItemText);

        List<WebElement> anyLinkUnderSelectedItem = actualSelectedItem.findElements(By.xpath(".//a[@href]"));
        String selectedItemBckgColor = actualSelectedItem.getCssValue(bckgColorCssProp);
        String selectedItemColor = actualSelectedItem.getCssValue(colorCssProp);
        String notSelectedItemBckgColor = notSelectedItem.getCssValue(bckgColorCssProp);
        String notSelectedItemColor = notSelectedItem.getCssValue(colorCssProp);

        assertThat(selectedItemExpectedText + " item of 'Resources For' menu is selected",
                actualSelectedItem.getText(), is(equalTo(selectedItemExpectedText)));
        assertThat(selectedItemExpectedText + " item of 'Resources For' menu is not clickable",
                anyLinkUnderSelectedItem, hasSize(equalTo(0)));
        assertThat(selectedItemExpectedText + " item of 'Resources For' has different from non-selected items style",
                !selectedItemBckgColor.equals(notSelectedItemBckgColor) && !selectedItemColor.equals(notSelectedItemColor));
    }

    @BeforeGroups(groups = "Sign up to Wiley check")
    public void setUpBeforeSignUp() {
        homePage = studentsPage.topNavMenuClickHome();
    }

    @DataProvider(name = "Sign up - alerts check data provider")
    public Object[][] signUpAlertsCheckDataProvider() {
        return new Object[][]{
                {"Please enter email address", ""},
                {"Invalid email address.", "www.test.com"}
        };
    }

    @Test(dataProvider = "Sign up - alerts check data provider", groups = "Sign up to Wiley check",
            testName = "Sign up to Wiley updates - alert check", priority = 5)
    public void checkAlertsOnWileyUpdates(String expectedAlertText, String inputForEmailField) {
        homePage.inputTextToSignUpToWiley(inputForEmailField);
        homePage.clickSignUpToWileySubmitButton();
        assertThat("Alert is present", isAlertPresent());
        String alertText = getAlertTextAndAccept();
        assertThat("Alert text is: " + expectedAlertText, alertText, is(equalTo(expectedAlertText)));
    }

    @Test(testName = "Search input check", priority = 6)
    public void checkSearchInput() {
        homePage.inputTextToSearchField("for dummies");
        searchResultsPage = homePage.submitSearch();
        searchResults = searchResultsPage.getSearchResultsItems();
        assertThat("List of search results appeared", searchResults, is(not(Matchers.<WebElement>empty())));
    }

    @Test(testName = "Search result item check", priority = 7)
    public void checkSearchResultsItem() {
        productItemPage = searchResultsPage.clickRandomSearchResultAndRememberTitle();
        String productTitle = productItemPage.getProductItemTitle().getText();
        String titleAtSearchResults = productItemPage.getTitleAtSearchResults();
        assertThat("Product title is equal to the title at search results", productTitle, is(equalTo(titleAtSearchResults)));
    }

    @BeforeGroups(groups = "Institutions page check")
    public void setUpBeforeInstitutionsPageCheck() {
        homePage = productItemPage.topNavMenuClickHome();
        Set<String> browserTabs = getCurrentTabs();
        if (browserTabs.size() > 1)
            throw new TestException("Number of browser tabs before 'Institutions page check' test should be 1, but was "
                    + browserTabs.size());
    }

    @Test(testName = "Search result item check", groups = "Institutions page check", priority = 8)
    public void checkInstitutionsPage() {
        String tabBeforeClick = getCurrentTab();
        edServicesHomePage = homePage.clickInstitutionsResourceLink();
        Set<String> tabsAfterClick = getCurrentTabs();
        boolean isNewTabOpened = false;
        for (String tab : tabsAfterClick) {
            if (!tab.equals(tabBeforeClick)) {
                isNewTabOpened = true;
                switchToTab(tab);
                break;
            }
        }
        assertThat("New tab is opened", isNewTabOpened);

        WebElement header = edServicesHomePage.getPageHeader();
        String actualCurrentUrl = getInstance().getCurrentUrl();
        String expectedCurrentUrl = EdServicesHomePage.URL;
        assertThat("'Wiley Education Services' page header is displayed", header.isEnabled());
        assertThat("'Wiley Education Services' URL is correct", actualCurrentUrl, is(expectedCurrentUrl));
    }
}
