-- query - 1
create database PetPals;
use PetPals;

-- quer - 2,3,4
create table if not exists shelters (
	shelter_id int primary key auto_increment,
    name varchar(40) not null,
    location varchar(255) not null
);

create table if not exists pets(
	pet_id int primary key auto_increment,
    name varchar(40) not null,
    age int not null,
    breed varchar(40) not null,
    type varchar(40) not null,
    available_for_adoption bit not null default 1,
    shelterId int,
    foreign key (shelterId) references shelters(shelter_id) on delete set null
);
create table if not exists donations (
	donationId int primary key auto_increment,
    DonorName varchar(255) not null,
    donationtype enum('cash', 'item') not null,
    donationamount decimal(10,2) default null,
    donationitem varchar(255) default null,
    donationdate datetime not null,
    shelterid int,
    foreign key (shelterid) references shelters(shelter_id) on delete cascade
);

create table if not exists adoptionevents (
    eventid int primary key auto_increment,
    eventname varchar(40) not null,
    eventdate datetime not null,
    location varchar(100) not null
);

create table if not exists participants (
    participantid int primary key auto_increment,
    participantname varchar(255) not null,
    participanttype enum('shelter', 'adopter') not null,
    eventid int,
    foreign key (eventid) references adoptionevents(eventid) on delete cascade
);
-- insert data
INSERT INTO shelters (name, location) VALUES
('Mumbai Animal Welfare Foundation', 'Mumbai, Maharashtra'),
('Delhi Pet Care Center', 'Delhi, Delhi'),
('Chennai Paws Shelter', 'Chennai, Tamil Nadu'),
('Bangalore Pet Rescue', 'Bangalore, Karnataka'),
('Hyderabad Animal Refuge', 'Hyderabad, Telangana');

INSERT INTO pets (name, age, breed, type, available_for_adoption, shelterId) VALUES
('Johny', 3, 'Pug', 'Dog', 0, 1);
INSERT INTO pets (name, age, breed, type, available_for_adoption, shelterId) VALUES
('Rocky', 3, 'Labrador', 'Dog', 1, 1),
('Simba', 2, 'Persian', 'Cat', 1, 2),
('Pinky', 4, 'Beagle', 'Dog', 1, 3),
('Milo', 1, 'Indian Pariah', 'Dog', 1, 4),
('Whiskers', 2, 'Bengal', 'Cat', 1, 5);

INSERT INTO donations (DonorName, donationtype, donationamount, donationitem, donationdate, shelterid) VALUES
('Amit Sharma', 'cash', 5000.00, NULL, '2025-03-01 10:00:00', 1),
('Neha Gupta', 'item', NULL, 'Cat Toys', '2025-03-02 11:30:00', 2),
('Ravi Kumar', 'cash', 2000.00, NULL, '2025-03-03 09:00:00', 3),
('Priya Patel', 'item', NULL, 'Dog Leash', '2025-03-04 15:00:00', 4),
('Vikas Reddy', 'cash', 3000.00, NULL, '2025-03-05 13:00:00', 5);

INSERT INTO adoptionevents (eventname, eventdate, location) VALUES
('Mumbai Adoption Day', '2025-04-01 10:00:00', 'Mumbai, Maharashtra'),
('Delhi Paws Event', '2025-05-15 09:00:00', 'Delhi, Delhi'),
('Chennai Adoption Fair', '2025-09-20 11:00:00', 'Chennai, Tamil Nadu'),
('Bangalore Pet Festival', '2025-12-10 12:00:00', 'Bangalore, Karnataka'),
('Hyderabad Adoption Drive', '2025-06-25 14:00:00', 'Hyderabad, Telangana');

INSERT INTO participants (participantname, participanttype, eventid) VALUES
('Sanjay Mehta', 'shelter', 1),
('Ritu Verma', 'adopter', 2),
('Vijay Singh', 'shelter', 3),
('Aarti Joshi', 'adopter', 4),
('Manoj Kumar', 'adopter', 5);


-- query - 5
select name,age,breed,type
from pets 
where available_for_adoption=1;

--  query - 6
delimiter //
create procedure showParticipants(in eventId int)
begin
    select p.participantname,p.participanttype
    from participants p
    join adoptionevents e
    on e.eventid=eventId
    where e.eventid=eventId
end //
delimiter ;
CALL showParticipants(1);

-- query - 7
delimiter //
create procedure updateshelter(in ShelterID int, in newname varchar(255), in newlocation varchar(255))
begin
    update shelters
    set name = NewName, location = newlocation
    where shelter_id = ShelterID;
end //
delimiter ;
CALL updateshelter(1, 'new shelter', 'new location');

-- query - 8
select s.name,ifnull(sum(d.donationamount),0) TotalDonations
from shelters s
left join donations d
on s.shelter_id=d.shelterid
group by s.name;

-- query - 9
select name,age,breed,type 
from pets
where shelterId is null;

-- query - 10
select date_format(donationdate, '%Y-%M') MonthYear, 
       sum(donationamount) AS TotalDonations
from donations
group by MonthYear;

-- query - 11
select distinct breed
from pets
where age between 1 and 3
or age>5;

-- query - 12
select p.name petName,s.name ShelterName
from pets p
join shelters s on p.shelterId=s.shelter_id
where p.available_for_adoption=1;

-- query - 13
select e.location, count(*) as totalParticipants
from participants p
join adoptionevents e
on e.eventid=p.eventid
where e.location='Chennai, Tamil Nadu';

--  query - 14
select distinct breed
from pets
where age between 1 and 5;

--  query - 15
select name, age, breed, type 
from Pets 
where available_for_adoption = 1;

--  query - 17
select s.name,count(p.pet_id) as AvailablePets
from pets p
left join shelters s
on s.shelter_id=p.shelterId and p.available_for_adoption=1
group by s.name;

--  query - 18
select p1.name as Pet1, p2.name as Pet2,p1.breed as Breed , s.name as Shelter
from pets p1
join pets p2 on p1.shelterId=p2.shelterId and p1.breed=p2.breed and p1.pet_id<p2.pet_id
join shelters s on p1.shelterId=s.shelter_id;

-- query - 19
select s.name AS ShelterName, e.eventname 
FROM shelters s 
CROSS JOIN adoptionevents e;

-- query - 20
select s.shelter_id, s.name, count(p.pet_id) AS adopted_pets_count
from pets p
join shelters s ON p.shelterId = s.shelter_id
where p.available_for_adoption = 0
group by s.shelter_id
order by adopted_pets_count desc
limit 1
