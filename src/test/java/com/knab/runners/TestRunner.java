package com.knab.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.knab.stepdefinitions", "com.knab.hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber-pretty.html",
        "json:target/cucumber-reports/CucumberTestReport.json",
        "rerun:target/failed_scenarios.txt"
    },
    tags = "@regression",  // This will run both @api and @ui tests
    monochrome = true
)
public class TestRunner {
}