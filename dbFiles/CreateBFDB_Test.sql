drop database if exists BigFootDB_Test;

create database BigFootDB_Test;

use BigFootDB_Test;

create table Item (
  itemId int(5) not null primary key auto_increment,
  itemName varchar(45) not null,
  description varchar(100), 
  itemValue decimal(7,2),
  featuredItem boolean,
  imageTitle varchar(45), 
  filename varchar(100)
);

create table Category
(
	categoryId int(5) not null primary key auto_increment, 
    categoryName varchar(45) not null
); 

create table ItemCategory
(
    FK_itemId int(5),
    FK_categoryId int(5),
    
    primary key (FK_itemId, FK_categoryId)
--     foreign key (FK_itemId) references Item(itemId),
--     foreign key (FK_categoryId) references Category(categoryId)
);

-- create table User
-- (
-- 	userId int(6) not null primary key auto_increment, 
--     userName varchar(45) not null,
--     permission varchar(45) not null
-- );

-- create table Article
-- (
-- 	articleId int(5) not null primary key auto_increment, 
--     articleDate date,
--     content text(5000),
--     FK_userId int(6), 
--     
--     foreign key (FK_userId) references users(user_Id)
-- );


CREATE TABLE IF NOT EXISTS users (
 user_id int(11) NOT NULL AUTO_INCREMENT,
 username varchar(20) NOT NULL,
 password varchar(100) NOT NULL,
 -- enabled tinyint(1) NOT NULL,
 PRIMARY KEY (user_id),
 KEY user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

create table Article
(
	articleId int(5) not null primary key auto_increment, 
    articleDate date,
    content text(5000),
    FK_userId int(6)
    
    -- foreign key (FK_userId) references users(user_Id)
);

--
-- Table structure for table `authorities`
--
CREATE TABLE IF NOT EXISTS authorities (
user_id int(11) NOT NULL,
 username varchar(20) NOT NULL,
 authority varchar(100) NOT NULL,
 KEY user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Dumping data for table `authorities`

-- Constraints for table `authorities`
--
ALTER TABLE authorities
 ADD CONSTRAINT authorities_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (user_id);
 ALTER TABLE Article ADD CONSTRAINT authorities_ibfk_2 foreign key (FK_userId) references users(user_Id);
 ALTER TABLE ItemCategory ADD CONSTRAINT authorities_ibfk_3 foreign key (FK_itemId) references Item(itemId);
 ALTER TABLE ItemCategory ADD CONSTRAINT authorities_ibfk_4 foreign key (FK_categoryId) references Category(categoryId);
