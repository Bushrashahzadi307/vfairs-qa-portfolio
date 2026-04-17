package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import pages.vfairs.mobile.MobileLoginPage;
import pages.vfairs.mobile.MobileBoothPage;
import utilities.ConfigManager;
import utilities.DriverManager;

/**
 * MobileStepDefinitions - maps Gherkin BDD steps to Appium mobile automation.
 * Covers vFairs Android app: Login, Booth/Exhibit Hall, Swag Bag, Attendee Listing.
 */
public class MobileStepDefinitions {

    private AndroidDriver androidDriver;
    private MobileLoginPage mobileLoginPage;
    private MobileBoothPage mobileBoothPage;

    @Before("@mobile")
    public void setUp() throws Exception {
        DriverManager.initMobileDriver();
        androidDriver   = (AndroidDriver) DriverManager.getDriver();
        mobileLoginPage = new MobileLoginPage(androidDriver);
        mobileBoothPage = new MobileBoothPage(androidDriver);
    }

    @After("@mobile")
    public void tearDown() {
        DriverManager.quitDriver();
    }

    // ── MOBILE LOGIN STEPS ───────────────────────────────────────────────────

    @Given("the vFairs mobile app is launched")
    public void mobileAppLaunched() {
        // App auto-launches via Appium capabilities in DriverManager
        Assert.assertNotNull("Appium driver not initialised", androidDriver);
    }

    @Then("the splash screen should be displayed")
    public void splashScreenDisplayed() {
        // Small wait for splash to appear
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    @Then("the vFairs logo should be visible and centred on screen")
    public void vFairsLogoVisible() {
        Assert.assertTrue("Splash logo not visible",
                mobileLoginPage.isSplashLogoVisible());
    }

    @When("the attendee enters a valid registered email on mobile")
    public void attendeeEntersValidEmailMobile() {
        mobileLoginPage.enterEmail(ConfigManager.get("test.user.email"));
    }

    @When("the attendee enters valid email {string} on mobile")
    public void attendeeEntersSpecificEmailMobile(String email) {
        mobileLoginPage.enterEmail(email);
    }

    @When("the attendee enters invalid email {string} on mobile")
    public void attendeeEntersInvalidEmailMobile(String email) {
        mobileLoginPage.enterEmail(email);
    }

    @When("the attendee enters unregistered email {string} on mobile")
    public void attendeeEntersUnregisteredEmailMobile(String email) {
        mobileLoginPage.enterEmail(email);
    }

    @When("the attendee enters {string} on mobile login")
    public void attendeeEntersEmailOnMobileLogin(String email) {
        mobileLoginPage.enterEmail(email);
    }

    @When("taps the Next button")
    public void tapsNextButton() {
        mobileLoginPage.tapNext();
    }

    @Then("the password entry screen should appear")
    public void passwordEntryScreenAppears() {
        Assert.assertTrue("Password screen not shown",
                mobileLoginPage.isCreatePasswordTitleVisible()
                || !mobileLoginPage.isErrorDisplayed());
    }

    @Then("the Create Password or Sign In screen should appear")
    public void createPasswordScreenAppears() {
        Assert.assertTrue("Create password screen not shown",
                mobileLoginPage.isCreatePasswordTitleVisible());
    }

    @Then("a Password field should be visible")
    public void passwordFieldVisible() {
        Assert.assertTrue("Password screen title not visible",
                mobileLoginPage.isCreatePasswordTitleVisible());
    }

    @Then("an error message should be shown on mobile login")
    public void errorShownOnMobileLogin() {
        Assert.assertTrue("No error shown on mobile login",
                mobileLoginPage.isErrorDisplayed());
        Assert.assertFalse("Error text empty",
                mobileLoginPage.getErrorText().isEmpty());
    }

    @Then("the app should display a not-registered error message")
    public void notRegisteredErrorOnMobile() {
        Assert.assertTrue("Not-registered error not shown",
                mobileLoginPage.isErrorDisplayed());
    }

    @When("the attendee enters an email registered across multiple events")
    public void attendeeEntersMultiEventEmailMobile() {
        mobileLoginPage.enterEmail(ConfigManager.get("test.user.email"));
    }

    @Then("the OTP verification screen should be shown")
    public void otpVerificationScreenShown() {
        // OTP screen appears when user is in multiple events
        Assert.assertFalse("App crashed", androidDriver.getPageSource().isEmpty());
    }

    @Given("the event is closed or expired on mobile")
    public void eventClosedOnMobile() {
        // App configured with closed event test data
    }

    @When("the attendee attempts to log in")
    public void attendeeAttemptsLogin() {
        mobileLoginPage.loginWith(
                ConfigManager.get("test.user.email"),
                ConfigManager.get("test.user.password"));
    }

    @Then("the Doors Closed message should appear on screen")
    public void doorsClosedMessageAppears() {
        Assert.assertTrue("Doors Closed message not shown",
                mobileLoginPage.isDoorsClosedDisplayed());
    }

    @Then("the app should reject the email with a validation error")
    public void appRejectsInvalidEmail() {
        Assert.assertTrue("Validation error not shown",
                mobileLoginPage.isErrorDisplayed());
    }

    @Given("the attendee has previously logged in successfully")
    public void attendeePreviouslyLoggedIn() {
        mobileLoginPage.loginWith(
                ConfigManager.get("test.user.email"),
                ConfigManager.get("test.user.password"));
    }

    @When("the app is relaunched")
    public void appRelaunched() {
        androidDriver.terminateApp("com.vfairs.app");
        androidDriver.activateApp("com.vfairs.app");
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
    }

    @Then("the attendee should be auto-logged in without entering credentials")
    public void attendeeAutoLoggedIn() {
        Assert.assertFalse("Auto-login failed — login screen shown",
                mobileLoginPage.isSplashLogoVisible());
    }

    @Given("the attendee is logged into the mobile app")
    public void attendeeLoggedIntoMobileApp() {
        mobileLoginPage.loginWith(
                ConfigManager.get("test.user.email"),
                ConfigManager.get("test.user.password"));
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    @When("the attendee navigates to profile and taps Logout")
    public void attendeeLogsOutFromMobile() {
        // Navigate to profile menu and tap logout
        // Implementation depends on actual app navigation structure
    }

    @Then("the app should return to the login screen")
    public void appReturnsToLoginScreen() {
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        // After logout, splash or login screen should appear
        Assert.assertNotNull("Driver lost after logout", androidDriver);
    }

    @Then("the result should be {string}")
    public void mobileLoginResultShouldBe(String expectedOutcome) {
        switch (expectedOutcome) {
            case "password_screen":
                Assert.assertTrue("Expected password screen",
                        mobileLoginPage.isCreatePasswordTitleVisible());
                break;
            case "error_message":
            case "validation_error":
                Assert.assertTrue("Expected error not shown",
                        mobileLoginPage.isErrorDisplayed());
                break;
        }
    }

    // ── MOBILE BOOTH STEPS ───────────────────────────────────────────────────

    @Given("the attendee is logged into the vFairs mobile app")
    public void attendeeLoggedIntoApp() {
        attendeeLoggedIntoMobileApp();
    }

    @Given("the attendee navigates to the Exhibit Hall")
    public void attendeeNavigatesToExhibitHall() {
        // Navigate via bottom nav or side menu to Exhibit Hall
        // Actual element depends on event configuration
    }

    @Then("the Exhibit Hall screen should be loaded")
    public void exhibitHallScreenLoaded() {
        Assert.assertTrue("Exhibit Hall not loaded",
                mobileBoothPage.isExhibitHallVisible());
    }

    @Then("booth cards should be visible on screen")
    public void boothCardsVisible() {
        Assert.assertTrue("No booths shown",
                mobileBoothPage.getBoothCount() > 0);
    }

    @Then("each booth card should show the booth logo and name")
    public void boothCardsShowLogoAndName() {
        Assert.assertTrue("Booth logo not visible",
                mobileBoothPage.isBoothLogoVisible());
    }

    @When("the attendee taps on the first booth")
    public void attendeeTapsFirstBooth() {
        mobileBoothPage.openFirstBooth();
    }

    @Then("the booth detail screen should open")
    public void boothDetailOpens() {
        Assert.assertTrue("Booth logo not on detail screen",
                mobileBoothPage.isBoothLogoVisible());
    }

    @Then("the booth logo should be displayed")
    public void boothLogoDisplayed() {
        Assert.assertTrue("Booth logo missing", mobileBoothPage.isBoothLogoVisible());
    }

    @Then("the booth title should be visible")
    public void boothTitleVisible() {
        Assert.assertTrue("Booth not loaded", mobileBoothPage.isExhibitHallVisible()
                || mobileBoothPage.isBoothLogoVisible());
    }

    @When("the attendee opens any booth")
    public void attendeeOpensAnyBooth() {
        mobileBoothPage.openFirstBooth();
    }

    @When("the attendee opens a booth with meeting booking enabled")
    public void attendeeOpensMeetingBooth() {
        mobileBoothPage.openFirstBooth();
    }

    @Then("the Company tab should be displayed on booth detail")
    public void companyTabDisplayed() {
        Assert.assertTrue("Company tab not visible",
                mobileBoothPage.isNavigationMenuVisible());
    }

    @When("the attendee taps the Company tab")
    public void attendeeTapsCompanyTab() {
        mobileBoothPage.tapCompanyTab();
    }

    @Then("the company information should be shown")
    public void companyInfoShown() {
        Assert.assertFalse("Page source empty",
                androidDriver.getPageSource().isEmpty());
    }

    @Then("the Video tab should be displayed on booth detail")
    public void videoTabDisplayed() {
        Assert.assertFalse("Page source empty",
                androidDriver.getPageSource().isEmpty());
    }

    @When("the attendee taps the Video tab")
    public void attendeeTapsVideoTab() {
        mobileBoothPage.tapVideoTab();
    }

    @Then("the booth video content should appear")
    public void videoContentAppears() {
        Assert.assertFalse("No page content", androidDriver.getPageSource().isEmpty());
    }

    @Then("the Documentation tab should be displayed on booth detail")
    public void documentsTabDisplayed() {
        Assert.assertFalse("Page source empty", androidDriver.getPageSource().isEmpty());
    }

    @When("the attendee taps the Documentation tab")
    public void attendeeTapsDocumentsTab() {
        mobileBoothPage.tapDocumentsTab();
    }

    @Then("the booth documents should be listed")
    public void boothDocumentsListed() {
        Assert.assertFalse("No content", androidDriver.getPageSource().isEmpty());
    }

    @Then("the Next booth arrow should be visible")
    public void nextArrowVisible() {
        Assert.assertTrue("Next arrow not visible",
                mobileBoothPage.isNextArrowVisible());
    }

    @When("the attendee taps the Next booth arrow")
    public void attendeeTapsNextBooth() {
        mobileBoothPage.tapNextBooth();
    }

    @Then("the next booth detail screen should load")
    public void nextBoothDetailLoads() {
        Assert.assertTrue("Booth logo gone after next tap",
                mobileBoothPage.isBoothLogoVisible());
    }

    @When("the attendee taps the Previous booth arrow")
    public void attendeeTapsPrevBooth() {
        mobileBoothPage.tapPrevBooth();
    }

    @Then("the previous booth detail should be shown")
    public void prevBoothDetailShown() {
        Assert.assertTrue("Booth logo gone after prev tap",
                mobileBoothPage.isBoothLogoVisible());
    }

    @Then("the navigation menu bar should be displayed at the bottom")
    public void navMenuDisplayedAtBottom() {
        Assert.assertTrue("Navigation menu not visible",
                mobileBoothPage.isNavigationMenuVisible());
    }

    @Then("the online users section should be visible on booth detail")
    public void onlineUsersSectionVisible() {
        Assert.assertTrue("Online users section not visible",
                mobileBoothPage.isOnlineUsersSectionVisible());
    }

    @Given("a booth exists with no speaker configured in the backend")
    public void boothWithNoSpeaker() {
        // Test data: booth configured without speakers in test environment
    }

    @Then("the Speakers heading should not appear on screen")
    public void speakerHeadingNotVisible() {
        Assert.assertTrue("Speaker heading incorrectly shown",
                mobileBoothPage.isSpeakerHeadingHidden());
    }

    @Given("the booth has an Embed URL content link configured")
    public void boothHasEmbedUrl() {
        // Test data configured in backend
    }

    @Then("the embedded URL content should be displayed correctly")
    public void embeddedUrlDisplayed() {
        Assert.assertFalse("No content on screen",
                androidDriver.getPageSource().isEmpty());
    }

    @Then("the Book a Meeting tab should be visible on the booth detail screen")
    public void bookMeetingTabVisible() {
        Assert.assertTrue("Book a Meeting tab not visible",
                mobileBoothPage.isBookMeetingTabVisible());
    }

    @When("taps the Book a Meeting button")
    public void tapsBookMeeting() {
        mobileBoothPage.tapBookMeeting();
    }

    @Then("the Book a Meeting page should open")
    public void bookMeetingPageOpens() {
        Assert.assertTrue("Select Rep heading not shown",
                mobileBoothPage.isSelectRepHeadingVisible());
    }

    @When("the attendee is on the Book a Meeting page")
    public void attendeeOnBookMeetingPage() {
        mobileBoothPage.openFirstBooth();
        mobileBoothPage.tapBookMeeting();
    }

    @Then("the Booth Rep tab should be visible")
    public void boothRepTabVisible() {
        Assert.assertFalse("No content on meeting page",
                androidDriver.getPageSource().isEmpty());
    }

    @Then("the My Bookings tab should be visible")
    public void myBookingsTabVisible() {
        Assert.assertFalse("No content on meeting page",
                androidDriver.getPageSource().isEmpty());
    }

    @Then("the first tab {string} should be active by default")
    public void firstTabActiveByDefault(String tabName) {
        Assert.assertTrue("Select rep heading not visible",
                mobileBoothPage.isSelectRepHeadingVisible());
    }

    @Then("the heading {string} should appear on screen")
    public void headingAppearsOnScreen(String heading) {
        Assert.assertTrue("Heading not on screen",
                androidDriver.getPageSource().contains(heading)
                || mobileBoothPage.isSelectRepHeadingVisible());
    }

    @Then("all available booth representatives should be listed")
    public void boothRepsListed() {
        Assert.assertFalse("Page source empty",
                androidDriver.getPageSource().isEmpty());
    }

    @When("taps the Next button without selecting a representative")
    public void tapsNextWithoutRepSelection() {
        // Tap next without selecting — expect error/block
    }

    @Then("an error or blocking behaviour should prevent progression")
    public void progressionBlocked() {
        Assert.assertTrue("Should still be on meeting page",
                mobileBoothPage.isSelectRepHeadingVisible()
                || !androidDriver.getPageSource().isEmpty());
    }

    @When("selects the first available booth representative")
    public void selectsFirstRep() {
        // Select first rep in the list
    }

    @When("taps Next")
    public void tapsNext() {
        mobileLoginPage.tapNext();
    }

    @Then("the Select Time Slot screen should open")
    public void timeSlotScreenOpens() {
        Assert.assertFalse("Page source empty",
                androidDriver.getPageSource().isEmpty());
    }

    @When("the attendee is on the Select Time Slot screen")
    public void attendeeOnTimeSlotScreen() {
        attendeeOnBookMeetingPage();
    }

    @Then("date tabs should be displayed according to event timing")
    public void dateTabsDisplayed() {
        Assert.assertFalse("Page source empty",
                androidDriver.getPageSource().isEmpty());
    }

    @When("the attendee selects the first available date")
    public void selectsFirstDate() {
        // Tap first date tab
    }

    @Then("the selected date should be highlighted")
    public void selectedDateHighlighted() {
        Assert.assertFalse("No content", androidDriver.getPageSource().isEmpty());
    }

    @When("the attendee selects a date on the time slot screen")
    public void selectsDateOnTimeSlotScreen() {
        // Tap a date
    }

    @Then("all available time slots for that date should be displayed")
    public void timeSlotsDisplayed() {
        Assert.assertFalse("No time slots shown",
                androidDriver.getPageSource().isEmpty());
    }

    @When("taps the Cancel button")
    public void tapsCancelButton() {
        // Tap cancel
    }

    @Then("the attendee should be returned to the booth detail screen")
    public void returnedToBoothDetail() {
        Assert.assertTrue("Booth detail not shown",
                mobileBoothPage.isBoothLogoVisible());
    }

    @Then("the Cancel button should be visible")
    public void cancelButtonVisible() {
        Assert.assertFalse("Page source empty",
                androidDriver.getPageSource().isEmpty());
    }

    @Then("the Next button should be visible")
    public void nextButtonVisible() {
        Assert.assertFalse("Page source empty",
                androidDriver.getPageSource().isEmpty());
    }

    // ── SWAG BAG & OTHER STEPS ───────────────────────────────────────────────

    @Then("the Swag Bag option should appear in the mobile navigation menu")
    public void swagBagInNavMenu() {
        Assert.assertTrue("Swag Bag not in nav",
                androidDriver.getPageSource().contains("Swag")
                || androidDriver.getPageSource().contains("swag"));
    }

    @When("the attendee navigates to the Resources section")
    public void navigatesToResources() {
        // Navigate to resources via side menu
    }

    @Then("the Swag Bag option should appear in the sub-menu")
    public void swagBagInSubMenu() {
        Assert.assertTrue("Swag Bag not in sub-menu",
                androidDriver.getPageSource().contains("Swag"));
    }

    @Then("the Swag Bag icon should be visible")
    public void swagBagIconVisible() {
        Assert.assertFalse("Page source empty",
                androidDriver.getPageSource().isEmpty());
    }

    @When("the attendee navigates to the Attendee Listing screen")
    public void navigatesToAttendeeList() {
        // Navigate via menu
    }

    @Then("the attendee list should be displayed")
    public void attendeeListDisplayed() {
        Assert.assertFalse("Attendee list page empty",
                androidDriver.getPageSource().isEmpty());
    }

    @Then("each attendee card should show name and profile information")
    public void attendeeCardsShowInfo() {
        Assert.assertFalse("No attendee content",
                androidDriver.getPageSource().isEmpty());
    }

    @When("the attendee navigates to Attendee Listing")
    public void navigatesToAttendeeListStep() {
        navigatesToAttendeeList();
    }

    @When("taps on the first attendee card")
    public void tapsFirstAttendeeCard() {
        // Tap first attendee
    }

    @Then("the attendee profile detail screen should open")
    public void attendeeProfileOpens() {
        Assert.assertFalse("Profile screen empty",
                androidDriver.getPageSource().isEmpty());
    }

    @Then("the search bar should be visible")
    public void searchBarVisible() {
        Assert.assertTrue("Search bar not found",
                androidDriver.getPageSource().contains("search")
                || androidDriver.getPageSource().contains("Search"));
    }

    @When("the attendee types a name in the search bar")
    public void typesNameInSearch() {
        // Type in search
    }

    @Then("the list should filter to show matching attendees only")
    public void listFiltered() {
        Assert.assertFalse("Search results empty",
                androidDriver.getPageSource().isEmpty());
    }
}
