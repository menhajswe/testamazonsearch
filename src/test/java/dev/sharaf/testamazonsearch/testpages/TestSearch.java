package dev.sharaf.testamazonsearch.testpages;

import dev.sharaf.testamazonsearch.pages.Search;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

public class TestSearch {
    final String amazonSearchLocator = "//*[ @id=\"twotabsearchtextbox\" or @id=\"nav-bb-search\" or ( @role=\"searchbox\" or @placeholder=\"Search Amazon\" or @id=\"search-field\" ) ]";
    By searchBoxLocator = By.xpath(amazonSearchLocator);
    Search searchPage;

    @BeforeClass
    public void setUp() {
        searchPage = new Search("firefox");
        searchPage.visit("https://www.amazon.com/");
        System.out.println("Page title: " );
    }

    @Test
    public void testSearch() {
        final String targetValue = "MacBook";
        searchPage.search(searchBoxLocator, targetValue);
        By partialLinkString = By.partialLinkText(targetValue);
        searchPage.waitForElementToBeVisible(partialLinkString);
        WebElement result = searchPage.find(partialLinkString);
        System.out.println(result.getText());
        assert result.getText().contains(targetValue);
    }

    @Test(dataProvider = "searchValues")
    public void testSearchWithMultipleValues(String target) {
        searchPage.search(searchBoxLocator, target);
        // add a wait time to see the search results
        searchPage.sleep(10);

        By partialLinkString = By.partialLinkText(target);
        searchPage.waitForElementToBeVisible(partialLinkString);
        WebElement result = searchPage.find(partialLinkString);
        System.out.println(result.getText());
        assert result.getText().contains(target);
    }

    @DataProvider(name = "searchValues")
    public Object[][] searchValues() {
        return new Object[][] {
                {"iPhone"},
                {"Samsung Galaxy"},
                {"Dell XPS"}
        };
    }

    @AfterClass
    public void tearDown() {
        searchPage.quitDriver();
    }
}
