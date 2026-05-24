package com.automation.steps;

import com.automation.driver.DriverManager;
import com.automation.pages.GooglePage;
import com.automation.utils.ScreenshotUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class GoogleSteps {

    private GooglePage googlePage;

    @Given("I open the Google homepage")
    public void iOpenTheGoogleHomepage() {
        googlePage = new GooglePage(DriverManager.getDriver());
        googlePage.navigate();
        ScreenshotUtil.capture("Google Homepage Loaded");
    }

    @When("I type {string} in the Google search box")
    public void iTypeInTheGoogleSearchBox(String text) {
        googlePage.typeInSearchBox(text);
        ScreenshotUtil.capture("Typed '" + text + "' in search box");
    }

    @Then("the Google search box should contain {string}")
    public void theGoogleSearchBoxShouldContain(String expectedText) {
        Assert.assertEquals(expectedText, googlePage.getSearchBoxValue());
    }

    @Then("the page title should contain {string}")
    public void thePageTitleShouldContain(String expectedTitle) {
        String actualTitle = DriverManager.getDriver().getTitle();
        Assert.assertTrue(
            "Expected title to contain: " + expectedTitle + " but was: " + actualTitle,
            actualTitle.contains(expectedTitle)
        );
    }
}
