package dev.sharaf.testnakupuna.tests;

import dev.sharaf.testnakupuna.pages.Home;
import dev.sharaf.testnakupuna.utils.NakConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestHomePage {
    private static Logger logger = LoggerFactory.getLogger(Home.class);
    Home homePage;

    @BeforeClass
    void setup() {
        homePage = new Home("firefox");
        homePage.visit(NakConstants.BASE_URL);
    }

    @AfterClass
    void teardown() {
        homePage.tearDown("quit");
    }

    @Test(description = "Test current url is the home pages url")
    public void testHome() {
        assert homePage.getDriver().getCurrentUrl().contains("https://nakupuna.com");
        logger.info("Current url is {}", homePage.getDriver().getCurrentUrl());
    }

    @Test
    void testTitle() {
        String expectedTitle = "The Nakupuna Companies – An NHO-Owned Family of Companies";
        Assert.assertEquals(homePage.getDriver().getTitle(), expectedTitle);
    }

    @Test
    void testMenu() {
        var result = homePage.getAboutUsWebElement().getText().toLowerCase();
        assert result.contains("about us");
        logger.info(result);
    }

    @Test(description = "Tests the expected menu items are listed")
    void testMenuItems() throws InterruptedException {
        homePage.getDriver().navigate().refresh();
        Thread.sleep(3000);
        boolean barDisplayed = homePage.isDisplayed(homePage.getMainBarLocator());
        boolean displayed = homePage.isDisplayed(homePage.getHeaderMenuLocator());
        if (!displayed || !barDisplayed) {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }

        List<WebElement> aboutUsElements = homePage.getHeaderElements();
        Set<String> items = new HashSet<>(Set.of("ABOUT US", "WHAT WE DO", "WORKING WITH US", "CAREERS", "NEWS", "CONTACT US", "EMPLOYEE PORTAL"));
        for (WebElement element : aboutUsElements) {
            String item = element.getText().toUpperCase();
            assert items.contains(item);
            System.out.println(item);
        }
        logger.info("Passed");
    }

    @Test(description = "Test the menu under About Us")
    void testAboutUs() throws InterruptedException {
        // Locate the 'About Us' element
        WebElement aboutUsElement = homePage.getAboutUs();

        // Execute the mouseover event using JavaScript
        String mouseEventScript = """
        const mouseOverEvent = new MouseEvent('mouseover', {
            bubbles: true,
            cancelable: true,
            view: window
        });
        arguments[0].dispatchEvent(mouseOverEvent);
        """;

        // Execute the script
        homePage.executeJavaScript(mouseEventScript, aboutUsElement);
        Thread.sleep(5_000); // Allow time for submenu to appear

        // Find the submenu elements after triggering the hover event
        By tmp = By.xpath("//nav[@aria-label='Main Menu']/ul/li/ul/li/a/span[@class='menu-title-text']");
        List<WebElement> subMenuElements = homePage.getElements(tmp);

        // Filter out elements with empty or whitespace-only text
        List<WebElement> filteredSubMenuElements = subMenuElements.stream()
                .filter(element -> !element.getText().trim().isEmpty())
                .toList();

        // Assert the filtered submenu elements are not empty
        Assert.assertFalse(filteredSubMenuElements.isEmpty(), "Submenu elements should not be empty under 'About Us'.");
        List<String> testValues = List.of(
                "Our History",
                "Our Values and Mission",
                "Leadership",
                "Board of Advisors",
                "Nakupuna Foundation");

        // Print the filtered submenu items for debugging
        for (WebElement element : filteredSubMenuElements) {
            assert testValues.contains(element.getText());
        }
    }

//    @Test
//    void testEmployeePortal() throws ExecutionControl.NotImplementedException {
//        throw new ExecutionControl.NotImplementedException("This is test hasn't been implemented yet.");
//    }

    @Test
    void testMessage() throws InterruptedException {
        Thread.sleep(3000);
        homePage.scrollDownBy500Pixels();
        String expectedMessage = "Powerful Ideas. Proven Capabilities. Positive Impact.".toLowerCase();
        String messageElement = homePage.getPowerIdeasH1Locator().getText().trim().toLowerCase();
        assert messageElement.contains(expectedMessage);
    }

    @Test(enabled = false)
    void testToWorkPlace() {
        homePage.moveToArrowAndClickArrowDown();
        String expectedTopWorkPlace = "Ranked First by the Washington Post Top Workplaces";
        assert homePage.getTopWorkplaceLocator().getText().contains(expectedTopWorkPlace);
    }

    @Test
    void testToLearnMore() throws InterruptedException {
        homePage.scrollToBottom();
        Thread.sleep(3000);
        homePage.moveToElement(homePage.getElement(By.xpath("//a[normalize-space()='LEARN MORE']")));
    }
}
