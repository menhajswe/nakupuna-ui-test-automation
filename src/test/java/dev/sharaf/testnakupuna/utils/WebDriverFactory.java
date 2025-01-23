package dev.sharaf.testnakupuna.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {

    /**
     * Create browser specific WebDriver
     * @param browserType specifies the type of browser in the request
     * @return WebDriver based on the browser type such as chrome, firefox etc.
     * @throws IllegalArgumentException when the OS is not one of the three: windows, mac-os or linux.
     */
    public static WebDriver createDriver(String browserType) {
        return switch (browserType.toLowerCase()) {
            case "chrome" -> new ChromeDriver();
            case "firefox" -> new FirefoxDriver();
            case "safari" -> new SafariDriver();
            case "edge" -> new EdgeDriver();
            default -> getDefaultDriverBasedOnOs();
        };
    }

    /**
     * Selects a default browser based on the Operating System
     * @return WebDriver
     */
    private static WebDriver getDefaultDriverBasedOnOs() {
        String os = System.getProperty("os.name").toLowerCase();
        os = os.substring(0, os.indexOf(' '));
        if (os.contains("mac")) {
            return new SafariDriver();
        } else if (os.contains("win")) {
            return new EdgeDriver();
        } else if (os.contains("linux")) {
            return new FirefoxDriver();
        } else {
            System.out.println("OS is not supported");
            throw new IllegalArgumentException("OS is not supported");
        }
    }
}
