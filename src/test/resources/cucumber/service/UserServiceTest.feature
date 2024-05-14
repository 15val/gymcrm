Feature: UserServiceTest feature

  Scenario: Generate Username
    Given a first name and a last name for user service test
    When the generateUsername method is called for user service test
    Then a valid username should be generated for user service test

  Scenario: Generate Password
    When the generatePassword method is called for user service test
    Then a valid password should be generated for user service test

  Scenario: Is Username and Password Valid with valid user
    Given a valid User for user service test
    When the isUsernameAndPasswordValid method is called for user service test
    Then the result should be true for user service test

  Scenario: Is Username and Password Valid with invalid user
    Given an invalid User for user service test
    When the isUsernameAndPasswordValid method is called for user service test
    Then the result should be false for user service test

  Scenario: Get User by Id with valid id
    Given a valid User Id for user service test
    When the getUserById method is called for user service test
    Then the User should be retrieved successfully for user service test

  Scenario: Get User by Id with invalid id
    Given an invalid User Id for user service test
    When the getUserById method is called for user service test
    Then a UsernameOrPasswordInvalidException should be thrown for user service test

  Scenario: Update User with invalid user
    Given an invalid User for user service test
    When the updateUser method is called for user service test
    Then a UsernameOrPasswordInvalidException should be thrown for user service test

  Scenario: Delete User with invalid id
    Given an invalid User Id for user service test
    When the deleteUser method is called for user service test
    Then a UsernameOrPasswordInvalidException should be thrown for user service test