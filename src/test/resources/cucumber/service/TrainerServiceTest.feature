Feature: TrainerServiceTest feature

  Scenario: Create Trainer success
    Given a Trainer and a User for trainer service test
    When the createTrainer method is called for trainer service test
    Then the Trainer should be created successfully for trainer service test

  Scenario: Create Trainer UserNotFound
    Given a Trainer without a User for trainer service test
    When the createTrainer method is called for trainer service test
    Then the Trainer should not be created for trainer service test

  Scenario: Update Trainer success
    Given a valid Trainer for trainer service test
    When the updateTrainer method is called for trainer service test
    Then the Trainer should be updated successfully for trainer service test

  Scenario: Get Trainer by Id success
    Given a valid Trainer Id for trainer service test
    When the getTrainerById method is called for trainer service test
    Then the Trainer should be retrieved successfully for trainer service test

  Scenario: Get Trainer by Id UserNotFound
    Given an invalid Trainer Id for trainer service test
    When the getTrainerById method is called for trainer service test
    Then a UserNotFoundException should be thrown for trainer service test