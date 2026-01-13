Feature: App updated

  Scenario: App updated to new version
    Given the following items have been created in the account
      | folder | Pictures            |
      | folder | Documents           |
      | image  | Pictures/image1.png |
      | image  | Pictures/image2.png |
      | image  | Pictures/image3.png |
      | image  | Pictures/image4.png |
      | image  | Pictures/image5.png |
      | file   | AAAA.txt            |
      | image  | blank.jpg           |
    Given app is installed
    When log in
    And list of files is displayed
    And file AAAA.txt is downloaded
    And file blank.jpg is av.offline
    And folder Pictures is downloaded
    And passcode is set
    And app is reinstalled
    Then passcode view is displayed
    And list of files is displayed
    And the following items should be displayed
      | Pictures  |
      | Documents |
      | AAAA.txt  |
      | blank.jpg |
    And the folder / should contain the following downloaded files
      | AAAA.txt  |
      | blank.jpg |
    And the folder Pictures should contain the following downloaded files
      | image1.png |
      | image2.png |
      | image3.png |
      | image4.png |
      | image5.png |
    And the correct commit is displayed in Settings