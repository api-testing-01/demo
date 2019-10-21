package org.fundacionjala.pivotal.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        glue = {"org.fundacionjala.pivotal"},
        features = "src/test/resources/features"
)
public class Runner extends AbstractTestNGCucumberTests {
}
