Feature: Verifying the test flow

  Background: Begin by contacting the services
    Given get posts details
    Given get comments details
    Given get albums details
    Given get photos details
    Given get todos details
    Given get users details

  Scenario: Verify that the user mentioned emails in the comment section are in the proper format
    Given get the user id of the user whose name is "Delphine"
    And retrieve all the related posts made by a specific user by using their user id
    And get the related comments that belong to the specific post id
    And get the related email ids with in the comments related to the specific post id
    Then verify email ids format

#    Extra Scenarios
  Scenario: Verify user has albums
    Given get all the user ids
    Then verify every user has albums with respect to their user id

  Scenario: Verify every album has photos
    Given get all the user ids
    Then verify that every user's album has photos

  Scenario: Verify url and thumbnailUrl base path id of photo's are the same
    Given get all the user ids
    And  verify every user has albums with respect to their user id
    Then verify the photo's url and thumbnailUrl base path ids are same

  Scenario: Verify that the user emails in the user section are in the proper format
    Given retrieve all the emails in the user section
    Then verify email ids format
