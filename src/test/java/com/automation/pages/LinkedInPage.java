package com.automation.pages;

import org.openqa.selenium.WebDriver;

public class LinkedInPage {

    private final WebDriver driver;

    public LinkedInPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigate() {
        driver.get("https://www.linkedin.com");
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
