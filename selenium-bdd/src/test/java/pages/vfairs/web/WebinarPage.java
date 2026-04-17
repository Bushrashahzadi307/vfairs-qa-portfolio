package pages.vfairs.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.BasePage;

import java.util.List;

/**
 * WebinarPage - vFairs Auditorium / Webinar session screen.
 * Covers webinar listing, session play, Q&A, and custom tabs.
 * Test cases: C547 (custom webinar tab), C548 (all tabs: Q&A, Wordly, Interprefy).
 */
public class WebinarPage extends BasePage {

    @FindBy(css = ".auditorium, #auditorium, .webinar-container, [class*='webinar']")
    private WebElement auditoriumContainer;

    @FindBy(css = ".webinar-card, .session-card, [class*='session-item']")
    private List<WebElement> webinarCards;

    @FindBy(css = ".webinar-title, .session-title, h2.title")
    private WebElement webinarTitle;

    @FindBy(css = ".video-player, video, iframe[src*='youtube'], iframe[src*='vimeo']")
    private WebElement videoPlayer;

    // Webinar tabs (C547, C548)
    @FindBy(css = ".tab-qa, [data-tab='qa'], button[id*='qa']")
    private WebElement qaTab;

    @FindBy(css = ".tab-custom, [data-tab='custom']")
    private List<WebElement> customTabs;

    @FindBy(css = ".wordly-tab, [data-tab='wordly']")
    private WebElement wordlyTab;

    @FindBy(css = ".interprefy-tab, [data-tab='interprefy']")
    private WebElement interprefyTab;

    // Q&A elements
    @FindBy(css = ".qa-input, textarea[placeholder*='question' i]")
    private WebElement qaInput;

    @FindBy(css = ".qa-submit, button[class*='post-question']")
    private WebElement qaSubmitButton;

    @FindBy(css = ".qa-list, .questions-list, [class*='qa-item']")
    private List<WebElement> qaQuestions;

    // Schedule
    @FindBy(css = ".add-to-schedule, .bookmark-btn, [class*='add-schedule']")
    private WebElement addToScheduleButton;

    @FindBy(css = ".my-schedule, [class*='my-schedule']")
    private WebElement myScheduleSection;

    // Search in auditorium
    @FindBy(css = ".search-auditorium, input[placeholder*='search' i]")
    private WebElement auditoriumSearch;

    public WebinarPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAuditoriumLoaded() {
        return isDisplayed(auditoriumContainer);
    }

    public int getWebinarCount() {
        return webinarCards.size();
    }

    public void openFirstWebinar() {
        if (!webinarCards.isEmpty()) {
            click(webinarCards.get(0));
        }
    }

    // C548: All standard tabs visible
    public boolean isQaTabDisplayed() {
        return isDisplayed(qaTab);
    }

    public boolean isWordlyTabDisplayed() {
        return isDisplayed(wordlyTab);
    }

    public boolean isInterprefyTabDisplayed() {
        return isDisplayed(interprefyTab);
    }

    // C547: Custom tab added
    public boolean isCustomTabDisplayed() {
        return !customTabs.isEmpty();
    }

    public int getCustomTabCount() {
        return customTabs.size();
    }

    public void clickQaTab() {
        click(qaTab);
    }

    public void postQuestion(String question) {
        click(qaTab);
        type(qaInput, question);
        click(qaSubmitButton);
    }

    public int getPostedQuestionsCount() {
        return qaQuestions.size();
    }

    public void clickAddToSchedule() {
        click(addToScheduleButton);
    }

    public boolean isVideoPlayerDisplayed() {
        return isDisplayed(videoPlayer);
    }

    public void searchWebinar(String keyword) {
        type(auditoriumSearch, keyword);
    }
}
