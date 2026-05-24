package com.automation.pages;

import org.openqa.selenium.WebDriver;

public class YouTubePage {

    private final WebDriver driver;

    public YouTubePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigate() {
        driver.get("https://www.youtube.com");
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
