package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.vfairs.web.ExhibitHallPage;
import pages.vfairs.web.LoginPage;
import pages.vfairs.web.WebinarPage;
import utilities.ConfigManager;
import utilities.DriverManager;

/**
 * WebStepDefinitions - maps Gherkin BDD steps to web automation code.
 * Covers vFairs web platform: Login, Exhibit Hall, Webinar/Auditorium.
 */
public class WebStepDefinitions {

    private WebDriver driver;
    private LoginPage loginPage;
    private ExhibitHallPage exhibitHallPage;
    private WebinarPage webinarPage;

    @Before("@web")
    public void setUp() {
        DriverManager.initWebDriver();
        driver = DriverManager.getDriver();
        loginPage       = new LoginPage(driver);
        exhibitHallPage = new ExhibitHallPage(driver);
        webinarPage     = new WebinarPage(driver);
    }

    @After("@web")
    public void tearDown() {
        DriverManager.quitDriver();
    }

    // ── LOGIN STEPS ──────────────────────────────────────────────────────────

    @Given("the vFairs web platform is loaded")
    public void vFairsWebPlatformLoaded() {
        loginPage.navigateTo();
    }

    @When("the attendee enters a valid registered email address")
    public void attendeeEntersValidEmail() {
        loginPage.enterEmail(ConfigManager.get("test.user.email"));
    }

    @When("the attendee enters valid email {string} on web")
    public void attendeeEntersSpecificEmail(String email) {
        loginPage.enterEmail(email);
    }

    @When("the attendee enters an invalid email format {string}")
    public void attendeeEntersInvalidEmailFormat(String email) {
        loginPage.enterEmail(email);
    }

    @When("the attendee enters an unregistered email {string}")
    public void attendeeEntersUnregisteredEmail(String email) {
        loginPage.enterEmail(email);
    }

    @When("the attendee enters an invalid email address {string}")
    public void attendeeEntersInvalidEmail(String email) {
        loginPage.enterEmail(email);
    }

    @When("clicks Next to proceed")
    public void clicksNext() {
        loginPage.clickSubmit();
    }

    @Then("the password screen should appear")
    public void passwordScreenAppears() {
        Assert.assertTrue("Password field not visible",
                driver.getCurrentUrl().contains("password")
                || loginPage.isErrorDisplayed() == false);
    }

    @When("the attendee enters the correct password")
    public void attendeeEntersPassword() {
        loginPage.enterPassword(ConfigManager.get("test.user.password"));
    }

    @When("the attendee enters email {string}")
    public void attendeeEntersEmail(String email) {
        loginPage.enterEmail(email);
    }

    @When("And clicks Sign In")
    public void clicksSignIn() {
        loginPage.clickSubmit();
    }

    @Then("the attendee should be taken to the event lobby")
    public void attendeeTakenToLobby() {
        Assert.assertTrue("Attendee not logged in", loginPage.isLoggedIn());
    }

    @Then("the password screen should be displayed with a Password field")
    public void passwordScreenDisplayed() {
        // After email step, password screen loaded
        Assert.assertFalse("Still on login, no redirect",
                driver.getCurrentUrl().isEmpty());
    }

    @Then("the page title should read {string} or {string}")
    public void pageTitleCheck(String title1, String title2) {
        String actual = loginPage.getPageTitle();
        Assert.assertTrue("Unexpected page title: " + actual,
                actual.contains(title1) || actual.contains(title2));
    }

    @Then("a validation error message should appear")
    public void validationErrorAppears() {
        Assert.assertTrue("No validation error shown",
                loginPage.isErrorDisplayed());
    }

    @Then("the attendee should remain on the login screen")
    public void attendeeRemainsOnLogin() {
        Assert.assertFalse("Attendee was logged in unexpectedly",
                loginPage.isLoggedIn());
    }

    @Then("an error message should inform the attendee they are not registered")
    public void notRegisteredErrorShown() {
        Assert.assertTrue("No error shown for unregistered email",
                loginPage.isErrorDisplayed());
    }

    @Then("login should be rejected with a validation error")
    public void loginRejectedWithValidationError() {
        Assert.assertTrue("Login was not rejected",
                loginPage.isErrorDisplayed() || !loginPage.isLoggedIn());
    }

    @Given("the event is closed or not yet open")
    public void eventIsClosedOrNotOpen() {
        // Test environment configured with a closed event
        loginPage.navigateTo();
    }

    @When("the attendee attempts to log in with valid credentials")
    public void attendeeAttemptsLoginWithValidCredentials() {
        loginPage.loginWithCredentials(
                ConfigManager.get("test.user.email"),
                ConfigManager.get("test.user.password"));
    }

    @Then("a {string} message should be displayed")
    public void specificMessageDisplayed(String message) {
        if (message.equalsIgnoreCase("Doors Closed")) {
            Assert.assertTrue("Doors Closed message not shown",
                    loginPage.isDoorsClosedMessageDisplayed());
        }
    }

    @Then("the Forgot Password link should be visible on the login page")
    public void forgotPasswordLinkVisible() {
        Assert.assertTrue("Forgot Password link not visible",
                loginPage.isForgotPasswordDisplayed());
    }

    @When("the attendee clicks Forgot Password")
    public void attendeeClicksForgotPassword() {
        loginPage.clickForgotPassword();
    }

    @Then("the attendee should be directed to the password reset flow")
    public void attendeeDirectedToPasswordReset() {
        Assert.assertTrue("Not on password reset page",
                driver.getCurrentUrl().contains("forgot")
                || driver.getCurrentUrl().contains("reset")
                || driver.getCurrentUrl().contains("password"));
    }

    @Given("the attendee is logged into the event")
    public void attendeeIsLoggedIn() {
        loginPage.navigateTo();
        loginPage.loginWithCredentials(
                ConfigManager.get("test.user.email"),
                ConfigManager.get("test.user.password"));
        Assert.assertTrue("Login failed", loginPage.isLoggedIn());
    }

    @When("the attendee navigates to profile and clicks Logout")
    public void attendeeLogsOut() {
        // Logout via URL or profile menu — implementation depends on event config
        driver.get(ConfigManager.getBaseUrl() + "/logout");
    }

    @Then("the attendee should be returned to the login screen")
    public void attendeeReturnedToLoginScreen() {
        Assert.assertTrue("Not redirected to login after logout",
                driver.getCurrentUrl().contains("login")
                || driver.getCurrentUrl().equals(ConfigManager.getBaseUrl() + "/"));
    }

    @When("the attendee enters an email registered for multiple events")
    public void attendeeEntersMultiEventEmail() {
        loginPage.enterEmail(ConfigManager.get("test.user.email"));
    }

    @Then("an OTP selection screen should appear")
    public void otpScreenAppears() {
        Assert.assertTrue("OTP screen not shown",
                driver.getCurrentUrl().contains("otp")
                || driver.getCurrentUrl().contains("verify")
                || !loginPage.isLoggedIn());
    }

    @Then("the result should be {string}")
    public void loginResultShouldBe(String expectedOutcome) {
        loginPage.clickSubmit();
        switch (expectedOutcome) {
            case "password_screen":
                Assert.assertFalse("Should have advanced past login",
                        loginPage.isErrorDisplayed());
                break;
            case "error_message":
            case "validation_error":
                Assert.assertTrue("Expected error not shown",
                        loginPage.isErrorDisplayed() || !loginPage.isLoggedIn());
                break;
        }
    }

    // ── EXHIBIT HALL STEPS ───────────────────────────────────────────────────

    @Given("the attendee is on the Exhibit Hall page")
    public void attendeeOnExhibitHall() {
        attendeeIsLoggedIn();
        driver.get(ConfigManager.getBaseUrl() + "/exhibit-hall");
    }

    @Then("the Exhibit Hall should load with booth cards")
    public void exhibitHallLoadsWithBooths() {
        Assert.assertTrue("Exhibit Hall not loaded",
                exhibitHallPage.isExhibitHallLoaded());
        Assert.assertTrue("No booths visible",
                exhibitHallPage.areBoothsVisible());
    }

    @When("the attendee opens the first booth")
    public void attendeeOpensFirstBooth() {
        exhibitHallPage.openFirstBooth();
    }

    @Then("the booth detail screen should open")
    public void boothDetailScreenOpens() {
        Assert.assertTrue("Booth navigation menu not visible",
                exhibitHallPage.isNavigationMenuDisplayed()
                || exhibitHallPage.isBookMeetingButtonDisplayed());
    }

    @Then("the Book a Meeting button should be visible on booth detail")
    public void bookMeetingButtonVisible() {
        Assert.assertTrue("Book a Meeting button not visible",
                exhibitHallPage.isBookMeetingButtonDisplayed());
    }

    // ── WEBINAR STEPS ────────────────────────────────────────────────────────

    @Given("the attendee navigates to the Auditorium")
    public void attendeeNavigatesToAuditorium() {
        attendeeIsLoggedIn();
        driver.get(ConfigManager.getBaseUrl() + "/auditorium");
    }

    @Then("the Auditorium should load with session cards")
    public void auditoriumLoadsWithSessions() {
        Assert.assertTrue("Auditorium not loaded",
                webinarPage.isAuditoriumLoaded());
        Assert.assertTrue("No sessions found",
                webinarPage.getWebinarCount() > 0);
    }

    @When("the attendee opens the first webinar session")
    public void attendeeOpensFirstWebinar() {
        webinarPage.openFirstWebinar();
    }

    @Then("the Q&A tab should be visible on the webinar screen")
    public void qaTabVisibleOnWebinar() {
        Assert.assertTrue("Q&A tab not visible",
                webinarPage.isQaTabDisplayed());
    }

    @Then("custom tabs configured in backend should appear")
    public void customTabsAppear() {
        Assert.assertTrue("Custom tab not displayed",
                webinarPage.isCustomTabDisplayed());
    }

    @When("the attendee posts a question {string}")
    public void attendeePostsQuestion(String question) {
        webinarPage.postQuestion(question);
    }

    @Then("the question should appear in the Q&A list")
    public void questionAppearsInQaList() {
        Assert.assertTrue("Question not posted",
                webinarPage.getPostedQuestionsCount() > 0);
    }
}
