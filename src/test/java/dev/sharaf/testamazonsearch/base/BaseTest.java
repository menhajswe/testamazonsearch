package dev.sharaf.testamazonsearch.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Base test class for all test classes. This class provides common methods for all test classes.
 * It initializes the WebDriver and WebDriverWait objects and provides methods for interacting with the web elements.
 *
 */
public class BaseTest {
    WebDriver driver;
    WebDriverWait wait;
    int waitTimeInSeconds = 5;

    /**
     * Default constructor that initializes the WebDriver and WebDriverWait objects based on the Operating System.
     * The default wait time is set to 5 seconds.
     */
    public BaseTest() {
        this.driver = getDefaultDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
        this.driver.manage().window().maximize();
    }

    /**
     * Constructor that initializes the WebDriver and WebDriverWait objects based on the browser specified.
     * @param browser name of the browser to use for the test.
     */
    public BaseTest(String browser) {
        this.driver = getDriver(browser);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
        this.driver.manage().window().maximize();
    }

    /**
     * Constructor that initializes the WebDriver and WebDriverWait objects based on the browser and headless mode specified.
     * @param browser name of the browser to use for the test.
     * @param headless boolean value to run the browser in headless mode.
     */
    public BaseTest(String browser, boolean headless) {
        this.driver = getDriver(browser, headless);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
    }

    /**
     * Visit the specified URL.
     * @param url URL to visit.
     */
    public void visit(String url) {
        driver.get(url);
    }

    /**
     * Set the wait time in seconds for the WebDriverWait object.
     * @param waitTimeInSeconds wait time in seconds.
     */
    public void setWaitTimeInSeconds(int waitTimeInSeconds) {
        this.waitTimeInSeconds = waitTimeInSeconds;
    }

    /**
     * Update the wait time in seconds for the WebDriverWait object.
     * @param waitTimeInSeconds wait time in seconds.
     */
    public void updateWaitTimeInSeconds(int waitTimeInSeconds) {
        this.waitTimeInSeconds = waitTimeInSeconds;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
    }

    /**
     * Get the wait time in seconds for the WebDriverWait object.
     * @return wait time in seconds.
     */
    public int getWaitTimeInSeconds() {
        return waitTimeInSeconds;
    }

    /**
     * This method finds the web element using the specified locator.
     * @param locator locator of the web element.
     * @return WebElement object.
     */
    public WebElement find(String locator) {
        return driver.findElement(By.xpath(locator));
    }

    /**
     * This method finds the web element using the By object locator.
     * @param target locator of the web element.
     * @return WebElement object.
     */
    public WebElement find(By target) {
        return driver.findElement(target);
    }

    /**
     * This method finds all the web elements using the specified locator.
     * @param target locator of the web elements.
     * @return List of WebElement objects.
     */
    public List<WebElement> findAll(By target) {
        return driver.findElements(target);
    }

    /**
     * This method clicks the web element.
     * @param element WebElement object to click.
     */
    public void clickElement(WebElement element) {
        element.click();
    }

    /**
     * This method sends the keys to the web element.
     * @param element WebElement object to send the keys.
     * @param keys keys to send to the web element.
     */
    public void sendKeys(WebElement element, String keys) {
        element.sendKeys(keys);
    }

    /**
     * This method performs submit actions on the given web element.
     * @param element WebElement object to clear.
     */
    public void submitElement(WebElement element) {
        element.submit();
    }

    /**
     * This method waits for the web element to be visible.
     * @param element WebElement object to wait for its visibility.
     */
    public void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * This method waits for the web element to be visible.
     * @param target locator of the web element to wait for its visibility.
     */
    public void waitForElementToBeVisible(By target) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(target));
    }

    /**
     * This method waits for the web element to be present.
     * @param target WebElement object to wait for its presence.
     */
    public void waitForElementToBePresent(By target) {
        wait.until(ExpectedConditions.presenceOfElementLocated(target));
    }

    /**
     * Pauses the thread for the specified milliseconds.
     * @param seconds time in seconds to pause the thread.
     */
    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Check if the web element is visible.
     * @param element WebElement object to check its visibility.
     * @return true if the web element is visible, false otherwise.
     */
    public boolean isElementVisible(WebElement element) {
        return element.isDisplayed();
    }

    /**
     * Check if the web element is visible.
     * @param target locator of the web element to check its visibility.
     * @return true if the web element is visible, false otherwise.
     */
    public boolean isElementVisible(By target) {
        return find(target).isDisplayed();
    }

    /**
     * Quit the WebDriver instance.
     */
    public void quitDriver() {
        driver.quit();
    }

    /**
     * Close the WebDriver instance.
     */
    public void closeDriver() {
        driver.close();
    }

    /**
     * Given the browser name, this method returns the corresponding WebDriver instance.
     * @param browser name of the browser.
     * @return WebDriver instance.
     */
    private WebDriver getDriver(String browser) {
        switch (browser) {
            case "chrome":
                return new ChromeDriver();
            case "firefox":
                return new FirefoxDriver();
            case "safari":
                return new SafariDriver();
            case "edge":
                return new EdgeDriver();
            default:
                return getDefaultDriver();
        }
    }

    /**
     * Given the browser name and headless mode, this method returns the corresponding WebDriver instance.
     * @param browser name of the browser.
     * @param headless boolean value to run the browser in headless mode.
     * @return WebDriver instance.
     */
    private WebDriver getDriver(String browser, boolean headless) {
        return headless ? getWebDriversWithHeadlessOptions(browser) : getDriver(browser);
    }

    /**
     * Given the browser name, this method returns the corresponding WebDriver instance with headless options.
     * @param browser name of the browser.
     * @return WebDriver instance.
     */
    private WebDriver getWebDriversWithHeadlessOptions(String browser) {
        switch (browser) {
            case "firefox":
                return new FirefoxDriver(new FirefoxOptions().addArguments("--headless"));
            case "safari":
                return new SafariDriver();
            case "edge":
                return new EdgeDriver(new EdgeOptions().addArguments("--headless"));
            default:
                return new ChromeDriver(new ChromeOptions().addArguments("--headless"));
        }
    }

    /**
     * This method returns the default WebDriver instance based on the Operating System.
     * @return WebDriver instance.
     */
    private WebDriver getDefaultDriver() {
        String os = detectHostOS();
        switch (os) {
            case "windows":
                return new EdgeDriver();
            case "mac":
                return new SafariDriver();
            case "linux":
                return new FirefoxDriver();
            default:
                return new ChromeDriver();
        }
    }

    /**
     * This method detects the Operating System of the host machine.
     * @return Operating System name.
     */
    private String detectHostOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "windows";
        } else if (os.contains("mac")) {
            return "mac";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return "linux";
        } else {
            return "other";
        }
    }
}
