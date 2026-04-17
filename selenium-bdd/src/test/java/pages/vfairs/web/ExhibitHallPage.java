package pages.vfairs.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.BasePage;

import java.util.List;

/**
 * ExhibitHallPage - vFairs Exhibit Hall (Booth Listing).
 * Covers booth navigation, search, filters, and booth detail access.
 * Test cases: C123 (Exhibit Hall Screen), C124 (Booth details), C125 (Company tab),
 *             C132 (Directional arrows), C133 (Navigation menu), C515 (Speaker heading).
 */
public class ExhibitHallPage extends BasePage {

    @FindBy(css = ".exhibit-hall, #exhibit-hall, .hall-container, [class*='exhibit']")
    private WebElement exhibitHallContainer;

    @FindBy(css = ".booth-card, .exhibitor-card, [class*='booth-item']")
    private List<WebElement> boothCards;

    @FindBy(css = "input[placeholder*='search' i], .search-input, #booth-search")
    private WebElement searchInput;

    @FindBy(css = ".search-btn, button[type='submit']")
    private WebElement searchButton;

    @FindBy(css = ".floor-tab, .floor-selector, [class*='floor']")
    private List<WebElement> floorTabs;

    @FindBy(css = ".booth-name, .booth-title, [class*='booth-name']")
    private List<WebElement> boothNames;

    @FindBy(css = ".booth-logo, .exhibitor-logo, [class*='booth-logo']")
    private List<WebElement> boothLogos;

    @FindBy(css = ".prev-booth, .arrow-left, [class*='prev']")
    private WebElement prevBoothArrow;

    @FindBy(css = ".next-booth, .arrow-right, [class*='next']")
    private WebElement nextBoothArrow;

    // Booth detail elements
    @FindBy(css = ".company-tab, [data-tab='company'], button[id*='company']")
    private WebElement companyTab;

    @FindBy(css = ".video-tab, [data-tab='video'], button[id*='video']")
    private WebElement videoTab;

    @FindBy(css = ".documents-tab, [data-tab='docs'], button[id*='doc']")
    private WebElement documentsTab;

    @FindBy(css = ".nav-menu, .booth-nav, [class*='navigation']")
    private WebElement navigationMenuBar;

    @FindBy(css = ".online-users, [class*='online-user']")
    private WebElement onlineUsersSection;

    @FindBy(css = ".book-meeting-btn, [class*='book-meeting'], a[href*='meeting']")
    private WebElement bookMeetingButton;

    @FindBy(css = ".leave-message-btn, [class*='leave-message']")
    private WebElement leaveMessageButton;

    @FindBy(css = ".send-card-btn, [class*='business-card']")
    private WebElement sendBusinessCardButton;

    public ExhibitHallPage(WebDriver driver) {
        super(driver);
    }

    // C123: Exhibit Hall screen loads
    public boolean isExhibitHallLoaded() {
        return isDisplayed(exhibitHallContainer);
    }

    public int getBoothCount() {
        return boothCards.size();
    }

    // C124: Open first booth
    public void openFirstBooth() {
        if (!boothCards.isEmpty()) {
            click(boothCards.get(0));
        }
    }

    public void openBoothByName(String boothName) {
        for (WebElement name : boothNames) {
            if (getText(name).equalsIgnoreCase(boothName)) {
                click(name);
                return;
            }
        }
        throw new RuntimeException("Booth not found: " + boothName);
    }

    // Search functionality
    public void searchBooth(String keyword) {
        type(searchInput, keyword);
        click(searchButton);
    }

    public boolean areBoothsVisible() {
        return !boothCards.isEmpty();
    }

    // C132: Directional arrows
    public void clickNextBooth() {
        click(nextBoothArrow);
    }

    public void clickPrevBooth() {
        click(prevBoothArrow);
    }

    public boolean isNextArrowDisplayed() {
        return isDisplayed(nextBoothArrow);
    }

    // C125: Company tab
    public void clickCompanyTab() {
        click(companyTab);
    }

    // C135: Video tab
    public void clickVideoTab() {
        click(videoTab);
    }

    // C136: Documents tab
    public void clickDocumentsTab() {
        click(documentsTab);
    }

    // C133: Navigation menu bar
    public boolean isNavigationMenuDisplayed() {
        return isDisplayed(navigationMenuBar);
    }

    // C134: Online users
    public boolean isOnlineUsersSectionDisplayed() {
        return isDisplayed(onlineUsersSection);
    }

    // Book a meeting
    public void clickBookMeeting() {
        scrollToElement(bookMeetingButton);
        click(bookMeetingButton);
    }

    public boolean isBookMeetingButtonDisplayed() {
        return isDisplayed(bookMeetingButton);
    }

    public void clickLeaveMessage() {
        click(leaveMessageButton);
    }

    public void clickSendBusinessCard() {
        click(sendBusinessCardButton);
    }

    // C515: Speaker heading hidden when no speaker
    public boolean isSpeakerHeadingHidden() {
        return !isElementPresent(By.cssSelector(".speaker-heading, [class*='speaker-title']"));
    }
}
