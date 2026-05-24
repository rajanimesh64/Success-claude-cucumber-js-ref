package com.automation.steps;

import com.automation.driver.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriverException;

import java.time.Duration;

public class ErrorPageSteps {

    @Given("I navigate to an incorrect URL {string}")
    public void iNavigateToAnIncorrectURL(String url) {
        try {
            DriverManager.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
            DriverManager.getDriver().get(url);
        } catch (WebDriverException e) {
            // Expected — URL is unreachable, Chrome error page is shown
        }
    }

    @Then("the browser should display a page not found error")
    public void theBrowserShouldDisplayAPageNotFoundError() {
        String source = DriverManager.getDriver().getPageSource().toLowerCase();
        String title  = DriverManager.getDriver().getTitle().toLowerCase();
        Assert.assertTrue(
            "Expected a browser error page but page loaded normally. Title: " + title,
            source.contains("err_") ||
            source.contains("not found") ||
            source.contains("not available") ||
            source.contains("can't be reached") ||
            title.isEmpty()
        );
    }
}
