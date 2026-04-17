# vFairs QA Automation Portfolio

> **Platform:** vFairs — a leading virtual event platform used by thousands of companies globally for trade shows, conferences, and job fairs.
>
> **My Role:** Senior SQA Engineer — responsible for end-to-end test strategy, automation framework design, and quality ownership across web and mobile platforms.

---

## Repository Structure

```
vfairs-qa-portfolio/
├── selenium-bdd/          # Web + Mobile UI automation (Selenium + Appium + Cucumber BDD)
└── api-rest-assured/      # API automation (REST Assured + TestNG)
```

---

## Project 1 — Selenium BDD Framework (Web + Mobile)

### What it covers

| Layer | Technology | Scope |
|-------|-----------|-------|
| Web (React platform) | Selenium Java 4 + Cucumber BDD | Login, Exhibit Hall, Webinar/Auditorium |
| Mobile (Android app) | Appium + Cucumber BDD | Login, Booth, Swag Bag, Attendee Listing, Book a Meeting |
| Design Pattern | Page Object Model (POM) | All pages extend BasePage |
| BDD | Cucumber 7 + Gherkin | 60+ scenarios across 5 feature files |
| Reporting | Extent Reports + Cucumber HTML | Auto-generated on every run |

### Real test cases covered

These scenarios are based on the actual TestRail test suite maintained during my time on the vFairs project:

**Login & Auth (Web + Mobile)**
- C111 — Valid email login flow
- C112 — Password screen appears after email validation
- C113 — Splash screen with vFairs logo
- C114 — Validation for invalid email format
- C115 — Unregistered email error
- C116 — OTP screen for multi-event users
- C117 — Doors Closed message for expired events
- C193 — Invalid email rejected
- C415 — Forgot password flow
- C464 — Auto-login for returning users
- C473 — Logout functionality

**Booth & Exhibit Hall (Mobile)**
- C123 — Exhibit Hall screen
- C124 — Individual booth detail
- C125 — Company tab
- C132 — Next/Previous booth directional arrows
- C133 — Navigation menu bar
- C134 — Online users section
- C135 — Video tab
- C136 — Documentation tab
- C515 — Speaker heading hidden when no speaker configured
- C725 — Embed URL content type
- C1138–C1167 — Full Book a Meeting / Reserve a Chat Slot flow
- C1219–C1221 — Meeting cancellation and date selection

**Swag Bag, Attendee & Webinar (Mobile)**
- C3103, C3104 — Swag Bag in navigation
- C121, C122, C129, C130, C131 — Attendee Listing
- C547, C548 — Webinar custom tabs and Q&A

### Framework structure

```
selenium-bdd/
├── pom.xml
└── src/test/
    ├── java/
    │   ├── pages/
    │   │   └── vfairs/
    │   │       ├── web/
    │   │       │   ├── LoginPage.java          (React web login — 2-step flow)
    │   │       │   ├── ExhibitHallPage.java    (Booth listing & detail)
    │   │       │   └── WebinarPage.java        (Auditorium & sessions)
    │   │       └── mobile/
    │   │           ├── MobileLoginPage.java    (Appium Android login)
    │   │           └── MobileBoothPage.java    (Appium booth & meetings)
    │   ├── stepDefinitions/
    │   │   ├── WebStepDefinitions.java
    │   │   └── MobileStepDefinitions.java
    │   ├── runners/
    │   │   ├── WebTestRunner.java
    │   │   └── MobileTestRunner.java
    │   └── utilities/
    │       ├── BasePage.java         (parent POM with explicit waits)
    │       ├── DriverManager.java    (Chrome, Firefox, Appium Android)
    │       └── ConfigManager.java    (externalised config)
    └── resources/
        ├── config.properties         (⚠ replace placeholders before running)
        └── features/
            ├── web/
            │   └── VFairsWebLogin.feature
            └── mobile/
                ├── MobileLogin.feature
                ├── MobileBooth.feature
                └── MobileSwagBagAndFeatures.feature
```

### How to run

#### Prerequisites
- Java 11+
- Maven 3.6+
- Chrome browser

#### Run web tests
```bash
cd selenium-bdd
mvn test -Dtest=WebTestRunner
```

#### Run mobile tests
```bash
# Start Appium server first
appium

# Then run
mvn test -Dtest=MobileTestRunner
```

#### Run by tag
```bash
mvn test -Dtest=WebTestRunner -Dcucumber.filter.tags="@smoke"
mvn test -Dtest=WebTestRunner -Dcucumber.filter.tags="@C111"
mvn test -Dtest=MobileTestRunner -Dcucumber.filter.tags="@booth"
```

#### View reports
```
target/reports/web-cucumber.html
target/reports/mobile-cucumber.html
```

### Setup — config.properties

Before running, open `src/test/resources/config.properties` and replace:

```properties
base.url=https://your-event.vfairs.com
browser=chrome
test.user.email=your-qa-test-user@email.com
test.user.password=YourTestPassword
test.event.id=your_event_id
```

> Use a **dedicated QA test account** — never use production credentials.

---

## Project 2 — REST Assured API Framework

### What it covers

| Category | Endpoints Tested |
|----------|-----------------|
| Authentication | Login, OTP/PIN, Register, Forgot Password, Logout, Fetch Login Fields |
| Booth | Booth Info, Videos, Docs, Links, Tabs, Jobs, Reps, Available Slots, Book Meeting, Booked Slots |
| Webinar | Webinar List, Webinar Detail, Tabs, Q&A Fetch, Post Question, My Schedule |
| Swag Bag | Fetch Docs, Add to Bag, Remove from Bag, Email Bag |
| User Profile | Fetch User, Generate QR Code, Fetch Contacts, Export Favourites |
| Leaderboard | Top Users, User Rank Details |
| Heartbeat | Health Check, Record Activity |

**Total: 40+ API test cases** covering positive, negative, and boundary scenarios.

### Framework structure

```
api-rest-assured/
├── pom.xml
└── src/test/
    ├── java/
    │   ├── tests/
    │   │   ├── BaseApiTest.java         (shared RequestSpec setup)
    │   │   ├── AuthApiTests.java        (8 auth test cases)
    │   │   ├── BoothApiTests.java       (14 booth test cases)
    │   │   └── VFairsApiTests.java      (webinar, swagbag, profile, leaderboard, heartbeat)
    │   └── utilities/
    │       └── ApiConfig.java           (config loader + spec builder)
    └── resources/
        ├── api-config.properties        (⚠ replace placeholders before running)
        └── testng.xml                   (parallel test suite config)
```

### How to run

#### Prerequisites
- Java 11+
- Maven 3.6+
- Your vFairs test environment credentials in `api-config.properties`

#### Run all API tests
```bash
cd api-rest-assured
mvn test
```

#### Run a specific test class
```bash
mvn test -Dtest=AuthApiTests
mvn test -Dtest=BoothApiTests
mvn test -Dtest=WebinarApiTests
```

### Setup — api-config.properties

Open `src/test/resources/api-config.properties` and replace:

```properties
api.base.url=https://your-event.vfairs.com/api/
api.auth.token=YOUR_QA_TEST_TOKEN_HERE
api.app.id=your_app_id
api.booth.id=your_booth_id
api.test.email=your-qa-test@email.com
```

> All values are **placeholder templates**. Never commit real tokens to Git.

---

## Key Design Decisions

**Why Page Object Model (POM)?**
Separates test logic from page interactions. When a UI element changes, only the Page Object needs updating — not every test.

**Why BDD / Cucumber?**
Gherkin scenarios are readable by non-technical stakeholders (PMs, clients). The vFairs QA team used TestRail for test case management — BDD bridges that gap into executable specs.

**Why externalised config?**
No hardcoded URLs, credentials, or environment-specific values anywhere in code. Everything is in `.properties` files that are templated for safety.

**Why REST Assured over Postman for regression?**
Postman collections are great for exploration and manual testing. REST Assured integrates into CI/CD pipelines, runs on every build, and produces structured reports.

---

## About

**Bushra Shahzadi** — Senior SQA Team Lead & Project Manager  
📍 Wolverhampton, UK | Immediately available  
🔗 [LinkedIn](https://linkedin.com/in/bushra-shahzadi-5bb899214)

> vFairs is a registered trademark of vFairs Inc. This repository contains only test automation code written independently. No proprietary vFairs source code, internal URLs, credentials, or confidential data is included.
