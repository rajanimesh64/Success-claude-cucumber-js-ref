package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GooglePage {

    private final WebDriver driver;

    private final By searchBox = By.name("q");

    public GooglePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigate() {
        driver.get("https://www.google.com");
    }

    public void typeInSearchBox(String text) {
        driver.findElement(searchBox).sendKeys(text);
    }

    public String getSearchBoxValue() {
        return driver.findElement(searchBox).getAttribute("value");
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
