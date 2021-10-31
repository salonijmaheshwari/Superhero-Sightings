DROP DATABASE IF EXISTS superHeroSightUpdated;

CREATE DATABASE superHeroSightUpdated;

Use superHeroSightUpdated;

CREATE TABLE superPower (
	powerId INT PRIMARY KEY AUTO_INCREMENT,
    superPowerName VARCHAR(30) NOT NULL
);

CREATE TABLE superHero(
    heroId INT PRIMARY KEY AUTO_INCREMENT,
    heroName VARCHAR(15) NOT NULL,
    heroDescription VARCHAR(255),
    powerId INT NOT NULL,
    FOREIGN KEY (powerId) REFERENCES superPower(powerId)
);

CREATE TABLE organizations(
    orgId INT PRIMARY KEY AUTO_INCREMENT,
    orgName VARCHAR(50) NOT NULL,
    orgDescription VARCHAR(255),
    orgContact VARCHAR(30) NOT NULL,
    orgAddress VARCHAR(100)
);

CREATE TABLE location(
    locationId INT PRIMARY KEY AUTO_INCREMENT,
    locationName VARCHAR(50) NOT NULL,
    locationDescription VARCHAR(255),
    locationAddress VARCHAR(100),
    latitude DECIMAL(10,8),
    longitude DECIMAL(11,8)
);

CREATE TABLE hero_organization(
    heroId INT NOT NULL,
    orgId INT NOT NULL,
    PRIMARY KEY(heroId, orgId),
    FOREIGN KEY (heroId) REFERENCES superHero(heroId),
    FOREIGN KEY (orgId) REFERENCES organizations(orgId)
);

CREATE TABLE hero_location(
    heroId INT NOT NULL,
    locationId INT NOT NULL,
    PRIMARY KEY(heroId, locationId),
    FOREIGN KEY (heroId) REFERENCES superHero(heroId),
    FOREIGN KEY (locationId) REFERENCES location(locationId)
);

CREATE TABLE heroSight(
	sightId INT PRIMARY KEY AUTO_INCREMENT,
    sightDate DATE NOT NULL,
    heroId INT NOT NULL,
    locationId INT NOT NULL,
    FOREIGN KEY(heroId, locationId) REFERENCES hero_location(heroId, locationId)
);
    
    
	
