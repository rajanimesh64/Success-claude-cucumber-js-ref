package com.automation.hooks;

import com.automation.driver.DriverManager;
import com.automation.utils.ScenarioContext;
import com.automation.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        ScenarioContext.set(scenario);
        DriverManager.initDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            ScreenshotUtil.capture("Failure - " + scenario.getName());
        }
        DriverManager.quitDriver();
        ScenarioContext.remove();
    }
}
