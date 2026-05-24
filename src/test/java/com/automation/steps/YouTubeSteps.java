package com.automation.steps;

import com.automation.driver.DriverManager;
import com.automation.pages.YouTubePage;
import com.automation.utils.ScreenshotUtil;
import io.cucumber.java.en.Given;

public class YouTubeSteps {

    private YouTubePage youtubePage;

    @Given("I open YouTube")
    public void iOpenYouTube() {
        youtubePage = new YouTubePage(DriverManager.getDriver());
        youtubePage.navigate();
        ScreenshotUtil.capture("YouTube Homepage Loaded");
    }
}
