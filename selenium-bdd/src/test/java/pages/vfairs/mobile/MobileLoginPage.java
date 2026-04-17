package pages.vfairs.mobile;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import utilities.ConfigManager;

import java.time.Duration;

/**
 * MobileLoginPage - vFairs Android app login screen.
 * vFairs mobile uses two-step login: email → password/OTP screen.
 * Real test cases from TestRail:
 *   C111 - Valid email login
 *   C112 - Password screen after email validation
 *   C114 - Invalid email validation message
 *   C115 - Unregistered email error
 *   C116 - OTP required for multiple events
 *   C193 - Invalid email rejected
 *   C473 - Logout functionality
 */
public class MobileLoginPage {

    private AndroidDriver driver;

    // Step 1 - Email screen
    @AndroidFindBy(accessibility = "Email Input")
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Email' or @hint='Enter your email']")
    private MobileElement emailField;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Next' or @text='NEXT']")
    @AndroidFindBy(id = "com.vfairs.app:id/btn_next")
    private MobileElement nextButton;

    // Step 2 - Password screen
    @AndroidFindBy(accessibility = "Password Input")
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Password' or @hint='Enter password']")
    private MobileElement passwordField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='OTP' or @hint='Enter OTP']")
    private MobileElement otpField;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Sign In' or @text='LOGIN' or @text='Log In']")
    private MobileElement signInButton;

    // Error/validation messages
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'invalid') or contains(@text,'Invalid') or contains(@text,'not registered')]")
    private MobileElement errorText;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Create Password') or contains(@text,'create password')]")
    private MobileElement createPasswordTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Doors Closed') or contains(@text,'Event is closed')]")
    private MobileElement doorsClosedText;

    // Splash screen (C113)
    @AndroidFindBy(id = "com.vfairs.app:id/splash_logo")
    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'vFairs') or contains(@content-desc,'logo')]")
    private MobileElement splashLogo;

    public MobileLoginPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(
            new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);
    }

    // C111: Enter valid email and proceed
    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void tapNext() {
        nextButton.click();
    }

    // C112: Enter password on second screen
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void tapSignIn() {
        signInButton.click();
    }

    // Full login flow
    public void loginWith(String email, String password) {
        enterEmail(email);
        tapNext();
        enterPassword(password);
        tapSignIn();
    }

    public void enterOtp(String otp) {
        otpField.clear();
        otpField.sendKeys(otp);
    }

    // C113: Splash screen visible
    public boolean isSplashLogoVisible() {
        try { return splashLogo.isDisplayed(); }
        catch (Exception e) { return false; }
    }

    // C114/C193: Error message after invalid email
    public String getErrorText() {
        try { return errorText.getText().trim(); }
        catch (Exception e) { return ""; }
    }

    public boolean isErrorDisplayed() {
        try { return errorText.isDisplayed(); }
        catch (Exception e) { return false; }
    }

    // C112: Create Password screen appears
    public boolean isCreatePasswordTitleVisible() {
        try { return createPasswordTitle.isDisplayed(); }
        catch (Exception e) { return false; }
    }

    // C117: Doors closed
    public boolean isDoorsClosedDisplayed() {
        try { return doorsClosedText.isDisplayed(); }
        catch (Exception e) { return false; }
    }
}
