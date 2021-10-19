insert into user(name, email, password) values('mod', 'mod@mail.com', 
'$2a$10$ACQ.mtvbG3f1hHrDr3mXVecTObxvkMtA2IxfC8nBTwyZUlMoBV6P2');
insert into user(name, email, password) values('person', 'person@mail.com', 
'$2a$10$ACQ.mtvbG3f1hHrDr3mXVecTObxvkMtA2IxfC8nBTwyZUlMoBV6P2');

insert into movie(id, name) values(1, 'Movie 1');
insert into movie(id, name) values(2, 'Movie 2');
insert into user_movies(user_id, movies_id) values(1, 1);
insert into user_movies(user_id, movies_id) values(1, 2);