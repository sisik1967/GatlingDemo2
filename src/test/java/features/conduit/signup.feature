Feature: Sign Up

  Background: Define test  base
    * url 'https://conduit.productionready.io/api/'
   # * url apiUrl
    * header Content-Type = 'application/json'
    * def dataGenerator = Java.type('helpers.DataGenerator')
    * def timeValidator = read('classpath:helpers/timeValidator.js')
    * def randomEmail = dataGenerator.getRandomEmail()
    * def randomUsername = dataGenerator.getRandomUsername()

  Scenario: Using Java Class
    Then path 'users'
    And request
  """
    {
        "user":{
            "email": #(randomEmail),
            "password": "Test123",
            "username": #(randomUsername)
        }
    }
  """
    When method POST
    Then status 200
    * print response
    And match response ==
  """
  {
      "user": {
          "email": #(randomEmail),
          "username": #(randomUsername),
          "bio": null,
          "image": "##string",
          "token": "#string"
      }
  }
  """


  # "id": "#number",
  # "createdAt": "#? timeValidator(_)",
  # "updatedAt": "#? timeValidator(_)",

  Scenario: Using Non Static Java Method
    * def jsFunction =
    """
      function(){
        var DataGenerator = Java.type('helpers.DataGenerator')
        var generator = new DataGenerator()
        return generator.getRandomUsername()
        }
    """
    * def randomUsername = call jsFunction
    * def randomEmail = dataGenerator.getRandomEmail()
    Then path 'users'
    And request
  """
    {
        "user":{
            "email": #(randomEmail),
            "password": "Test123",
            "username": #(randomUsername2)
        }
    }
  """
    When method POST
    Then status 200

  @negative
  Scenario Outline: Validate Sign Up Error Messages / Data Driven Scenario
    Then path 'users'
    And request
  """
    {
        "user":{
            "email": "<email>",
            "password": "<password>",
            "username": "<username>"
        }
    }
  """
    When method POST
    Then status 422
    And match response == <errorResponse>

    Examples:
      | email                     | password | username          | errorResponse                                       |
      | #(randomEmail)            | Test123  | TestUser          | {"errors": {"username":["has already been taken"]}} |
      | tdemailtestdata@gmail.com | Test123  | #(randomUsername) | {"errors": {"email":["has already been taken"]}}    |
      #| abc                         | Test123  | #(randomUsername)                 | {"errors": {"email":["is invalid"]}}                                |
      #| #(randomEmail)            | Test123  | TestUser111111111111111111111 | {"errors": {"username":["is too long (maximum is 20 characters)"]}} |
     # | #(randomEmail)            | Te        | #(randomUsername)                 | {"errors": {"password":["is too short (minimum is 8 characters)"]}} |
      |                           | Test123  | #(randomUsername) | {"errors": {"email":["can't be blank"]}}            |
      | #(randomEmail)            |          | #(randomUsername) | {"errors": {"password":["can't be blank"]}}         |
      | #(randomEmail)            | Test123  |                   | {"errors": {"username":["can't be blank"]}}         |