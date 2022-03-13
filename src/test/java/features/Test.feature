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
    Then verify email ids format inside the comments related to the specific post id


