package dev.sharaf.testnakupuna.tests;

import dev.sharaf.testnakupuna.pages.ContactUs;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestContactUs {
    ContactUs contactUs;

    @BeforeMethod
    void setup() {
        contactUs = new ContactUs("chrome");
    }

    @AfterMethod
    void teardown() {
        contactUs.getDriver().quit();
    }

    @Test(description = "Testing failure path", enabled = false)
    public void testHappyPath() throws InterruptedException {
        String result = contactUs.failedFormSubmission("James", "Bond", "tester@test.com", "Test", "This is a test.");
        Thread.sleep(5_000);
        assert result.contains("success");
    }

    @Test(description = "Testing failure path")
    public void testFailurePath() throws InterruptedException {
        String result = contactUs.failedFormSubmission("", "b", "", "", "");
        Thread.sleep(5_000);
        assert result.contains("There was a problem with your submission. Errors are marked below.");
    }

    @Test(description = "Testing DC office location")
    public void testDCOfficeLocation() throws InterruptedException {
        String expectedAddress = "251 18th St S #1005, Arlington, VA 22202";
        String result = contactUs.getDcOfficeAddress();
        assert result.contains(expectedAddress);
    }
}
