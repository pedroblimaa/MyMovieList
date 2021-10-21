# myMovieList

### 1. Clone The Repository 
#### Open the terminal in folder in your computer to download and run the following command: 
`git clone https://github.com/pedroblimaa/MyMovieList.git`
##### If you still don't have git, install it before this 

### 2. Run the Mysql container to run the tests 
##### Run the command `docker-compose -f docker-compose.mysql.yml` and open another tab of terminal 

### 3. Create the .jar of the project
#### Run the command `mvn clean package`
##### If you don't have maven installed, download it here: https://maven.apache.org/download.cgi

### 4. Build the project
#### Run `docker-compose build`
##### You need to have docker installed: https://www.docker.com/products/docker-desktop

### 5. Just run the project 
#### Run `docker-compose up`
#### And there we go, the application is up and runnning 

