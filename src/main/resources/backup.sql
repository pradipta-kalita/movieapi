-- MySQL dump 10.13  Distrib 9.0.1, for Linux (x86_64)
--
-- Host: localhost    Database: movies
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actor_movie`
--

DROP TABLE IF EXISTS `actor_movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actor_movie` (
  `movie_id` binary(16) NOT NULL,
  `actor_id` binary(16) NOT NULL,
  PRIMARY KEY (`movie_id`,`actor_id`),
  KEY `FKsl8om7f4myc9jm9rattt4wqlp` (`actor_id`),
  CONSTRAINT `FK93fat9sv5vd7acpjrqrri8rad` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
  CONSTRAINT `FKsl8om7f4myc9jm9rattt4wqlp` FOREIGN KEY (`actor_id`) REFERENCES `actors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_movie`
--

LOCK TABLES `actor_movie` WRITE;
/*!40000 ALTER TABLE `actor_movie` DISABLE KEYS */;
INSERT INTO `actor_movie` VALUES (_binary 'Qõñw=G§Ä\ﬂO\ı	\'m\≈',_binary '\—\–/+\ZE≥±m)\È˘à4'),(_binary 'ó¿\‚.π∞J∞¨˚\Ÿ\r¿0',_binary '\Û\◊u†\ÓDßí\Ù¥lrK≠'),(_binary '\È∑@`ºBæï\Ì\0Ö\ﬂGE',_binary '(v£44\0Cf±ZêId;L)'),(_binary 'Qõñw=G§Ä\ﬂO\ı	\'m\≈',_binary '.\Ì\‹{\»FDÑ\œ\»å±\ÌC'),(_binary '\È∑@`ºBæï\Ì\0Ö\ﬂGE',_binary '4,w£ìüN\ÛØ\À\« Ñ\''),(_binary 'ó¿\‚.π∞J∞¨˚\Ÿ\r¿0',_binary 'FZ˛çEOn¨|´ø-o¢\≈'),(_binary '\È∑@`ºBæï\Ì\0Ö\ﬂGE',_binary 'HE1D¡YOTípØ\Ó&û\Ò'),(_binary 'Qõñw=G§Ä\ﬂO\ı	\'m\≈',_binary 'q\ÀP_G}G1∞Ñ\ƒ\ÌT|.'),(_binary 'Qõñw=G§Ä\ﬂO\ı	\'m\≈',_binary '|\·\›\Ë	¸@V∑ØQ\ı{<'),(_binary 'ó¿\‚.π∞J∞¨˚\Ÿ\r¿0',_binary '∞?<´5@ã≤#aq\»k%\˜'),(_binary '\È∑@`ºBæï\Ì\0Ö\ﬂGE',_binary '\Ìﬁ¶-∏y@|ï\√[\·Z’ø\ﬂ'),(_binary '\È∑@`ºBæï\Ì\0Ö\ﬂGE',_binary '˚e\˜r1\◊EÅ\‚?í1£{\n');
/*!40000 ALTER TABLE `actor_movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actors`
--

DROP TABLE IF EXISTS `actors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actors` (
  `id` binary(16) NOT NULL,
  `dob` date DEFAULT NULL,
  `mini_biography` text NOT NULL,
  `name` varchar(255) NOT NULL,
  `profile_picture_url` varchar(2000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actors`
--

LOCK TABLES `actors` WRITE;
/*!40000 ALTER TABLE `actors` DISABLE KEYS */;
INSERT INTO `actors` VALUES (_binary '\—\–/+\ZE≥±m)\È˘à4','1940-03-26','James Edmund Caan was an American actor. He came to prominence playing Sonny Corleone in The Godfather ‚Äì a performance that earned him Academy Award and Golden Globe nominations for Best Supporting Actor. He reprised his role in The Godfather Part II.','James Caan','http://res.cloudinary.com/dfths157i/image/upload/v1729921667/actors/jamescaan.jpg'),(_binary '\Û\◊u†\ÓDßí\Ù¥lrK≠','1954-02-18','John Joseph Travolta is an American actor, singer, and producer. He began acting in television before transitioning into a leading man in films','John Travolta','http://res.cloudinary.com/dfths157i/image/upload/v1729922087/actors/johntravolta.jpg'),(_binary '\Ïuû¡7L`âΩ\Œ\Õ\"\Áì','1963-06-09','An American actor known for his eccentric roles in \'Edward Scissorhands\', \'Pirates of the Caribbean\', and \'Sweeney Todd\'. He has frequently collaborated with Tim Burton in films like \'Edward Scissorhands\' and \'Alice in Wonderland\'.','Johnny Depp','http://res.cloudinary.com/dfths157i/image/upload/v1729904491/actors/johnnydepp.jpg'),(_binary 'π(\¨XBZ°\ l±\ÎR\ ','1974-01-30','An English actor known for his transformation-heavy roles in films like \'American Psycho\' and \'The Machinist\'. He starred as Batman in Christopher Nolan\'s \'The Dark Knight\' trilogy.','Christian Bale','http://res.cloudinary.com/dfths157i/image/upload/v1729901601/actors/christianbale.jpg'),(_binary '“ú\˜FM≤ÅªÑ\Á\ﬂ\Ò','1930-05-31','An iconic American actor and director known for roles in \'The Good, the Bad and the Ugly\' and \'Gran Torino\'. He worked with himself as the director in many films, including \'Unforgiven\' and \'Million Dollar Baby\'.','Clint Eastwood','http://res.cloudinary.com/dfths157i/image/upload/v1729904750/actors/clinteastwood.jpg'),(_binary '\·&´ü;O\‰†K\Îi≠e•','1937-04-22','A celebrated American actor known for his roles in \'The Shining\', \'One Flew Over the Cuckoo\'s Nest\', and \'Chinatown\'. He starred in Stanley Kubrick\'s horror classic, \'The Shining\'.','Jack Nicholson','http://res.cloudinary.com/dfths157i/image/upload/v1729904286/actors/jacknicholson.jpg'),(_binary '\"Ωw17EE\né\€2ç¢I','1943-08-17','A legendary actor known for iconic roles in \'Taxi Driver\', \'Goodfellas\', and \'The Godfather Part II\'. He has had a longstanding collaboration with Martin Scorsese in films like \'Raging Bull\' and \'The Irishman\'.','Robert De Niro','http://res.cloudinary.com/dfths157i/image/upload/v1729901548/actors/robertdeniro.jpg'),(_binary '(v£44\0Cf±ZêId;L)','1987-02-21','Elliot Page is a Canadian actor and producer. His accolades include nominations for an Academy Award, three British Academy Film Awards, a Golden Globe Award, two Primetime Emmy Awards, and a Screen Actors Guild Award. He is also known for his outspoken activism.','Elliot Page','http://res.cloudinary.com/dfths157i/image/upload/v1729909848/actors/elliotpage.jpg'),(_binary '.¬≠U\≈L@∏“°ê¿\Ã','1964-04-07','An Australian actor known for his roles in \'Gladiator\', \'A Beautiful Mind\', and \'Les Mis√©rables\'. He starred in Ridley Scott\'s \'Gladiator\' and \'American Gangster\'.','Russell Crowe','http://res.cloudinary.com/dfths157i/image/upload/v1729904437/actors/russellcrowe.jpg'),(_binary '.\Ì\‹{\»FDÑ\œ\»å±\ÌC','1924-04-03','Marlon Brando Jr. was an American actor. He received numerous accolades throughout his career, which spanned six decades, including two Academy Awards, three British Academy Film Awards, a Cannes Film Festival Award, two Golden Globe Awards, and a Primetime Emmy Award.','Marlon Brando','http://res.cloudinary.com/dfths157i/image/upload/v1729921533/actors/marlonbrando.jpg'),(_binary '/H†L0EfßâlµFí©f','1954-12-28','An American actor known for his powerful roles in \'Training Day\', \'Malcolm X\', and \'Glory\'. He worked with Spike Lee on \'Malcolm X\' and \'Inside Man\'.','Denzel Washington','http://res.cloudinary.com/dfths157i/image/upload/v1729904651/actors/denzelwashington.jpg'),(_binary '4,w£ìüN\ÛØ\À\« Ñ\'','1981-02-17','Joseph Leonard Gordon-Levitt is an American actor. He has received various accolades, including nominations for the Golden Globe Award for Best Actor ‚Äì Motion Picture Musical or Comedy for his leading performances in 500 Days of Summer and 50/50.','Joseph Gordon-Levitt','http://res.cloudinary.com/dfths157i/image/upload/v1729907711/actors/josephgordonlevitt.jpg'),(_binary 'FZ˛çEOn¨|´ø-o¢\≈','1970-04-29','Uma Karuna Thurman is an American actress. She has performed in a variety of films, from romantic comedies and dramas to science fiction and action films. Following her appearances on the December 1985 and May 1986 covers of British Vogue, Thurman starred in Dangerous Liaisons.','Uma Thurman','http://res.cloudinary.com/dfths157i/image/upload/v1729922217/actors/umathurman.jpg'),(_binary 'HE1D¡YOTípØ\Ó&û\Ò','1959-10-21','Ken Watanabe is a Japanese actor. To English-speaking audiences, he is known for playing tragic hero characters, such as General Tadamichi Kuribayashi in Letters from Iwo Jima and Lord Katsumoto Moritsugu in The Last Samurai, for which he was nominated for the Academy Award for Best Supporting Actor.','Ken Watanabe','http://res.cloudinary.com/dfths157i/image/upload/v1729911633/actors/kenwatanabe.jpg'),(_binary 'q\ÀP_G}G1∞Ñ\ƒ\ÌT|.','1931-01-05','Robert Selden Duvall is an American actor. With a career spanning seven decades, he is regarded as one of the greatest actors of all time. He is the recipient of an Academy Award, four Golden Globe Awards, a BAFTA Award, two Primetime Emmy Awards, and a Screen Actors Guild Award.','Robert Duvall','http://res.cloudinary.com/dfths157i/image/upload/v1729921781/actors/robertduvall.jpg'),(_binary '|\·\›\Ë	¸@V∑ØQ\ı{<','1940-04-25','One of America\'s most acclaimed actors, known for his roles in \'The Godfather\', \'Scarface\', and \'Heat\'. He has worked with Martin Scorsese in \'The Irishman\'.','Al Pacino','http://res.cloudinary.com/dfths157i/image/upload/v1729904389/actors/alpacino.jpg'),(_binary 'ê\Í\ÊÇ\Á2FkµΩF¿\«l\'$','1963-12-18','An American actor known for his performances in \'Fight Club\', \'Seven\', and \'Once Upon a Time in Hollywood\'. He worked with David Fincher on \'Seven\' and \'Fight Club\'.','Brad Pitt','http://res.cloudinary.com/dfths157i/image/upload/v1729904595/actors/bradpitt.jpg'),(_binary '®3ù\»\ÁIå∞N\Â0•ç\‚','1958-10-20','An American actor best known for his role as Aragorn in \'The Lord of the Rings\' trilogy directed by Peter Jackson.','Viggo Mortensen','http://res.cloudinary.com/dfths157i/image/upload/v1729904342/actors/viggomortensen.jpg'),(_binary '∞?<´5@ã≤#aq\»k%\˜','1948-12-21','Known for his distinctive voice and roles in \'Pulp Fiction\', \'Django Unchained\', and the Marvel Cinematic Universe. He has frequently worked with Quentin Tarantino.','Samuel L. Jackson','http://res.cloudinary.com/dfths157i/image/upload/v1729901654/actors/samuelljackson.jpg'),(_binary 'µÜ/ ?OAª\Ë˚]=;Ñ','1956-07-09','An American actor known for his versatile roles in movies like \'Forrest Gump\', \'Saving Private Ryan\', and \'Cast Away\'. He has worked with Steven Spielberg in films such as \'Saving Private Ryan\' and \'Catch Me If You Can\'.','Tom Hanks','http://res.cloudinary.com/dfths157i/image/upload/v1729901432/actors/tomhanks.jpg'),(_binary '\◊@\Ûù\ŒBJBºEô¶≠B¸\”','1949-10-08','An American actress known for her role as Ellen Ripley in the \'Alien\' series. She worked with James Cameron on \'Aliens\' and later in \'Avatar\'.','Sigourney Weaver','http://res.cloudinary.com/dfths157i/image/upload/v1729901702/actors/sigourneyweaver.jpg'),(_binary '\Ÿ&»∂\ÍDƒ´r‘¥ Ø>ç','1972-09-27','An American actress known for roles in \'Shakespeare in Love\', \'Iron Man\', and \'The Royal Tenenbaums\'. She worked with Wes Anderson in \'The Royal Tenenbaums\'.','Gwyneth Paltrow','http://res.cloudinary.com/dfths157i/image/upload/v1729904546/actors/gwynethpaltrow.jpg'),(_binary '\Ìﬁ¶-∏y@|ï\√[\·Z’ø\ﬂ','1977-09-15','Edward Thomas Hardy CBE is an English actor. After studying acting at the Drama Centre London, Hardy made his film debut in Ridley Scott\'s Black Hawk Down in 2001.','Tom Hardy','http://res.cloudinary.com/dfths157i/image/upload/v1729910678/actors/tomhardy.jpg'),(_binary '˚e\˜r1\◊EÅ\‚?í1£{\n','1974-11-11','An American actor recognized for his roles in \'Titanic\', \'Inception\', and \'The Wolf of Wall Street\'. He has collaborated with both Martin Scorsese in \'The Departed\' and Quentin Tarantino in \'Django Unchained\' and \'Once Upon a Time in Hollywood\'.','Leonardo DiCaprio','http://res.cloudinary.com/dfths157i/image/upload/v1729901489/actors/leonardodicaprio.jpg');
/*!40000 ALTER TABLE `actors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `director_movie`
--

DROP TABLE IF EXISTS `director_movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `director_movie` (
  `movie_id` binary(16) NOT NULL,
  `director_id` binary(16) NOT NULL,
  PRIMARY KEY (`movie_id`,`director_id`),
  KEY `FKretofew60id5eferiuntbyw9y` (`director_id`),
  CONSTRAINT `FKcvl9srlsi82ak35g3u3gryviq` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
  CONSTRAINT `FKretofew60id5eferiuntbyw9y` FOREIGN KEY (`director_id`) REFERENCES `directors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `director_movie`
--

LOCK TABLES `director_movie` WRITE;
/*!40000 ALTER TABLE `director_movie` DISABLE KEYS */;
INSERT INTO `director_movie` VALUES (_binary 'Qõñw=G§Ä\ﬂO\ı	\'m\≈',_binary '≤˝ØãûH‹Ür!É\ı}*\Ó'),(_binary 'ó¿\‚.π∞J∞¨˚\Ÿ\r¿0',_binary 'z¸<ºLlñ3\ˆC\Õ>\Î'),(_binary '\È∑@`ºBæï\Ì\0Ö\ﬂGE',_binary '‘§~0\≈NÇü\n&\Ê *®m');
/*!40000 ALTER TABLE `director_movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `directors`
--

DROP TABLE IF EXISTS `directors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `directors` (
  `id` binary(16) NOT NULL,
  `dob` date DEFAULT NULL,
  `mini_biography` text NOT NULL,
  `name` varchar(255) NOT NULL,
  `profile_picture_url` varchar(2000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `directors`
--

LOCK TABLES `directors` WRITE;
/*!40000 ALTER TABLE `directors` DISABLE KEYS */;
INSERT INTO `directors` VALUES (_binary 'Í°≤EFõQ\\e\Ê:ª,','1958-08-25','Famous for his dark, gothic style in films like \'Edward Scissorhands\' and \'The Nightmare Before Christmas\'.','Tim Burton','http://res.cloudinary.com/dfths157i/image/upload/v1729852676/directors/timburton.jpg'),(_binary 'N(\≈JVEàdw\ZMIr','1942-11-17','Renowned for crime and drama films such as \'Goodfellas\', \'Taxi Driver\', and \'The Irishman\'.','Martin Scorsese','http://res.cloudinary.com/dfths157i/image/upload/v1729851826/directors/martinscorsese.jpg'),(_binary 'UâncwF\\£UJ5üÇ˚','1961-10-31','Known for \'The Lord of the Rings\' trilogy and \'The Hobbit\' series.','Peter Jackson','http://res.cloudinary.com/dfths157i/image/upload/v1729852522/directors/peterjackson.jpg'),(_binary '≤˝ØãûH‹Ür!É\ı}*\Ó','1939-04-07','Director of \'The Godfather\' series and \'Apocalypse Now\'.','Francis Ford Coppola','http://res.cloudinary.com/dfths157i/image/upload/v1729852573/directors/francisfordcoppola.jpg'),(_binary '4¶$:D¨Ik†Ñ\Ú(ºzC','1962-08-28','Known for \'Fight Club\', \'Se7en\', and \'The Social Network\'.','David Fincher','http://res.cloudinary.com/dfths157i/image/upload/v1729852868/directors/davidfincher.jpg'),(_binary 'o\ÿ6&ëSMvπ©*F\Ó¿','1969-05-01','Known for quirky, stylized films such as \'The Grand Budapest Hotel\' and \'Fantastic Mr. Fox\'.','Wes Anderson','http://res.cloudinary.com/dfths157i/image/upload/v1729852757/directors/wesanderson.jpg'),(_binary 'z¸<ºLlñ3\ˆC\Õ>\Î','1963-03-27','Famous for his unique style and dialogue-heavy films like \'Pulp Fiction\' and \'Kill Bill\'.','Quentin Tarantino','http://res.cloudinary.com/dfths157i/image/upload/v1729852281/directors/quentintarantino.jpg'),(_binary 'ïÿ∑ì\ÎL#æ4&ÜJ˚É','1928-07-26','Legendary director of classics like \'2001: A Space Odyssey\', \'The Shining\', and \'A Clockwork Orange\'.','Stanley Kubrick','http://res.cloudinary.com/dfths157i/image/upload/v1729852466/directors/stanleykubrick.jpg'),(_binary '´S£˘_A∑≥$∞hŸπ\Ë\Ì','1937-11-30','Known for sci-fi classics like \'Alien\' and \'Blade Runner\', as well as \'Gladiator\'.','Ridley Scott','http://res.cloudinary.com/dfths157i/image/upload/v1729852625/directors/ridleyscott.jpg'),(_binary '´\€\Â]y\’@QÆT?\ÏÄur','1946-12-18','An iconic filmmaker known for blockbuster movies like \'Jaws\', \'E.T.\', and \'Jurassic Park\'.','Steven Spielberg','http://res.cloudinary.com/dfths157i/image/upload/v1729851675/directors/stevenspielberg.jpg'),(_binary '∏\\Ú¨\ÚEâà…ä?Ä¿','1899-08-13','Master of suspense known for films like \'Psycho\', \'Vertigo\', and \'Rear Window\'.','Alfred Hitchcock','http://res.cloudinary.com/dfths157i/image/upload/v1729852400/directors/alfredhitchcock.jpg'),(_binary '\∆\‰Ÿ©F£òYjOMÃò','1957-03-20','Director known for films like \'Do the Right Thing\' and \'Malcolm X\'.','Spike Lee','http://res.cloudinary.com/dfths157i/image/upload/v1729899551/directors/spikelee.jpg'),(_binary '\“\'Ç1Gë)ßø7^\ﬂ','1954-08-16','Sci-fi and action director known for \'Avatar\', \'Titanic\', and \'The Terminator\'.','James Cameron','http://res.cloudinary.com/dfths157i/image/upload/v1729852321/directors/jamescameron.jpg'),(_binary '‘§~0\≈NÇü\n&\Ê *®m','1970-07-30','Known for complex, non-linear storytelling in films like \'Inception\', \'Interstellar\', and \'The Dark Knight\'.','Christopher Nolan','http://res.cloudinary.com/dfths157i/image/upload/v1729851728/directors/christophernolan.jpg'),(_binary '\ﬁP3i\ÚõF\Í∞\Ë\…[$\F*','1930-05-31','Actor and director of films such as \'Unforgiven\' and \'Million Dollar Baby\'.','Clint Eastwood','http://res.cloudinary.com/dfths157i/image/upload/v1729899606/directors/clinteastwood.jpg');
/*!40000 ALTER TABLE `directors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movies` (
  `id` binary(16) NOT NULL,
  `movie_poster_url` varchar(2000) NOT NULL,
  `release_year` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `studio_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj3lislm2u9pdjpymgnmflxo01` (`studio_id`),
  CONSTRAINT `FKj3lislm2u9pdjpymgnmflxo01` FOREIGN KEY (`studio_id`) REFERENCES `studios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (_binary 'Qõñw=G§Ä\ﬂO\ı	\'m\≈','https://res.cloudinary.com/dfths157i/image/upload/v1729199808/directors/default_pfp.jpg',1972,'The Godfather',_binary '•\"f£XóN\‡è≤˚†\Ê'),(_binary 'ó¿\‚.π∞J∞¨˚\Ÿ\r¿0','https://res.cloudinary.com/dfths157i/image/upload/v1729199808/directors/default_pfp.jpg',1994,'Pulp Fiction',_binary '˛ØfˇC@´=a\“º9x'),(_binary '\È∑@`ºBæï\Ì\0Ö\ﬂGE','https://res.cloudinary.com/dfths157i/image/upload/v1729199808/directors/default_pfp.jpg',2010,'Inception',_binary 'XI¶\ŸI2ÉØrjLt/');
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studios`
--

DROP TABLE IF EXISTS `studios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studios` (
  `id` binary(16) NOT NULL,
  `description` text NOT NULL,
  `name` varchar(255) NOT NULL,
  `studio_profile_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studios`
--

LOCK TABLES `studios` WRITE;
/*!40000 ALTER TABLE `studios` DISABLE KEYS */;
INSERT INTO `studios` VALUES (_binary 'Äc∫åvJQÜY⁄ñ1x\◊','Originally 20th Century Fox, now owned by Disney, it\'s known for movies like Avatar, Alien, and X-Men.','20th Century Studios','http://res.cloudinary.com/dfths157i/image/upload/v1729900684/studios/20thcenturystudios.jpg'),(_binary 'XI¶\ŸI2ÉØrjLt/','One of the \'Big Five\' American film studios, known for franchises like Harry Potter, DC Universe, and The Matrix.','Warner Bros. Pictures','http://res.cloudinary.com/dfths157i/image/upload/v1729900464/studios/warnerbrospictures.jpg'),(_binary '\'âSéPK\ı´£	h}s	','A major studio owned by Sony, Columbia is known for franchises like Spider-Man, Men in Black, and Ghostbusters.','Columbia Pictures','http://res.cloudinary.com/dfths157i/image/upload/v1729900731/studios/columbiapictures.jpg'),(_binary ')ív\ÒRùE4Üº>\ˆ=@\›','An American film studio known for blockbuster franchises like The Hunger Games, Twilight, and John Wick.','Lionsgate Films','http://res.cloudinary.com/dfths157i/image/upload/v1729900823/studios/lionsgatefilms.jpg'),(_binary ';é\‰Lh~A[±5\∆#I\‡','Known for family-friendly movies and animated classics, Disney owns Pixar, Marvel Studios, and Lucasfilm.','Walt Disney Pictures','http://res.cloudinary.com/dfths157i/image/upload/v1729900630/studios/waltdisneypictures.jpg'),(_binary 'SEÄ!∑N£ûÀù1nbfZ','Founded in 1912, Universal is one of the oldest film studios in the US, known for Jurassic Park, Fast & Furious, and classic monster films.','Universal Pictures','http://res.cloudinary.com/dfths157i/image/upload/v1729900549/studios/universalpictures.png'),(_binary 'X\Àè®Cñú∏\ÈÖ~#é','Founded by Steven Spielberg, Jeffrey Katzenberg, and David Geffen, known for live-action films and animation under DreamWorks Animation.','DreamWorks Pictures','http://res.cloudinary.com/dfths157i/image/upload/v1729900911/studios/dreamworkspictures.jpg'),(_binary '•\"f£XóN\‡è≤˚†\Ê','The second oldest American film studio, famous for franchises like Mission Impossible, Star Trek, and Transformers.','Paramount Pictures','http://res.cloudinary.com/dfths157i/image/upload/v1729900590/studios/paramountpictures.png'),(_binary 'ºÎ©ºùOôâ\\›\Î(ã]','Founded in 1924, MGM is known for the James Bond series, Rocky, and classic musicals like The Wizard of Oz.','MGM Studios','http://res.cloudinary.com/dfths157i/image/upload/v1729900867/studios/mgmstudios.jpg'),(_binary '\Ë\Ë”´nG`≥\‡R4:I¯','A division of Sony Pictures, focused on animated movies like Hotel Transylvania, Spider-Man: Into the Spider-Verse, and The Emoji Movie.','Sony Pictures Animation','http://res.cloudinary.com/dfths157i/image/upload/v1729900768/studios/sonypicturesanimation.jpg'),(_binary '˛ØfˇC@´=a\“º9x','Miramax, LLC, formerly known as Miramax Films, is an American independent film and television production and distribution company founded on December 19, 1979, by Harvey and Bob Weinstein, and based in Los Angeles, California. Today, it is owned by beIN Media Group and Paramount Global.','Miramax','http://res.cloudinary.com/dfths157i/image/upload/v1729922306/studios/miramax.jpg');
/*!40000 ALTER TABLE `studios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-26 18:32:43
