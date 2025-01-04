-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
--ALTER TABLE product MODIFY COLUMN id int(11) AUTO_INCREMENT;
insert into product(id, name, description, price, quantity) values (1, 'Phones', 'Apple Smartphone iphone 15 Plus', 1903803.93, 10);
insert into product(id, name, description, price, quantity) values (2, 'Laptop', 'Samsung Original Laptop', 8732920.00, 2);
--insert into product(name, description, price, quantity) values ('Phones', 'Apple Smartphone iphone 15 Plus', 1903803.93, 10);

