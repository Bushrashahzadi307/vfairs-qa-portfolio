package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * MobileTestRunner - runs all vFairs mobile (Appium) BDD tests.
 * Requires: Appium server running on localhost:4723
 * To run: mvn test -Dtest=MobileTestRunner
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/mobile",
        glue = "stepDefinitions",
        tags = "not @ignore",
        plugin = {
                "pretty",
                "html:target/reports/mobile-cucumber.html",
                "json:target/reports/mobile-cucumber.json"
        },
        monochrome = true
)
public class MobileTestRunner {
}
