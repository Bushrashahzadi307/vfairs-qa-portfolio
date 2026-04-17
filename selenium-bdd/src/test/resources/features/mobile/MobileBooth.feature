@mobile @vfairs @booth
Feature: vFairs Mobile App - Booth & Exhibit Hall
  As a QA Engineer testing the vFairs mobile application
  I want to verify exhibit hall, booth listing and booth detail screens
  So that attendees can browse and interact with exhibitors seamlessly

  # Real TestRail cases: C123, C124, C125, C132, C133, C134, C135, C136, C515, C725
  # Book a Meeting: C1132-C1167, C1219-C1221

  Background:
    Given the attendee is logged into the vFairs mobile app
    And the attendee navigates to the Exhibit Hall

  @smoke @C123
  Scenario: C123 - Exhibit Hall screen displays correctly
    Then the Exhibit Hall screen should be loaded
    And booth cards should be visible on screen
    And each booth card should show the booth logo and name

  @C124
  Scenario: C124 - Individual exhibitor booth detail screen
    When the attendee taps on the first booth
    Then the booth detail screen should open
    And the booth logo should be displayed
    And the booth title should be visible

  @C125
  Scenario: C125 - Company tab is visible and tappable on booth detail
    When the attendee opens any booth
    Then the Company tab should be displayed on booth detail
    When the attendee taps the Company tab
    Then the company information should be shown

  @C135
  Scenario: C135 - Video tab is visible and functional
    When the attendee opens any booth
    Then the Video tab should be displayed on booth detail
    When the attendee taps the Video tab
    Then the booth video content should appear

  @C136
  Scenario: C136 - Documentation tab is visible and functional
    When the attendee opens any booth
    Then the Documentation tab should be displayed on booth detail
    When the attendee taps the Documentation tab
    Then the booth documents should be listed

  @C132
  Scenario: C132 - Directional arrows navigate between booths
    When the attendee opens any booth
    Then the Next booth arrow should be visible
    When the attendee taps the Next booth arrow
    Then the next booth detail screen should load
    When the attendee taps the Previous booth arrow
    Then the previous booth detail should be shown

  @C133
  Scenario: C133 - Navigation menu bar is visible on booth screens
    When the attendee opens any booth
    Then the navigation menu bar should be displayed at the bottom

  @C134
  Scenario: C134 - Online users section shown on booth screen
    When the attendee opens any booth
    Then the online users section should be visible on booth detail

  @C515
  Scenario: C515 - Speaker heading hidden when booth has no speaker configured
    Given a booth exists with no speaker configured in the backend
    When the attendee opens that booth
    Then the Speakers heading should not appear on screen

  @C725
  Scenario: C725 - Embed URL content link type available in booth setup
    Given the booth has an Embed URL content link configured
    When the attendee opens the booth detail
    Then the embedded URL content should be displayed correctly

  # Book a Meeting scenarios
  @smoke @C1138
  Scenario: C1138 - Book a Meeting tab appears on booth detail page
    When the attendee opens a booth with meeting booking enabled
    Then the Book a Meeting tab should be visible on the booth detail screen

  @C1139
  Scenario: C1139 - Tapping Book a Meeting navigates to the booking page
    When the attendee opens a booth with meeting booking enabled
    And taps the Book a Meeting button
    Then the Book a Meeting page should open

  @C1140
  Scenario: C1140 - Two required tabs shown on Book a Meeting page
    When the attendee is on the Book a Meeting page
    Then the Booth Rep tab should be visible
    And the My Bookings tab should be visible

  @C1141
  Scenario: C1141 - Booth Rep tab is selected by default
    When the attendee is on the Book a Meeting page
    Then the first tab "Booth Rep" should be active by default

  @C1142
  Scenario: C1142 - Select booth representative heading displayed
    When the attendee is on the Book a Meeting page
    Then the heading "Select Booth Representative" should appear on screen

  @C1143
  Scenario: C1143 - All configured booth representatives appear in the list
    When the attendee is on the Book a Meeting page
    Then all available booth representatives should be listed

  @C1154 @negative
  Scenario: C1154 - Attendee cannot proceed without selecting a booth rep
    When the attendee is on the Book a Meeting page
    And taps the Next button without selecting a representative
    Then an error or blocking behaviour should prevent progression

  @C1156
  Scenario: C1156 - Selecting a rep and tapping Next moves to time slot screen
    When the attendee is on the Book a Meeting page
    And selects the first available booth representative
    And taps Next
    Then the Select Time Slot screen should open

  @C1163
  Scenario: C1163 - Attendee can select a date from the date tabs
    When the attendee is on the Select Time Slot screen
    Then date tabs should be displayed according to event timing
    When the attendee selects the first available date
    Then the selected date should be highlighted

  @C1165
  Scenario: C1165 - Available time slots shown after date selection
    When the attendee selects a date on the time slot screen
    Then all available time slots for that date should be displayed

  @C1219
  Scenario: C1219 - Cancel button on Book a Meeting page works correctly
    When the attendee is on the Book a Meeting page
    And taps the Cancel button
    Then the attendee should be returned to the booth detail screen

  @C1153
  Scenario: C1153 - Cancel and Next buttons both visible on booking screen
    When the attendee is on the Book a Meeting page
    Then the Cancel button should be visible
    And the Next button should be visible
