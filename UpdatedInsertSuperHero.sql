USE superHeroSightUpdated;

INSERT INTO superpower(superPowerName) VALUES
	("Flight"),
    ("Invisibility"),
    ("Healing"),
    ("Super Speed"),
    ("Under Water Control"),
    ("Telekinesis"),
    ("Jumping"),
    ("Shape Shifting"),
    ("Time Travel");
    
INSERT INTO superHero(heroName,heroDescription,powerID) VALUES
	("Superman","America’s first superhero",4),
    ("Spiderman","This Marvel character can web-crawl, leap across buildings",7),
    ("Ironman","He gets power from his suit",3);
    
INSERT INTO organizations(orgName,orgDescription,orgcontact,orgAddress) VALUES
	("Marvel Entertainment","Founded in June 1998","212-576-4000","NewYork"),
    ("DC Entertainment","","1-888-516-7365","California");
    
INSERT INTO hero_organization(heroId,orgID) VALUES
	(1,1),
    (2,1),(2,2),
    (3,2);
    
INSERT INTO location(locationName,locationDescription,locationAddress,latitude,longitude) VALUES
	("Eiffel Tower","1063 ft tall","Paris",84.5672,125.234),
    ("Taj Mahal","one of Seven wonders ","Agra",8.5672,25.234),
    ("Qutub Minar","UNESCO World Heritage Site","Delhi",54.5672,178.234),
    ("Statue of Liberty","151 ft tall","NewYork",45.2541,100.5385);
    
INSERT INTO hero_location(heroId,locationId) VALUES
	(1,1),(1,2),
    (2,1),(2,2),
    (3,1),(3,3);




INSERT INTO heroSight(sightDate,heroId,locationId) VALUES
	('2021-11-11',2,1),
    ('2021-10-29',1,2),
    ('2021-12-01',3,1),
    ('2022-01-01',1,1),
    ('2021-01-31',1,2),
    ('2021-01-31',2,2),
    ('2021-01-31',1,1);

    


	
    

    
	