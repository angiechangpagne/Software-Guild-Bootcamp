DROP DATABASE IF EXISTS GUESSNUMBER;
CREATE DATABASE GUESSNUMBER;

USE GUESSNUMBER;

CREATE TABLE GAME(
	gameId INT PRIMARY KEY AUTO_INCREMENT,
	answer int NOT NULL,
    status boolean default 0 not null
);

CREATE TABLE ROUND(
	roundId INT PRIMARY KEY AUTO_INCREMENT,
    result varchar(6) NOT NULL,
    guess int NOT NULL,
    gameId int not null,
    FOREIGN KEY (gameId) REFERENCES GAME(gameId)
);


INSERT INTO GAME(status, answer ) VALUES (false, 1476);
INSERT INTO ROUND(guess,result, gameId) VALUES (1234,"e:2p:0",1);
INSERT INTO ROUND(guess,result, gameId) VALUES ( 6789,"e:2p:0", 1);

Select roundId, guess,result,gameId From ROUND where roundId=1;
Select * from ROUND

