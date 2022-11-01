package com.gcw.apiautomation.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeSuite;

@CucumberOptions(
        features = "src/test/java/com/gcw/testautomation/tests/",
        glue = "com.gcw.testautomation.steps",
        monochrome = true,
        dryRun = true
)
public class FeatureTestRunner extends AbstractTestNGCucumberTests {

    @BeforeSuite
    public static void setUp() {
        String tags = "@ui or @api";
        System.setProperty("cucumber.filter.tags", tags);

    }


}
