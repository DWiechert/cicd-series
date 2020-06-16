Feature: verify functionality of /guests

  # https://stackoverflow.com/questions/19392492/is-it-ok-to-have-multiple-groups-of-given-when-then-in-a-single-gherkin-scenario

  Scenario: return no guests by default
    Given an empty guestbook
    When we query /guests
    Then the response should be empty

  Scenario: add a guest
    Given an empty guestbook
    When we add a guest
    Then the response should be 201
    And a single guest should be found with /guests

  Scenario: conflict if a guest is added twice
    Given a guestbook with one guest
    When we add a guest
    Then the response should be 409
    And a single guest should be found with /guests

  Scenario: return not found if the guest to delete doesn't exist
    Given a guestbook with one guest
    When we delete an unknown guest
    Then the response should be 404
    And a single guest should be found with /guests

  Scenario: delete a guest
    Given a guestbook with one guest
    When we delete a guest
    Then the response should be 204
    And no guests should be found with /guests