package dev.sharaf.testamazonsearch.testpages;

import dev.sharaf.testamazonsearch.pages.Search;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

public class TestSearch {
    By searchBoxLocator = By.xpath("//input[@id='twotabsearchtextbox']");
    Search searchPage;

    @BeforeClass
    public void setUp() {
        searchPage = new Search("");
        searchPage.visit("https://www.amazon.com/");
    }

    @Test
    public void testSearch() {
        final String targetValue = "MacBook";
        WebElement searchBox = null;
        try {
            searchPage.waitForElementToBeVisible(searchBoxLocator);
        } catch (TimeoutException timeoutException) {
            System.out.println("TimeoutException: " + timeoutException.getMessage());
            // In case of A/B testing, the search box may not have the default locator
            searchBox = searchPage.find(By.xpath("//*[@id=\"nav-bb-search\"]"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (searchBox == null) {
            searchBox = searchPage.find(searchBoxLocator);
        }

        // Clear the search box before entering the search term
        searchBox.clear();
        searchBox.sendKeys(targetValue);
        searchBox.submit();

        By partialLinkString = By.partialLinkText(targetValue);
        searchPage.waitForElementToBeVisible(partialLinkString);
        WebElement result = searchPage.find(partialLinkString);
        System.out.println(result.getText());
        assert result.getText().contains(targetValue);
    }

    @Test(dataProvider = "searchValues")
    public void testSearchWithMultipleValues(String target) {
        WebElement searchBox = null;
        try {
            searchPage.waitForElementToBeVisible(searchBoxLocator);
        } catch (TimeoutException timeoutException) {
            System.out.println("TimeoutException: " + timeoutException.getMessage());
            // In case of A/B testing, the search box might not be visible
            searchBox = searchPage.find(By.xpath("//*[@id=\"nav-bb-search\"]"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (searchBox == null) {
            searchBox = searchPage.find(searchBoxLocator);
        }

        // Clear the search box before entering the search term
        searchBox.clear();
        searchBox.sendKeys(target);
        searchBox.submit();

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
