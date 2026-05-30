package com.automation.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "@target/rerun.txt",
        glue = "com.automation",
        plugin = {
                "pretty",
                "rerun:target/rerun.txt"
        },
        monochrome = true
)
public class RerunTestRunner {
}
