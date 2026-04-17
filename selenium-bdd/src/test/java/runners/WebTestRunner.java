package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * WebTestRunner - runs all vFairs web BDD tests.
 * To run specific tags: mvn test -Dcucumber.filter.tags="@smoke"
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = "stepDefinitions",
        tags = "not @ignore",
        plugin = {
                "pretty",
                "html:target/reports/web-cucumber.html",
                "json:target/reports/web-cucumber.json"
        },
        monochrome = true
)
public class WebTestRunner {
}
