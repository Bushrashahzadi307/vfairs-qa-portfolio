package pages.vfairs.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.BasePage;
import utilities.ConfigManager;

/**
 * LoginPage - vFairs virtual event web platform login.
 * vFairs uses a two-step login: email first, then password/OTP.
 * Test cases derived from real TestRail suite (C111, C112, C114, C115, C193).
 */
public class LoginPage extends BasePage {

    @FindBy(css = "input[type='email'], input[name='email'], input[placeholder*='email' i]")
    private WebElement emailField;

    @FindBy(css = "input[type='password'], input[name='password']")
    private WebElement passwordField;

    @FindBy(css = "input[name='otp'], input[placeholder*='OTP' i], input[placeholder*='code' i]")
    private WebElement otpField;

    @FindBy(css = "button[type='submit'], .btn-login, .login-btn, button.primary")
    private WebElement submitButton;

    @FindBy(css = ".error-message, .alert-danger, [class*='error'], [class*='invalid']")
    private WebElement errorMessage;

    @FindBy(css = "a[href*='forgot'], .forgot-password-link")
    private WebElement forgotPasswordLink;

    @FindBy(css = ".doors-closed, [class*='doors-closed'], .event-closed-msg")
    private WebElement doorsClosedMessage;

    @FindBy(css = ".lobby-container, .event-lobby, #lobby")
    private WebElement lobbyContainer;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        driver.get(ConfigManager.getBaseUrl());
    }

    public void enterEmail(String email) {
        type(emailField, email);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void enterOtp(String otp) {
        type(otpField, otp);
    }

    public void clickSubmit() {
        click(submitButton);
    }

    // C111: Valid login flow
    public void loginWithCredentials(String email, String password) {
        enterEmail(email);
        clickSubmit();
        enterPassword(password);
        clickSubmit();
    }

    // C193: Invalid email login
    public void loginWithEmailOnly(String email) {
        enterEmail(email);
        clickSubmit();
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    // C117: Doors closed check
    public boolean isDoorsClosedMessageDisplayed() {
        return isDisplayed(doorsClosedMessage);
    }

    public boolean isLoggedIn() {
        return isDisplayed(lobbyContainer) ||
               getCurrentUrl().contains("lobby") ||
               getCurrentUrl().contains("hall");
    }

    public void clickForgotPassword() {
        click(forgotPasswordLink);
    }

    // C415: Forgot password link visible
    public boolean isForgotPasswordDisplayed() {
        return isDisplayed(forgotPasswordLink);
    }
}
