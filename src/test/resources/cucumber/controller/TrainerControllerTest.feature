Feature: TrainerControllerTest feature
  Scenario: Trainer register success
    Given a trainer CreateTrainerDto
    When the registerTrainer method is called
    Then the response status should be OK for trainer

  Scenario: Trainer register throws exception
    Given a trainer CreateTrainerDto that throws exception
    When the registerTrainer method is called
    Then the response status should be INTERNAL_SERVER_ERROR for trainer

  Scenario: Trainer update success
    Given an trainer UpdateTrainerDto
    When the updateTrainer method is called
    Then the response status should be OK for trainer

  Scenario: Trainer update throws exception
    Given an trainer UpdateTrainerDto that throws exception
    When the updateTrainer method is called
    Then the response status should be INTERNAL_SERVER_ERROR for trainer

  Scenario: Get Trainer success
    Given a trainer UsernameDto
    When the getTrainer method is called
    Then the response status should be OK for trainer

  Scenario: Get Trainer throws exception
    Given a trainer UsernameDto that throws exception
    When the getTrainer method is called
    Then the response status should be INTERNAL_SERVER_ERROR for trainer

  Scenario: Update IsActive success
    Given a trainer UsernameAndIsActiveDto
    When the updateIsActive method is called
    Then the response status should be OK for trainer

  Scenario: Update IsActive throws exception
    Given a trainer UsernameAndIsActiveDto that throws exception
    When the updateIsActive method is called
    Then the response status should be INTERNAL_SERVER_ERROR for trainer

  Scenario: Get Trainers Training List success
    Given a trainer GetTrainersTrainingListRequestDto
    When the getTrainersTrainingList method is called
    Then the response status should be OK for trainer

  Scenario: Get Trainers Training List throws exception
    Given a trainer GetTrainersTrainingListRequestDto that throws exception
    When the getTrainersTrainingList method is called
    Then the response status should be INTERNAL_SERVER_ERROR for trainer