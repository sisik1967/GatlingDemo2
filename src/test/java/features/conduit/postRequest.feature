Feature: Post Request

  Background: Test Base
    Given url 'https://api.realworld.io/api/'
    And path 'users/login'
    And header Content-Type = 'application/json'
    When request {"user":{"email":"tdemailtestdata@gmail.com","password":"Trendyol123!"}}
    Then method POST
    And status 200
    * def token = response.user.token
    #tdemailtestdata@gmail.com
    #Trendyol123!

    Scenario: post request
      * print token
      Given header Authorization = 'Token ' + token
      And path 'articles'
      And request {"article":{"title":"test title1","description":"test","body":"karate api test","tagList":""}}
      When method POST
      And status 200
      * print response