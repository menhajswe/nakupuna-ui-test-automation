package dev.sharaf.testnakupuna.bases;

import dev.sharaf.testnakupuna.pages.Home;
import dev.sharaf.testnakupuna.utils.WebDriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Base class for Nakupuna test classes.
 */
public class NakBase {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;
    private int waitTimeInSeconds = 5;
    private Actions actions;

    public NakBase(String browser) {
        driver = WebDriverFactory.createDriver(browser);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
        jsExecutor = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public void setWindowSize(int width, int height) {
        driver.manage().window().setSize(new Dimension(width, height));
    }

    public void tearDown(String closeOrQuit) {
        if (driver != null) {
            if (closeOrQuit.equalsIgnoreCase("quit")) {
                driver.quit();
            } else {
                driver.close();
            }
        }
    }

    public void visit(String baseUrl) {
        driver.get(baseUrl);
    }

    public WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    public List<WebElement> getElements(By elements) {
        return driver.findElements(elements);
    }

    public List<WebElement> getElementWithAction(By element) {
        WebElement el = getElement(element);
        actions.moveToElement(el).build().perform(); // add click
        return driver.findElements(element);
    }

    public void moveToElement(WebElement element) {
        actions.moveToElement(element).build().perform(); // add click
    }

    public void executeJavaScript(String script, WebElement element) {
        jsExecutor.executeScript(script, element);
    }

    public Object executeJavaScript(String script) {
        return jsExecutor.executeScript(script);
    }

    public List<WebElement> getElementsWithJs(By xpath, String script) {
        jsExecutor.executeScript(script);
        return getElements(xpath);
    }

    public boolean isDisplayed(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).isDisplayed();
    }

    public boolean isDisplayed(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.isDisplayed();
    }

    public void scrollToElement(By element) {
        WebElement webElement = driver.findElement(element);
        Actions mouseActions = new Actions(driver);
        mouseActions.scrollToElement(webElement).build().perform();
        webElement.click();
    }

    public void clickElement(By locator) {
        getElement(locator).click();
    }

    public void scrollDown(int width, int height) {
        // const width = window.innerWidth;
        // const height = window.innerHeight;
        // window.moveTo(0, 0);
        // window.resizeTo(screen.availWidth, screen.availHeight);
        Point currentPosition = driver.manage().window().getPosition();
        driver.manage().window().setPosition(
                new Point(currentPosition.getX() + width, currentPosition.getY() + height));
    }

    public void scrollToBottom() {
        String documentBodyScrollHeight = """
                window.scrollTo({
                  top: document.body.scrollHeight,
                behavior: 'smooth'
                })
                """;
        jsExecutor.executeScript(documentBodyScrollHeight);
    }

    public void scrollDownBy500Pixels() {
        String scrollBy500Pixels = """
            window.scrollBy({
              top: 500,
              behavior: 'smooth'
            });
            """;
        jsExecutor.executeScript(scrollBy500Pixels);
    }

    public void scrollDownByHalfPage() {
        String scrollByHalfPage = """
            window.scrollBy({
              top: window.innerHeight / 2,
              behavior: 'smooth'
            });
            """;
        jsExecutor.executeScript(scrollByHalfPage);
    }

    public void asyncScroll() {
        String scrollDown = """
                (async () => {
                  for (let i = 0; i < 5; i++) {
                    await driver.executeScript("window.scrollBy(0, 200);"); // Scroll 200 pixels down
                    await new Promise(resolve => setTimeout(resolve, 500)); // Pause for 500ms
                  }
                })();
                """;
        jsExecutor.executeScript(scrollDown);
    }
}
