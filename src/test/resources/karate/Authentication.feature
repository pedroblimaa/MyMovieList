Feature: Api Authentication

    Background: 
        * url 'http://localhost:8081/'

    Scenario: Authenticate with valid credentials
        Given path 'auth'
        When request {'email': 'mod@mail.com', 'password': '123456'}
        And method POST
        Then status 200
