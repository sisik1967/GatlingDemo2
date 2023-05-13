Feature: Create Token

  @login
  Scenario: login scenario
    Given url baseUrl
    And path 'users/login'
    And header Content-Type = 'application/json'
    * def parameters = {"email":#(email),"password":#(password)}
    When request {"user":{"email":#(parameters.email),"password":#(parameters.password)}}
    Then method POST
    And status 200
    * def token = response.user.token
    * print 'TOKEN: ', token