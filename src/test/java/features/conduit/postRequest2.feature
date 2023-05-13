@postRequestFeature
Feature: Post Request

  Background: Test Base
    Given url 'https://api.realworld.io/api/'

  @login
  Scenario: login scenario
    And path 'users/login'
    And header Content-Type = 'application/json'
    When request {"user":{"email":"tdemailtestdata@gmail.com","password":"Trendyol123!"}}
    Then method POST
    And status 200
    * def token = response.user.token
    * print 'TOKEN: ', token


  @smoke
  Scenario: post request
    * def tokenResponse = call read('classpath:features/conduit/postRequest2.feature@login')
    * def authToken = tokenResponse.token
    #* def token2 = tokenResponse.response.user.token
    Given header Authorization = 'Token ' + authToken
    And path 'articles'
    And request {"article":{"title":"test title132","description":"test","body":"karate api test","tagList":""}}
    When method POST
    And status 200
    * print response