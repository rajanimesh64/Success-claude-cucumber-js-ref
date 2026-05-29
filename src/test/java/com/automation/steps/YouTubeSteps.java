package com.automation.steps;

import com.automation.driver.DriverManager;
import com.automation.pages.YouTubePage;
import com.automation.utils.CucumberLogger;
import com.automation.utils.ScreenshotUtil;
import io.cucumber.java.en.Given;

public class YouTubeSteps {

    private YouTubePage youtubePage;

    @Given("I open YouTube")
    public void iOpenYouTube() {
        CucumberLogger.addStepLog("Navigating to YouTube homepage");
        youtubePage = new YouTubePage(DriverManager.getDriver());
        youtubePage.navigate();
        CucumberLogger.addStepLog("YouTube loaded. Page title: " + DriverManager.getDriver().getTitle());
        ScreenshotUtil.capture("YouTube Homepage Loaded");
    }
}
