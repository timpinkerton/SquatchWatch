use BigFootDB_Test; 


insert into Item  (itemName, description, itemValue, featuredItem, imageTitle, filename) value ('Dart Gun', 'For putting Bigfoot to sleep', 4000, TRUE, 'dart gun photo', '/filename/here/there'); 
insert into Item  (itemName, description, itemValue, featuredItem, imageTitle, filename) value ('Go Pro', 'Photo shoot with BF', 500,  FALSE, 'photo of go pro', '/filename/here/there'); 
insert into Item  (itemName, description, itemValue, featuredItem, imageTitle, filename) value ('Ninja Sword', 'In case Big Foot attacks', 265,  TRUE,'sword pic', '/filename/here/there'); 
insert into Item  (itemName, description, itemValue, featuredItem, imageTitle, filename) value ('Night Vision Goggles', 'Seeing in the dark', 1000,  TRUE,'goggles', '/filename/here/there'); 
insert into Item  (itemName, description, itemValue, featuredItem, imageTitle, filename) value ('Boots', 'Good for walking through the forest', 120, FALSE, 'boots pic', '/filename/here/there'); 
insert into Item  (itemName, description, itemValue, featuredItem, imageTitle, filename) value ('Big Foot hair', 'In case Big Foot attacks', 265, FALSE, 'photo of hair', '/filename/here/there'); 


insert into Category(categoryName) value ('Weapon'); 
insert into Category(categoryName) value ('Evidence');
insert into Category(categoryName) value ('Camera');
insert into Category(categoryName) value ('Traps');
insert into Category(categoryName) value ('Gear');

insert into ItemCategory(FK_itemId , FK_categoryId) value (1, 1); 
insert into ItemCategory(FK_itemId , FK_categoryId) value (2, 3); 
insert into ItemCategory(FK_itemId , FK_categoryId) value (3, 1); 
insert into ItemCategory(FK_itemId , FK_categoryId) value (4, 5); 
insert into ItemCategory(FK_itemId , FK_categoryId) value (5, 2); 

insert into User (userName, permission) value ('Kyle', 'Admin'); 
insert into User (userName, permission) value ('Ausin', 'Admin'); 
insert into User (userName, permission) value ('Ollie', 'Admin'); 
insert into User (userName, permission) value ('Chris', 'Admin'); 

insert into Article(articleDate, content, FK_userId) value ('2018-01-02', 'some written text about sasquatch stuff', 1); 
insert into Article(articleDate, content, FK_userId) value ('2018-02-03', 'talking about a new bigfoot camera', 3); 
