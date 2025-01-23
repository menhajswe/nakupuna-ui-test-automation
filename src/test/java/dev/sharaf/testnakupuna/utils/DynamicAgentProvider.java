package dev.sharaf.testnakupuna.utils;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DynamicAgentProvider {
    public static Object getOptions(String browser) {
        if (browser == null || browser.isEmpty()) {
            throw new IllegalArgumentException("Browser must not be null or empty");
        }

        String randomUserAgent = getRandomAgent();

        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--user-agent=" + randomUserAgent);
                return firefoxOptions;

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--user-agent=" + randomUserAgent);
                return chromeOptions;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--user-agent=" + randomUserAgent);
                return edgeOptions;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static String getRandomAgent() {
        List<String> userAgents = Arrays.asList(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36",
                "Mozilla/5.0 (iPhone14,3; U; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Mobile/19A346 Safari/602.1"
        );
        Random random = new Random();
        return userAgents.get(random.nextInt(userAgents.size()));
    }
}
