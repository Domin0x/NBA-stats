CREATE DATABASE  IF NOT EXISTS `nba`;
USE `nba`;
ALTER DATABASE `nba` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
drop table seasonstats;
drop table players;
drop table teams;


CREATE TABLE `teams` (
  id INT AUTO_INCREMENT,
  rest_api_id INT,
  full_name varchar(45) CHARACTER SET 'utf8mb4' NOT NULL,
  title varchar(45) CHARACTER SET 'utf8mb4',
  abbreviation varchar(3) CHARACTER SET 'utf8mb4',
  conference varchar(45) CHARACTER SET 'utf8mb4' DEFAULT NULL ,
  city varchar(45) CHARACTER SET 'utf8mb4' DEFAULT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
CREATE TABLE `players` (
  id INT AUTO_INCREMENT,
  rest_api_id INT,
  first_name varchar(45) CHARACTER SET 'utf8mb4' NOT NULL,
  last_name varchar(45) CHARACTER SET 'utf8mb4',
  position varchar(10) CHARACTER SET 'utf8mb4',
  height INT DEFAULT NULL,
  weight INT DEFAULT NULL,
  team_id INT DEFAULT NULL,
  FOREIGN KEY (team_id) REFERENCES teams(id) ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `seasonstats` (
	season INT,
    player_id INT,
    games_played INT,
    time_played INT,
    fgm DECIMAL(4,2),
    fga DECIMAL(4,2),
    fg3m DECIMAL(4,2),
    fg3a DECIMAL(4,2),
    ftm DECIMAL(4,2),
    fta DECIMAL(4,2),
    oreb DECIMAL(4,2),
    dreb DECIMAL(4,2),
    reb DECIMAL(4,2),
    ast DECIMAL(4,2),
    blk DECIMAL(4,2),
    stl DECIMAL(4,2),
    turnovers DECIMAL(4,2),
    pf  DECIMAL(4,2),
    pts DECIMAL(4,2),
    fg_pct DECIMAL(6,3),
    fg3_pct DECIMAL(6,3),
    ft_pct DECIMAL(6,3),
    
     FOREIGN KEY (player_id) REFERENCES players(id) ,
     PRIMARY KEY (season, player_id)
    )ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
    