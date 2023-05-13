Feature: Get request for tags

  Background: Test Base
    Given url 'https://api.realworld.io/api/'
    And path 'tags'
    And header Content-Type = 'application/json'

  Scenario: Get Request
    When method GET
    Then status 200
    * print response
    And match response.tags contains "welcome"
    And match response.tags !contains "welcomee"
    And match response.tags contains ["welcome","ipsum"]
    And match response.tags !contains ["cat","dog"]
    And match response.tags == "#array"
    And match response.tags != "#[] null"
    And match response.tags != '#null'
    And match response.tags != "#string"
    And match response.tags == "#[10]"
    And match header Content-Type == 'application/json; charset=utf-8'
    And match header Content-Type contains 'application'
    * print response.tags
    * print response.tags[0]
