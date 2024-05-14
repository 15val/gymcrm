Feature: TraineeServiceTest feature

  Scenario: Create Trainee success
    Given a Trainee and a User for trainee service test
    When the createTrainee method is called
    Then the Trainee should be created successfully for trainee service test

  Scenario: Create Trainee throws UserNotFoundException
    Given a Trainee without a User for trainee service test
    When the createTrainee method is called
    Then a UserNotFoundException should be thrown for trainee service test

  Scenario: Update Trainee success
    Given a valid Trainee for trainee service test
    When the updateTrainee method is called
    Then the Trainee should be updated successfully for trainee service test

  Scenario: Delete Trainee success
    Given a Trainee for trainee service test
    When the deleteTrainee method is called
    Then the Trainee should be deleted successfully for trainee service test

  Scenario: Get Trainee by Id success
    Given a valid Trainee Id for trainee service test
    When the getTraineeById method is called
    Then the Trainee should be retrieved successfully for trainee service test