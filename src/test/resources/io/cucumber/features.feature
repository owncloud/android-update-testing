Feature: App updated

  Scenario: App updated to new version
    Given the following items have been created in the account
      | folder | Pictures  |
      | folder | Documents |
      | file   | AAAA.txt  |
      | file   | blank.jpg |
    Given app is installed
    When log in
    And list of files is displayed
    And app is reinstalled
    Then the following items should be displayed
      | Pictures  |
      | Documents |
      | AAAA.txt  |
      | blank.jpg |