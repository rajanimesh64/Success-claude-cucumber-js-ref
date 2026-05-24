Feature: Browser Navigation

  Scenario: Launch Google in Chrome
    Given I open the Google homepage
    Then the page title should contain "Google"

  Scenario: Open Google and type animesh in the search box
    Given I open the Google homepage
    When I type "animesh" in the Google search box
    Then the Google search box should contain "animesh"

  Scenario: Open YouTube and verify the page title
    Given I open YouTube
    Then the page title should contain "YouTube"

  Scenario: Enter an incorrect URL in Chrome
    Given I navigate to an incorrect URL "https://thisisnotavalidwebsite.invalid"
    Then the browser should display a page not found error

  Scenario: Open LinkedIn
    Given I open LinkedIn
    Then the page title should contain "LinkedIn"
