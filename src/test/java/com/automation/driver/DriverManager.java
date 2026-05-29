package com.automation.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void initDriver() {
        String browser = System.getProperty("browser", "firefox");
        WebDriver driver;

        if (browser.equalsIgnoreCase("chrome")) {
            if (System.getProperty("webdriver.chrome.driver") == null) {
                WebDriverManager.chromedriver().setup();
            }
            ChromeOptions options = new ChromeOptions();
            if (System.getenv("CI") != null) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
            }
            driver = new ChromeDriver(options);
        } else {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if (System.getenv("CI") != null) {
                options.addArguments("--headless");
            }
            driver = new FirefoxDriver(options);
        }

        if (System.getenv("CI") == null) {
            driver.manage().window().maximize();
        }
        driverThreadLocal.set(driver);
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
