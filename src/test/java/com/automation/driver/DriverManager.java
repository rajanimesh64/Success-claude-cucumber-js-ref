package com.automation.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void initDriver() {
        String localDriver = System.getProperty("webdriver.chrome.driver");
        if (localDriver == null || localDriver.isEmpty()) {
            // online: let WebDriverManager download the matching ChromeDriver
            WebDriverManager.chromedriver().setup();
        }
        // offline: pass -Dwebdriver.chrome.driver=C:/drivers/chromedriver.exe to Maven
        ChromeOptions options = new ChromeOptions();
        if (System.getenv("CI") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }
        WebDriver driver = new ChromeDriver(options);
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
