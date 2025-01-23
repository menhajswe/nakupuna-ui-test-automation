package dev.sharaf.testnakupuna.pages;

import dev.sharaf.testnakupuna.bases.NakBase;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This class includes locators and actions for retrieving home-page elements.
 */
public class Home extends NakBase {
    // xPaths for the web elements in home page
    private By mainBarLocator = By.xpath("//header[@id='top']//div[@class='container']");
    private By aboutUsLocator = By.xpath("//span[@class='menu-title-text'][normalize-space()='About Us']");



    @Getter
    private By headerMenuLocator = By.xpath("//nav[@aria-label='Main Menu']/ul/li");
    private By whatWeDoLocator = By.xpath("//span[@class='menu-title-text'][normalize-space()='What We Do']");
    private By powerIdeasH1Locator = By.xpath("//h1[contains(@aria-label,'Powerful Ideas. Proven Capabilities. Positive Impact.')]");
    private By arrowDownLocator = By.xpath("//i[@class='fa fa-angle-down top']");

    private By topWorkplaceLocator = By.xpath("//h5[normalize-space()='Ranked First by the Washington Post Top Workplaces']");
    private By workingWithUsLocator = By.xpath("//span[@class='menu-title-text'][normalize-space()='Working With Us']");
    private By careersLocator = By.xpath("//span[@class='menu-title-text'][normalize-space()='Careers']");
    private By newLocator = By.xpath("//span[@class='menu-title-text'][normalize-space()='News']");
    private By contactUsLocator = By.xpath("//span[@class='menu-title-text'][normalize-space()='Contact Us']");
    private By employeePortalLocator = By.xpath("//span[@class='menu-title-text'][normalize-space()='Employee Portal']");
    private By aboutUsSubMenuLocator = By.xpath("//*[@class=\"sub-menu\"]/li");


    public Home(String browser) {
        super(browser);
    }

    public WebElement getMainBarLocator() {
        return getElement(mainBarLocator);
    }

    public WebElement getAboutUsWebElement() {
        return getElement(aboutUsLocator);
    }

    public List<WebElement> getHeaderElements() {
        return getElements(headerMenuLocator);
    }

    public WebElement getAboutUs() {
        return getElement(aboutUsLocator);
    }

    public void moveToArrowAndClickArrowDown() {
        WebElement arrow = getElement(arrowDownLocator);
        moveToElement(arrow);
        arrow.click();
    }

    public WebElement getPowerIdeasH1Locator() {
        return getElement(powerIdeasH1Locator);
    }

    public WebElement getTopWorkplaceLocator() {
        return getElement(topWorkplaceLocator);
    }

    public List<WebElement> getSubElementsOfAboutUs() {
        By aboutUs = By.xpath("//nav/ul/li/ul/li");
        return getElementWithAction(aboutUs);
    }

    public List<WebElement> getSubElementsOfAboutUsWithJS() {
        String mouseEventScript = """
                const element = document.querySelector("body > div:nth-child(5) > div:nth-child(1) > div:nth-child(3) > header:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > nav:nth-child(3) > ul:nth-child(1) > li:nth-child(1) > a:nth-child(1) > span:nth-child(1)")
                const mouseOverEvent = new MouseEvent('mouseover', {
                            bubbles: true,
                            cancelable: true,
                            view: window
                        });
                element.dispatchEvent(mouseOverEvent);
                """;
        return getElementsWithJs(By.xpath("//nav/ul/li/ul/li"), mouseEventScript);
    }

    public WebElement getWhatWeDoElement() {
        return getElement(whatWeDoLocator);
    }

    public List<WebElement> getSubElementsOfAboutUsWithJS(By parentPath, String childNodes) {
        // Use the parentPath to dynamically find the parent element and hover over it
        String mouseEventScript = String.format("""
            const parentElement = document.evaluate(`%s`, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
            if (parentElement) {
                const mouseOverEvent = new MouseEvent('mouseover', {
                    bubbles: true,
                    cancelable: true,
                    view: window
                });
                parentElement.dispatchEvent(mouseOverEvent);
            } else {
                console.error('Parent element not found for XPath: %s');
            }
            """, parentPath.toString(), parentPath.toString());

        // Find and return child elements after dispatching the hover event
        return getElementsWithJs(By.xpath(childNodes), mouseEventScript);
    }
}
