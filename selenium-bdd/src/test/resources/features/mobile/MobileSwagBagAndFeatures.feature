@mobile @vfairs @swagbag
Feature: vFairs Mobile App - Swag Bag & Attendee Features
  As a QA Engineer testing the vFairs mobile application
  I want to verify Swag Bag, Attendee Listing, and Webinar features
  So that attendees can collect documents, connect with others, and attend sessions

  # Swag Bag: C3103, C3104
  # Attendee Listing: C121, C122, C129, C130, C131
  # Webinar: C547, C548

  Background:
    Given the attendee is logged into the vFairs mobile app

  # ── SWAG BAG ────────────────────────────────────────────────────────────────

  @smoke @C3103
  Scenario: C3103 - Swag Bag option added to mobile navigation menu
    Then the Swag Bag option should appear in the mobile navigation menu

  @C3104
  Scenario: C3104 - Swag Bag icon displayed in resources sub-menu
    When the attendee navigates to the Resources section
    Then the Swag Bag option should appear in the sub-menu
    And the Swag Bag icon should be visible

  @regression
  Scenario: Attendee can add document to Swag Bag from booth
    Given the attendee is viewing a booth with downloadable documents
    When the attendee taps Add to Swag Bag on a document
    Then the document should appear in the Swag Bag

  @regression
  Scenario: Attendee can remove document from Swag Bag
    Given the attendee has at least one document in the Swag Bag
    When the attendee opens the Swag Bag and removes a document
    Then the document should no longer appear in the Swag Bag list

  @regression
  Scenario: Attendee can email Swag Bag documents to themselves
    Given the attendee has documents in the Swag Bag
    When the attendee taps Send Email in the Swag Bag
    Then the documents should be sent to the attendee's registered email

  # ── ATTENDEE LISTING ────────────────────────────────────────────────────────

  @smoke @C121
  Scenario: C121 - Attendee listing screen shows correct information
    When the attendee navigates to the Attendee Listing screen
    Then the attendee list should be displayed
    And each attendee card should show name and profile information

  @C122
  Scenario: C122 - Tapping an attendee card opens their profile
    When the attendee navigates to Attendee Listing
    And taps on the first attendee card
    Then the attendee profile detail screen should open

  @C129
  Scenario: C129 - Search bar in Attendee Listing works correctly
    When the attendee navigates to Attendee Listing
    Then the search bar should be visible
    When the attendee types a name in the search bar
    Then the list should filter to show matching attendees only

  @C130
  Scenario: C130 - Scanned label appears for previously met attendees
    Given the attendee has previously scanned another attendee via QR
    When the attendee navigates to Attendee Listing
    Then the previously scanned attendee card should display a "Scanned" label

  @C131
  Scenario: C131 - Attendee profile data matches backend data
    When the attendee opens an attendee profile
    Then the displayed data should match the backend registered information
    And name, job title, and company should all be shown correctly

  # ── WEBINAR / SESSION ────────────────────────────────────────────────────────

  @smoke @C547
  Scenario: C547 - Custom webinar tab added successfully on video screen
    Given a webinar session has a custom tab configured in the backend
    When the attendee opens that webinar session
    Then the custom tab should appear in the webinar tab bar

  @C548
  Scenario: C548 - All standard webinar tabs are visible
    When the attendee opens a webinar session screen
    Then the Q&A tab should be visible
    And all configured tabs should be accessible

  @regression
  Scenario: Attendee can post a question in Q&A
    Given the attendee is on a live webinar session
    When the attendee taps the Q&A tab
    And types a question "What are the key features of vFairs?"
    And submits the question
    Then the question should appear in the Q&A list

  @regression
  Scenario: Attendee can add a webinar to their schedule
    When the attendee views a webinar session
    And taps Add to My Schedule
    Then the session should appear in the attendee's personal schedule

  # ── CHECK-IN ───────────────────────────────────────────────────────────────

  @regression
  Scenario: Attendee can check in to the event via QR code
    Given the attendee has a valid QR code for the event
    When the attendee scans the QR code at check-in
    Then the check-in status should be updated to Checked In

  @regression
  Scenario: QR code generation for attendee profile
    When the attendee navigates to their profile
    And taps Generate QR Code
    Then a QR code should be displayed for their profile
