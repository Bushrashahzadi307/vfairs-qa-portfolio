@web @vfairs @login
Feature: vFairs Virtual Event Platform - Login (Web)
  As a QA Engineer on the vFairs project
  I want to verify all login journeys for the vFairs web platform
  So that attendees can securely access their virtual events

  # Based on real TestRail test cases: C111, C112, C114, C115, C116, C117, C193, C415, C473

  Background:
    Given the vFairs web platform is loaded

  @smoke @C111
  Scenario: C111 - Attendee logs in with valid registered email
    When the attendee enters a valid registered email address
    And clicks Next to proceed
    Then the password screen should appear
    When the attendee enters the correct password
    And clicks Sign In
    Then the attendee should be taken to the event lobby

  @C112
  Scenario: C112 - Password screen appears after successful email validation
    When the attendee enters a valid email "qa-attendee@example.com"
    And clicks Next to proceed
    Then the password screen should be displayed with a Password field
    And the page title should read "Create Password" or "Sign In"

  @C114 @negative
  Scenario: C114 - Validation message shown for invalid email format
    When the attendee enters an invalid email format "notanemail"
    And clicks Next to proceed
    Then a validation error message should appear
    And the attendee should remain on the login screen

  @C115 @negative
  Scenario: C115 - Unregistered email shows appropriate error
    When the attendee enters an unregistered email "nobody@notregistered.com"
    And clicks Next to proceed
    Then an error message should inform the attendee they are not registered

  @C193 @negative
  Scenario: C193 - Attendee cannot sign in with invalid email
    When the attendee enters an invalid email address "invalid@@test"
    And clicks Next to proceed
    Then login should be rejected with a validation error

  @C117
  Scenario: C117 - Doors Closed message when event is expired or not open yet
    Given the event is closed or not yet open
    When the attendee attempts to log in with valid credentials
    Then a "Doors Closed" message should be displayed

  @C415
  Scenario: C415 - Forgot password link is visible and functional
    Then the Forgot Password link should be visible on the login page
    When the attendee clicks Forgot Password
    Then the attendee should be directed to the password reset flow

  @C473
  Scenario: C473 - Attendee can logout successfully
    Given the attendee is logged into the event
    When the attendee navigates to profile and clicks Logout
    Then the attendee should be returned to the login screen

  @C116
  Scenario: C116 - OTP required when attendee is registered in multiple events
    When the attendee enters an email registered for multiple events
    And clicks Next to proceed
    Then an OTP selection screen should appear

  @regression
  Scenario Outline: Login attempt with various email formats
    When the attendee enters email "<email>"
    And clicks Next to proceed
    Then the result should be "<expected_outcome>"

    Examples:
      | email                        | expected_outcome    |
      | valid-attendee@example.com   | password_screen     |
      | notregistered@example.com    | error_message       |
      | invalidemail                 | validation_error    |
      | ""                           | validation_error    |
