Feature: TrainingTypeControllerTest feature
  Scenario: Get Training Type List throws exception
    Given the TrainingTypeFacade throws exception for training type
    When the getTrainingTypeList method is called for training type
    Then the response status should be INTERNAL_SERVER_ERROR for training type