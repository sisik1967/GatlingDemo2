@postRequestFeature
Feature: Post Request

  Background:
    * def tokenResponse = callonce read('classpath:callers/conduit/token.feature@login') {"email":#(userEmail),"password":#(userPassword)}
    * def authToken = tokenResponse.token
    Given header Authorization = 'Token ' + authToken
    Given url baseUrl
    And path 'articles'
    * def requestJson = read('classpath:datas/conduit/createArticleRequest.json')
    * def responseJson = read('classpath:datas/conduit/createArticleResponse.json')
    * def timeValidator = read('classpath:helpers/timeValidator.js')

  @smoke
  Scenario: post request
    * set requestJson.article.title = 'test title 6512433'
    * set requestJson.article.description = 'test title'
    * set requestJson.article.body = 'test title bal bla'
    * set requestJson.article.tagList = null
    And request requestJson
    When method POST
    And status 200
    * print response
    And match response.article.title == responseJson.article.title
    And match response.article.createdAt == '#? timeValidator(_)'

