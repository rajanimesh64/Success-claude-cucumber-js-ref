package com.automation.utils;

import java.nio.charset.StandardCharsets;

public class CucumberLogger {

    public static void addStepLog(String message) {
        ScenarioContext.get().attach(message.getBytes(StandardCharsets.UTF_8), "text/plain", message);
    }
}
