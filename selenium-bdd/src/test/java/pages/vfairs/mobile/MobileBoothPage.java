package pages.vfairs.mobile;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

/**
 * MobileBoothPage - vFairs Android Booth/Exhibit Hall screen.
 * Real test cases from TestRail:
 *   C123 - Exhibit Hall Screen info
 *   C124 - Individual exhibitor detail
 *   C125 - Company Tab
 *   C132 - Directional arrows next/prev booth
 *   C133 - Navigation menu bar
 *   C134 - Online users section
 *   C135 - Video Tab
 *   C136 - Documentation Tab
 *   C515 - Speaker heading hidden when no speaker
 *   C1138 - Book a meeting tab on booth detail
 */
public class MobileBoothPage {

    private AndroidDriver driver;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Exhibit Hall') or contains(@text,'Booths')]")
    private MobileElement exhibitHallTitle;

    @AndroidFindBy(xpath = "//android.widget.RelativeLayout[contains(@resource-id,'booth_item')] | //android.view.ViewGroup[@clickable='true']")
    private List<MobileElement> boothCards;

    @AndroidFindBy(id = "com.vfairs.app:id/booth_logo")
    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='booth logo']")
    private MobileElement boothLogo;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'booth_title') or contains(@resource-id,'booth_name')]")
    private MobileElement boothTitle;

    // Tabs on booth detail
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Company' or @text='COMPANY']")
    private MobileElement companyTab;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Videos' or @text='VIDEO' or @text='Video']")
    private MobileElement videoTab;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Documents' or @text='DOCUMENTS' or @text='Docs']")
    private MobileElement documentsTab;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Book a Meeting' or @text='BOOK A MEETING']")
    private MobileElement bookMeetingTab;

    // Navigation arrows
    @AndroidFindBy(id = "com.vfairs.app:id/btn_prev_booth")
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Previous booth']")
    private MobileElement prevBoothArrow;

    @AndroidFindBy(id = "com.vfairs.app:id/btn_next_booth")
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Next booth']")
    private MobileElement nextBoothArrow;

    // Navigation menu bar
    @AndroidFindBy(id = "com.vfairs.app:id/bottom_navigation")
    @AndroidFindBy(xpath = "//android.widget.LinearLayout[contains(@resource-id,'nav')]")
    private MobileElement navigationMenuBar;

    // Online users
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Online') or contains(@text,'online')]")
    private MobileElement onlineUsersText;

    // Book a meeting elements
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Book a Meeting' or @text='Reserve a chat slot']")
    private MobileElement bookMeetingButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Select booth representative') or contains(@text,'Select Rep')]")
    private MobileElement selectRepHeading;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Select time') or contains(@text,'Time slot')]")
    private MobileElement selectTimeHeading;

    // Speaker heading (C515 - should be hidden if no speaker)
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Speakers' or @text='SPEAKERS']")
    private List<MobileElement> speakerHeadings;

    public MobileBoothPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(
            new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);
    }

    // C123
    public boolean isExhibitHallVisible() {
        try { return exhibitHallTitle.isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public int getBoothCount() {
        return boothCards.size();
    }

    // C124
    public void openFirstBooth() {
        if (!boothCards.isEmpty()) boothCards.get(0).click();
    }

    public boolean isBoothLogoVisible() {
        try { return boothLogo.isDisplayed(); }
        catch (Exception e) { return false; }
    }

    // C132
    public void tapNextBooth() { nextBoothArrow.click(); }
    public void tapPrevBooth() { prevBoothArrow.click(); }
    public boolean isNextArrowVisible() {
        try { return nextBoothArrow.isDisplayed(); } catch (Exception e) { return false; }
    }

    // C125
    public void tapCompanyTab() { companyTab.click(); }

    // C135
    public void tapVideoTab() { videoTab.click(); }

    // C136
    public void tapDocumentsTab() { documentsTab.click(); }

    // C133
    public boolean isNavigationMenuVisible() {
        try { return navigationMenuBar.isDisplayed(); } catch (Exception e) { return false; }
    }

    // C134
    public boolean isOnlineUsersSectionVisible() {
        try { return onlineUsersText.isDisplayed(); } catch (Exception e) { return false; }
    }

    // C1138
    public boolean isBookMeetingTabVisible() {
        try { return bookMeetingTab.isDisplayed(); } catch (Exception e) { return false; }
    }

    public void tapBookMeeting() { bookMeetingButton.click(); }

    public boolean isSelectRepHeadingVisible() {
        try { return selectRepHeading.isDisplayed(); } catch (Exception e) { return false; }
    }

    // C515 - Speaker heading should NOT appear when no speaker configured
    public boolean isSpeakerHeadingHidden() {
        return speakerHeadings.isEmpty();
    }
}
