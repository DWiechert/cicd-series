Feature: verify guestbook functionality

  Scenario: return no guests by default
    Given an empty guestbook
    When we query /guests
    Then an empty list should be returned

  Scenario: add a guest
    Given an empty guestbook
    When we add a guest
    Then the response should be 201

  Scenario: conflict if a guest is added twice
    Given a guestbook with one guest
    When we add a guest
    Then the response should be 409

  Scenario: return not found if the guest to delete doesn't exist
    Given a guestbook with one guest
    When we query for an unknown guest
    Then the response should be 404

  Scenario: delete a guest
    Given a guestbook with one guest
    When we delete a guest
    Then the response should be 204