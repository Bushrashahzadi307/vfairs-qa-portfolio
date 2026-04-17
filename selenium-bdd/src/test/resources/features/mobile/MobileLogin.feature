@mobile @vfairs @login
Feature: vFairs Mobile App - Login & Authentication
  As a QA Engineer testing the vFairs mobile application
  I want to verify all login and authentication flows
  So that attendees can securely access their events on mobile

  # Real TestRail cases: C113, C111, C112, C114, C115, C116, C117, C193, C464, C473

  Background:
    Given the vFairs mobile app is launched

  @smoke @C113
  Scenario: C113 - Splash screen appears with vFairs logo centred
    Then the splash screen should be displayed
    And the vFairs logo should be visible and centred on screen

  @smoke @C111
  Scenario: C111 - Attendee signs in with valid email address
    When the attendee enters a valid registered email on mobile
    And taps the Next button
    Then the password entry screen should appear

  @C112
  Scenario: C112 - Password screen shown after successful email validation
    When the attendee enters valid email "qa-attendee@example.com" on mobile
    And taps the Next button
    Then the Create Password or Sign In screen should appear
    And a Password field should be visible

  @C114 @negative
  Scenario: C114 - Validation message appears for invalid email
    When the attendee enters invalid email "notvalid-email" on mobile
    And taps the Next button
    Then an error message should be shown on mobile login

  @C115 @negative
  Scenario: C115 - Unregistered email shows error on mobile
    When the attendee enters unregistered email "nobody@unknown.com" on mobile
    And taps the Next button
    Then the app should display a not-registered error message

  @C116
  Scenario: C116 - OTP screen appears for attendee registered in multiple events
    When the attendee enters an email registered across multiple events
    And taps the Next button
    Then the OTP verification screen should be shown

  @C117
  Scenario: C117 - Doors Closed message if event is not open or expired
    Given the event is closed or expired on mobile
    When the attendee attempts to log in
    Then the Doors Closed message should appear on screen

  @C193 @negative
  Scenario: C193 - Invalid email address is rejected
    When the attendee enters invalid email "invalid@@broken" on mobile
    And taps the Next button
    Then the app should reject the email with a validation error

  @C464
  Scenario: C464 - Auto login works for previously authenticated attendee
    Given the attendee has previously logged in successfully
    When the app is relaunched
    Then the attendee should be auto-logged in without entering credentials

  @C473
  Scenario: C473 - Attendee can log out from the app
    Given the attendee is logged into the mobile app
    When the attendee navigates to profile and taps Logout
    Then the app should return to the login screen

  @regression
  Scenario Outline: Mobile login with various email inputs
    When the attendee enters "<email>" on mobile login
    And taps the Next button
    Then the result should be "<outcome>"

    Examples:
      | email                      | outcome          |
      | valid@example.com          | password_screen  |
      | unregistered@example.com   | error_message    |
      | notanemail                 | validation_error |
      | ""                         | validation_error |
