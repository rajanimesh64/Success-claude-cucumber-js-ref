package com.automation.utils;

import io.cucumber.java.Scenario;

public class ScenarioContext {

    private static final ThreadLocal<Scenario> current = new ThreadLocal<>();

    public static void set(Scenario scenario) {
        current.set(scenario);
    }

    public static Scenario get() {
        return current.get();
    }

    public static void remove() {
        current.remove();
    }
}
