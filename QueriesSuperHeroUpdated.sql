Use superherosightupdated;

-- A user must be able to record a superhero/supervillain sighting for a particular location and date.
INSERT INTO heroSight(sightDate,heroId,locationId) VALUES
	('2021-11-11 17:00:00',3,2);
    
-- The system must be able to report all of the superheroes sighted at a particular location.
SELECT sh.heroName,l.locationName FROM superHero sh JOIN
	hero_location hl ON sh.heroId = hl.heroId JOIN
    location l ON hl.locationId = l.locationId
    WHERE l.locationId = 1;

-- The system must be able to report all of the locations where a particular superhero has been seen.
SELECT l.locationName,sh.heroName FROM superHero sh JOIN
	hero_location hl ON sh.heroId = hl.heroId JOIN
    location l ON hl.locationId = l.locationId
    WHERE sh.heroId = 3; 

-- The system must be able to report all sightings (hero and location) for a particular date.
SELECT sh.heroName,l.locationName,hs.sightDate FROM superHero sh JOIN
	hero_location hl ON sh.heroId = hl.heroId JOIN
    location l ON hl.locationId = l.locationId AND hl.heroId = sh.heroId JOIN
    heroSight hs ON l.locationId = hs.locationId AND hs.heroId = sh.heroId
    WHERE hs.sightDate = '2021/11/11';
    
-- The system must be able to report all of the members of a particular organization.
SELECT sh.heroName,o.orgName FROM superHero sh JOIN
	hero_organization ho ON sh.heroId = ho.heroId JOIN
    organizations o ON ho.orgId = o.orgId
    WHERE ho.orgId = 1;
    
-- The system must be able to report all of the organizations a particular superhero/villain belongs to.
SELECT sh.heroName,o.orgName FROM superHero sh JOIN
	hero_organization ho ON sh.heroId = ho.heroId JOIN
    organizations o ON ho.orgId = o.orgId
    WHERE ho.heroId = 2;
    
Update location SET locationName = "Delhi" where locationId= 1;

update superHero SET heroName = "Batman" where heroId=3;

DELETE FROM superHero WHERE powerId = 4;

select * from superhero;

select * from hero_location;

delete from hero_location where locationId =1;

SELECT sh.* FROM superHero sh JOIN
	hero_location hl ON sh.heroId = hl.heroId JOIN
    location l ON hl.locationId = l.locationId AND hl.heroId = sh.heroId JOIN
    heroSight hs ON l.locationId = hs.locationId AND hs.heroId = sh.heroId where sightId = 1;

select * from herosight WHERE sightDate BETWEEN '2021/11/11 00:00:00' AND '2021/11/11 23:59:59' ;

select * from herosight WHERE sightDate='2021/11/11';

DELETE hs.* FROM herosight hs JOIN hero_location hl ON hs.heroId=hl.heroId JOIN superHero sh ON hl.heroId = sh.heroId WHERE sh.powerId = 7;

DELETE FROM herosight WHERE heroId=1;

SELECT * FROM herosight;

SELECT hs.sightId,sh.heroName,l.locationName,hs.sightDate FROM superHero sh JOIN
	hero_location hl ON sh.heroId = hl.heroId JOIN
    location l ON hl.locationId = l.locationId AND hl.heroId = sh.heroId JOIN
    heroSight hs ON l.locationId = hs.locationId AND hs.heroId = sh.heroId
    ORDER BY sightId DESC LIMIT 10;

