package com.automation.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void initDriver() {
        String browser   = System.getProperty("browser", "firefox");
        String gridUrl   = System.getProperty("grid.url", System.getenv("SELENIUM_GRID_URL"));
        WebDriver driver;

        MutableCapabilities options = buildOptions(browser);

        if (gridUrl != null && !gridUrl.isEmpty()) {
            driver = createRemoteDriver(gridUrl, options);
        } else {
            driver = createLocalDriver(browser, options);
        }

        driver.manage().window().maximize();
        driverThreadLocal.set(driver);
    }

    private static MutableCapabilities buildOptions(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (isCI()) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
            }
            return options;
        } else {
            FirefoxOptions options = new FirefoxOptions();
            if (isCI()) {
                options.addArguments("--headless");
            }
            return options;
        }
    }

    private static WebDriver createRemoteDriver(String gridUrl, MutableCapabilities options) {
        try {
            return new RemoteWebDriver(new URL(gridUrl), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Selenium Grid URL: " + gridUrl, e);
        }
    }

    private static WebDriver createLocalDriver(String browser, MutableCapabilities options) {
        if (browser.equalsIgnoreCase("chrome")) {
            if (System.getProperty("webdriver.chrome.driver") == null) {
                WebDriverManager.chromedriver().setup();
            }
            return new ChromeDriver((ChromeOptions) options);
        } else {
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver((FirefoxOptions) options);
        }
    }

    private static boolean isCI() {
        return System.getenv("CI") != null;
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
