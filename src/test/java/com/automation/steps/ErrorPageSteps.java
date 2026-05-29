package com.automation.steps;

import com.automation.driver.DriverManager;
import com.automation.utils.CucumberLogger;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriverException;

import java.time.Duration;

public class ErrorPageSteps {

    @Given("I navigate to an incorrect URL {string}")
    public void iNavigateToAnIncorrectURL(String url) {
        CucumberLogger.addStepLog("Navigating to invalid URL: " + url);
        try {
            DriverManager.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
            DriverManager.getDriver().get(url);
            CucumberLogger.addStepLog("Page loaded unexpectedly for URL: " + url);
        } catch (WebDriverException e) {
            CucumberLogger.addStepLog("WebDriverException caught as expected: " + e.getMessage().split("\n")[0]);
        }
    }

    @Then("the browser should display a page not found error")
    public void theBrowserShouldDisplayAPageNotFoundError() {
        String source = DriverManager.getDriver().getPageSource().toLowerCase();
        String title  = DriverManager.getDriver().getTitle().toLowerCase();
        CucumberLogger.addStepLog("Browser error page title: '" + title + "'");
        Assert.assertTrue(
            "Expected a browser error page but page loaded normally. Title: " + title,
            source.contains("err_") ||
            source.contains("not found") ||
            source.contains("not available") ||
            source.contains("can't be reached") ||
            source.contains("neterror") ||
            source.contains("problem loading") ||
            source.contains("server not found") ||
            title.contains("problem loading") ||
            title.isEmpty()
        );
    }
}
