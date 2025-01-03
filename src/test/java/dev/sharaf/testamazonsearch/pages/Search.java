package dev.sharaf.testamazonsearch.pages;

import dev.sharaf.testamazonsearch.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Search page class that contains methods to interact with the search box and search results.
 */
public class Search extends BaseTest {
    public Search(String browser) {
        super(browser);
    }

    public Search(String browser, boolean headless) {
        super(browser, headless);
    }

    /**
     * Search for the target value in the search box.
     * @param searchBoxLocator locator of the search box.
     * @param targetValue value to search for.
     */
    public void search(By searchBoxLocator, String targetValue) {
        WebElement searchBoxElement = find(searchBoxLocator);
        searchBoxElement.clear();
        searchBoxElement.sendKeys(targetValue);
        searchBoxElement.submit();
    }

    /**
     * Click the search button after entering the target value in the search box.
     * @param searchBoxLocator locator of the search box.
     * @param searchButton locator of the search button.
     * @param targetValue value to search for.
     */
    public void clickSearchButton(By searchBoxLocator, By searchButton ,String targetValue) {
        WebElement searchBoxElement = find(searchBoxLocator);
        searchBoxElement.sendKeys(targetValue);
        find(searchButton).click();
    }

    /**
     * Get the text of the element.
     * @param element element to get the text from.
     * @return text of the element.
     */
    public String get(WebElement element) {
        return element.getText();
    }
}
