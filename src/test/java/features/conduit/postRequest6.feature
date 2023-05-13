@postRequestFeature
Feature: Post Request

  @smoke
  Scenario: post request
    Given url baseUrl
    * def tokenResponse = call read('classpath:callers/conduit/token.feature@login') {"email":#(userEmail),"password":#(userPassword)}
    * def authToken = tokenResponse.token
    #* def token2 = tokenResponse.response.user.token
    Given header Authorization = 'Token ' + authToken
    And path 'articles'
    And request {"article":{"title":"test title13554542","description":"test","body":"karate api test","tagList":""}}
    When method POST
    And status 200
    * print response