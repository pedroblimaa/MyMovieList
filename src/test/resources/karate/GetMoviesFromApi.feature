Feature: Movies Api tests

    Background:
        * def authorization = call read('Authentication.feature')
        * def accessToken = authorization.response.token
        * url 'http://localhost:8081/movies'

    Scenario: Test Get Movies from API
        Given path ''
        And header Authorization = 'Bearer ' + accessToken
        When method GET
        And status 200
        And response.length == 20

    Scenario: Test Get Movies with name parameter from API
        Given path ''
        And params { name: 'Megamind' }
        And header Authorization = 'Bearer ' + accessToken
        When method GET
        Then status 200
        And match each response.[*].title contains 'Megamind'

    Scenario: Test Get no Movies becouse name does not exist
        Given path ''
        And params { name: 'Megamind2dfashiudh' }
        And header Authorization = 'Bearer ' + accessToken
        When method GET
        Then status 200
        And response.length == 0

    Scenario: Test Get Movies with non existing parameter
        Given path ''
        And params { dasdasg: 'fsadasdsa',}
        And header Authorization = 'Bearer ' + accessToken
        When method GET
        Then status 200
        And response.length == 20

    Scenario: Test recieve 400 when Get movies with invalid page
        Given path ''
        And params { page: '0' }
        And header Authorization = 'Bearer ' + accessToken
        When method GET
        Then status 400
