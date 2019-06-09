DROP DATABASE IF EXISTS SuperheroSightings;
CREATE DATABASE SuperheroSightings;

USE SuperheroSightings;

CREATE TABLE `Super` (
	SuperId INT PRIMARY KEY AUTO_INCREMENT,
	`Name` VARCHAR(50) NOT NULL,
	Description VARCHAR(150) NULL,
	Image VARCHAR(500) NULL
);

CREATE TABLE Ability (
	AbilityId INT PRIMARY KEY AUTO_INCREMENT,
	`Name` VARCHAR(50) NOT NULL,
	Description VARCHAR(150) NULL
);

CREATE TABLE AbilitySuper (
	SuperId INT NOT NULL,
	AbilityId INT NOT NULL,
    PRIMARY KEY (SuperId, AbilityId),
	CONSTRAINT fk_AbilitySuper_SuperId
		FOREIGN KEY (SuperId)
		REFERENCES Super(SuperId),
	CONSTRAINT fk_AbilitySuper_AbilityId
		FOREIGN KEY (AbilityId)
		REFERENCES Ability(AbilityId)
);

CREATE TABLE Location (
	LocationId INT PRIMARY KEY AUTO_INCREMENT,
	`Name` VARCHAR(50) NULL,
    Latitude DECIMAL(10,8) NOT NULL,
    Longitude DECIMAL(11,8) NOT NULL
);

CREATE TABLE `Organization` (
	OrganizationId INT PRIMARY KEY AUTO_INCREMENT,
	`Name` VARCHAR(50) NOT NULL,
	Description VARCHAR(500) NULL,
	LocationId INT NULL,
	CONSTRAINT fk_Organization_Location
		FOREIGN KEY (LocationId)
		REFERENCES Location(LocationId)
);

CREATE TABLE OrganizationSuper (
	SuperId INT NOT NULL,
	OrganizationId INT NOT NULL,
    PRIMARY KEY (SuperId, OrganizationId),
	CONSTRAINT fk_OrganizationSuper_Super
		FOREIGN KEY (SuperId)
		REFERENCES Super(SuperId),
	CONSTRAINT fk_OrganizationSuper_Organization
		FOREIGN KEY (OrganizationId)
		REFERENCES `Organization`(OrganizationId)
);

CREATE TABLE Sighting (
	SightingId INT PRIMARY KEY AUTO_INCREMENT,
	Description VARCHAR(500) NULL,
	`Date` DATE NOT NULL,
	`Time` TIME NULL,
	LocationId INT NOT NULL,
	CONSTRAINT fk_Sighting_Location
		FOREIGN KEY (LocationId)
		REFERENCES Location(LocationId)
);

CREATE TABLE SightingSuper (
	SuperId INT NOT NULL,
	SightingId INT NOT NULL,
    PRIMARY KEY (SuperId, SightingId),
	CONSTRAINT fk_SightingSuper_Super
		FOREIGN KEY (SuperId)
		REFERENCES `Super`(SuperId),
	CONSTRAINT fk_SightingSuper_Sighting
		FOREIGN KEY (SightingId)
		REFERENCES Sighting(SightingId)
);

INSERT INTO Location (`Name`, Latitude, Longitude) VALUES
	('Jupiter', 41.26999892, -73.551664),
    ('Los Angeles', 42.652580, -73.756233),
    ('Elon Musk Mars Space Station', 40.758896, -73.985130),
    ('Mars New York', 40.790885, -73.9747115),
    ('Floral Da, Mars', 42.843163, -78.269665),
    ('Times Ellipse, Mars', 43.009305, -76.139715);

INSERT INTO Sighting (Description, `Date`, `Time`, LocationId) VALUES
	('They teleported.', '2017-10-29', '14:57', 1),
    ('They appeared to be returning from a morning run.', '2017-10-14', '4:58', 1),
    ('I saw a lightning strike and then Donald Trump became president.', '2017-10-07', '0:39', 2),
    ('I watched the spaceship land here.', '2017-09-08', '19:22', 2),
    ('Looked like paranormal.', '2017-09-16', '18:19', 3),
    ('They remixed the national anthem to techno on mars', '2017-10-14', '7:01', 4),
    ('They were both holding the phones to their ears in the visitors\' room but it didn\'t look like they were speaking.', '2018-01-12', '17:00', 5),
    ('Zir was watching the new sitcom,', '2017-09-20', '8:00', 4),
    ('Mars is just like the United States in 1955 -Ray Bradbury', '2018-04-05', '8:00', 4),
    ('The Future is correct', '2018-03-14', '13:00', 3),
    ('He was dancing in a flash mob demonstration about Robot Rights in Los Angeles.', '2018-04-12', '12:00', 3);
    
INSERT INTO `Super` (`Name`, Description, Image) VALUES
	('Kanye West', 'Started a shoe company and hates Taylor Swift, brought awareness about influential people with bipolar disorder', 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/11/Kanye_West_at_the_2009_Tribeca_Film_Festival.jpg/220px-Kanye_West_at_the_2009_Tribeca_Film_Festival.jpg'),
    ('Elon Musk', 'President of Mars', 'https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/Elon_Musk_Royal_Society.jpg/220px-Elon_Musk_Royal_Society.jpg'),
    ('Grimes', 'Artist of Alternative Music and Futurist', 'https://junkee.com/wp-content/uploads/2018/11/Grimes-credit-Eli-Russell-Linnetz.jpg'),
    ('Lil Uzi Vert','Raps about money', 'https://d3vhc53cl8e8km.cloudfront.net/hello-staging/wp-content/uploads/2017/05/28011826/main-972x597.jpg'),
    ('Jefree Star', 'Androgynous CEO of Jeffree Star Cosmetics, controversial you tuber based in Calabasas, cannot relate', 'https://ugc.reveliststatic.com/gen/constrain/640/640/80/2018/10/31/09/e0/j3/ph1jml6eos2qbwe.png'),
    ('James Charles', 'Controversial Artist, owner of Morphe cosmetics, last seen in Met Gala', 'https://a57.foxnews.com/static.foxnews.com/foxnews.com/content/uploads/2019/05/931/524/james-charles-AP.jpg?ve=1&tl=1'),
    ('NULLBYTE', 'INVENTED NULL', 'https://s.abcnews.com/images/US/Gty_Hacker_Group_Anonymous_er_160318_12x5_992.jpg');

INSERT INTO SightingSuper (SuperId, SightingId) VALUES
	(1,1),
    (4,1),
    (1,2),
    (4,2),
    (3,3),
    (2,4),
    (5,4),
    (3,5),
    (2,6),
    (5,6),
    (1,7),
    (6,7),
    (2,8),
    (2,9),
    (3,10),
    (3,11);
    
INSERT INTO Ability (`Name`, Description) VALUES
	('Tweet','Ability to induce conflicts in the media'),
	('Hexadecimal Division and Multiplication', 'Ability to perform Hexadecimal functions'),
	('Six Sense', 'Unknown other ability'),
    ('99% Access', 'Ability to access more of the human brain'),
    ('Inventor', 'Can create gadgets '),
    ('Storm Control', 'ability to use chemtrails to induce weather and control populations'),
    ('Astro Projecting', 'To travel to other peoples dreams'), 
    ('Factor 9', 'Born in the Bermuda triangle, needs to be studied'),
    ('Tesseract', 'Mathematically blessed and can solve universal equations'),
    ('Giga-byte', 'Able to cut through any security system including Trumps wall'),
    ('Super Suit', 'Bulletproof business casual outfit established in the year 3005'),
    ('Super Vehicle', 'Tesla V 5'),
    ('Magnetic Manipulation', 'Allows super being to generate and control magnetic force fields, the moon and affects different astrological signs'),
    ('Glide', 'Ability to fly through the air.  Sometimes under certain conditions or with certain equipment.');

INSERT INTO AbilitySuper (SuperId, AbilityId) VALUES
	(1,1),
	(1,2),
	(1,3),
	(2,4),
	(2,5),
	(3,6),
	(3,7),
	(3,14),
	(4,8),
	(4,9),
	(4,10),
	(5,8),
	(5,11),
	(5,12),
	(6,11),
	(6,13),
	(6,14);

INSERT INTO `Organization` (`Name`, Description, LocationId) VALUES
	('Order 500', 'The government controlled by a system of server computers that can compute the algorithm for world peace', 1),
	('Cyber Cartel', 'A group of ethereum monopolies, forming an Oligarchy based in the international space station', NULL),
	('Suicide Squad', 'currently living in area 51, no longer established', 2),
	('Fight Club','No Comment', NULL);

INSERT OrganizationSuper (SuperId, OrganizationId) VALUES
	(1,1),
    (4,1),
    (2,2),
    (5,2),
    (3,3),
    (6,4);