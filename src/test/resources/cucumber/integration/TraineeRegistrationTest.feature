Feature: Trainee Registration feature

  @registerTraineeTag
  Scenario: Register Trainee
    Given a CreateTraineeDto request for TraineeRegistrationTest
    When the registerTrainee endpoint is called for TraineeRegistrationTest
    Then a new Trainee should be created and stored in the database for TraineeRegistrationTest