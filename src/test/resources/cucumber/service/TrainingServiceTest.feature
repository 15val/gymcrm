Feature: TrainingServiceTest feature

  Scenario: Create Training TraineeNotFound
    Given a Training with a non-existent Trainee for training service test
    When the createTraining method is called for training service test
    Then a UserNotFoundException should be thrown for training service test

  Scenario: Create Training TrainerNotFound
    Given a Training with a non-existent Trainer for training service test
    When the createTraining method is called for training service test
    Then a UserNotFoundException should be thrown for training service test

  Scenario: Create Training TrainingTypeNotFound
    Given a Training with a non-existent TrainingType for training service test
    When the createTraining method is called for training service test
    Then a UserNotFoundException should be thrown for training service test

  Scenario: Get Training by Id success
    Given a valid Training Id for training service test
    When the getTrainingById method is called for training service test
    Then the Training should be retrieved successfully for training service test

  Scenario: Get Training by Id NotFound
    Given an invalid Training Id for training service test
    When the getTrainingById method is called for training service test
    Then a UserNotFoundException should be thrown for training service test