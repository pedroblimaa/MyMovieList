<h1 align="middle"> MyMovieList 
  <br>
  <p align="middle"> 
    <img src="https://img.shields.io/badge/-Java-orange?style=for-the-badge&logo=java&logoColor=white"/>
    <img src="https://img.shields.io/badge/-MySQL-blue?style=for-the-badge&logo=mysql&logoColor=white"/>
    <img src="https://img.shields.io/badge/-mongodb-green?style=for-the-badge&logo=mongodb&logoColor=white"/>
    <img src="https://img.shields.io/badge/-JWT-green?style=for-the-badge&logo=json web tokens&logoColor=white"/>
    <img src="https://img.shields.io/badge/-Swagger-green?style=for-the-badge&logo=swagger&logoColor=white"/>
  </p>
  <img src="https://img.shields.io/github/last-commit/pedroblimaa/MyMovieList?style=for-the-badge"></p>
</h1>

## 📝API Requirements
### Must Have
- [x] Comunicar com uma API externa (desenvolvida pelo colaborador ou por terceiros)
- [x] Persistir dados num BD relacional (não relacional é "nice to have")
- [x] Uso de um design pattern aderente ao Spring Boot/Java
- [x] Criar alguns unit tests de algumas classes desenvolvidas
- [x] Criar testes funcionais de alguns requisitos desenvolvidos (por exemplo usando o banco H2)
- [x] Testes de integração são bem vindos (usando um ambiente “real” com MySQL)
- [x] Criar a imagem docker da aplicação para a execução em containers

### Nice to Have
- [x] Organização do código
- [x] Logging
- [x] Segurança (ex: JWT)
- [x] Cache
- [x] Incluir alguma operação que grave informações em um banco NoSQL, por exemplo, um log de eventos

## ⚙️ Set Up
  
### 1. Clone The Repository 
#### Open the terminal in folder in your computer to download and run the following command: 
`git clone https://github.com/pedroblimaa/MyMovieList.git`
##### If you still don't have git, install it before this 

### 2. Run the Mysql container to run the tests 
##### Run the command `docker-compose -f docker-compose.mysql.yml`

### 3. Create the .jar of the project
#### Open another tab of the terminal, go into the project folder again and run the command `mvn clean package`
##### If you don't have maven installed, download it here: https://maven.apache.org/download.cgi

### 4. Build the project
#### Stop the mysql container and run `docker-compose build`
##### You need to have docker installed: https://www.docker.com/products/docker-desktop

### 5. Now just run the project 
#### Run `docker-compose up`
#### And there we go, the application is up and runnning

## 📚 Design Patterns

### MVC 
Modelo padrão, muito usado para API's por sua facilidade e organização

### DTO
Padrão usado para devolver dados ao cliente e receber, com a facilidade de receber e passar só os parametrôs necessários

### Controller -> Service -> Repository
Ajuda na organização e leitura do código, pois determina um padrão de chamada

## 📃 Documentation
`localhost:8081/swagger-ui/`




