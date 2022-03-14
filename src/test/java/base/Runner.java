package base;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber.json", "html:target/cucumber-reports/cucumber.html"},
    features = {"src/test/java/features"},
    monochrome = true,
    glue = "steps")
public class Runner {}
