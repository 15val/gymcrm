Feature: ActuatorTest feature

  Scenario: Server is reachable
    Given the server is reachable
    When the health method is called
    Then the server status should be UP and the detail should be "Server is reachable"

  Scenario: Server is unreachable
    Given the server is unreachable
    When the health method is called
    Then the server status should be DOWN and the detail should be "Server is unreachable"

  Scenario: Error occurs while pinging the server
    Given an error occurs while pinging the server
    When the health method is called
    Then the server status should be DOWN and the detail should be "Error while pinging server"

  Scenario: Free memory is above the threshold
    Given the free memory is above the threshold
    When the health method is called
    Then the server status should be UP

  Scenario: Free memory is below the threshold
    Given the free memory is below the threshold
    When the health method is called
    Then the server status should be DOWN