Feature: Tests for Home Page

  Background: Test Base
    Given url 'https://api.realworld.io/api/'
    And path 'articles'
    And params {limit: 10, offset:0}
    And header Content-Type = 'application/json'

  @smoke
  Scenario: Get All Tags
    When method GET
    Then status 200
  #  * print response
    * print 'responseBytes: ', responseBytes
    * print 'responseStatus: ', responseStatus
    * print responseHeaders

    @ignore
  Scenario: Path and Query Param
     # Given url 'https://api.realworld.io/api/'
     # And path 'articles'
      #And param limit = 10
      #And param offset = 0
    When method GET
    Then status 200
    * print response
