package dev.sharaf.testnakupuna.tests;

import dev.sharaf.testnakupuna.pages.Careers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestCareers {

    Careers careers;

    @BeforeMethod
    void setup() {
        careers = new Careers("firefox");
    }

    @AfterMethod
    void teardown() {
        careers.getDriver().quit();
    }

    @Test()
    void testJobSearch() throws InterruptedException {
        assert careers.searchAJob("tech");
    }
}
