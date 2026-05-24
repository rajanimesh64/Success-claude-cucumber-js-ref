package com.automation.steps;

import com.automation.driver.DriverManager;
import com.automation.pages.LinkedInPage;
import com.automation.utils.ScreenshotUtil;
import io.cucumber.java.en.Given;

public class LinkedInSteps {

    private LinkedInPage linkedInPage;

    @Given("I open LinkedIn")
    public void iOpenLinkedIn() {
        linkedInPage = new LinkedInPage(DriverManager.getDriver());
        linkedInPage.navigate();
        ScreenshotUtil.capture("LinkedIn Homepage Loaded");
    }
}
