Feature: verify health check functionality

  Scenario: return OK for /health
    Given we have a service running
    When we query /health
    Then the response should be 200