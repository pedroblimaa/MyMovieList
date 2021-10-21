# create databases
CREATE DATABASE IF NOT EXISTS `movie_list_db`;
CREATE DATABASE IF NOT EXISTS `movie_list_db_test`;

#create tables
USE movie_list_db_test;
CREATE TABLE IF NOT EXISTS user(
	id BIGINT primary key auto_increment, 
    name varchar(255), 
    email varchar(255), 
    password varchar(255), 
    private_list BIT(1));
    
CREATE TABLE IF NOT EXISTS movie(
	id BIGINT primary key, 
	language varchar(255), 
    name varchar(255), 
    overview varchar(1000), 
    release_date varchar(255),
    vote_average float,
    vote_count BIGINT);
    
CREATE TABLE IF NOT EXISTS user_movies(
	user_id BIGINT,
    movies_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (movies_id) REFERENCES movie(id));
    
#insert data
insert into user(name, email, password) values('mod', 'mod@mail.com', 
'$2a$10$ACQ.mtvbG3f1hHrDr3mXVecTObxvkMtA2IxfC8nBTwyZUlMoBV6P2');
insert into user(name, email, password) values('person', 'person@mail.com', 
'$2a$10$ACQ.mtvbG3f1hHrDr3mXVecTObxvkMtA2IxfC8nBTwyZUlMoBV6P2');

insert into movie(id, name) values(1, 'Movie 1');
insert into movie(id, name) values(2, 'Movie 2');
insert into user_movies(user_id, movies_id) values(1, 1);
insert into user_movies(user_id, movies_id) values(1, 2);

USE movie_list_db;
CREATE TABLE IF NOT EXISTS user(
	id BIGINT primary key auto_increment, 
    name varchar(255), 
    email varchar(255), 
    password varchar(255), 
    private_list BIT(1));

#insert data
insert into user(name, email, password, private_list) values('mod', 'mod@mail.com', 
'$2a$10$ACQ.mtvbG3f1hHrDr3mXVecTObxvkMtA2IxfC8nBTwyZUlMoBV6P2', false);
insert into user(name, email, password, private_list) values('user', 'user@mail.com', 
'$2a$10$ACQ.mtvbG3f1hHrDr3mXVecTObxvkMtA2IxfC8nBTwyZUlMoBV6P2', false);