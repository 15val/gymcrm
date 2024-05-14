Feature: TraineeControllerTest feature
  Scenario: Trainee register success
    Given a trainee CreateTraineeDto
    When registerTrainee method is called
    Then Trainee should be registered

  Scenario: Trainee register throws exception
    Given a trainee CreateTraineeDto exception
    When the registerTrainee method is called and throws exception
    Then the response status should be INTERNAL_SERVER_ERROR for register

  Scenario: Trainee update throws exception
    Given an trainee UpdateTraineeDto
    When the updateTrainee method is called and throws exception
    Then the response status should be INTERNAL_SERVER_ERROR for update

  Scenario: Get Trainee success
    Given a trainee UsernameDto
    When the getTrainee method is called
    Then the response status should be OK

  Scenario: Get Trainee throws exception
    Given a trainee UsernameDto
    When the getTrainee method is called and throws exception
    Then the response status should be INTERNAL_SERVER_ERROR for retrieving