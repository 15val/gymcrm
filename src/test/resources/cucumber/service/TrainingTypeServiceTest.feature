Feature: TrainingTypeServiceTest feature

  Scenario: Get TrainingType by Id success
    Given a valid TrainingType Id for training type service test
    When the getTrainingTypeById method is called for training type service test
    Then the TrainingType should be retrieved successfully for training type service test

  Scenario: Get TrainingType by Id NotFound
    Given an invalid TrainingType Id for training type service test
    When the getTrainingTypeById method is called for training type service test
    Then a UserNotFoundException should be thrown for training type service test