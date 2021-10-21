# myMovieList

### 1. Clone The Repository 
#### Open the terminal in folder in your computer to download and run the following command: 
`git clone https://github.com/pedroblimaa/MyMovieList.git`
##### If you still don't have git, install it before this 

### 2. Run the Mysql container to run the tests 
##### Run the command `docker-compose -f docker-compose.mysql.yml`

### 3. Create the .jar of the project
#### Open another tab of the terminal and run the command `mvn clean package`
##### If you don't have maven installed, download it here: https://maven.apache.org/download.cgi

### 4. Build the project
#### Stop the mysql container and run `docker-compose build`
##### You need to have docker installed: https://www.docker.com/products/docker-desktop

### 5. Now just run the project 
#### Run `docker-compose up`
#### And there we go, the application is up and runnning 

