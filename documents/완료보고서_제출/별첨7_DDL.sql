drop database if exists cafeDB;
create database cafeDB;

use cafeDB;

drop table if exists member;
create table member (
   id int primary key auto_increment,
   phone_number varchar(13) not null,
   point int default 0
);

drop table if exists order_state;
create table order_state (
   id int primary key auto_increment,
   name varchar(10) not null
);

drop table if exists `order`;
create table `order` (
   id int primary key auto_increment,
   member_id int not null,
   order_state_id int default 1 not null,
   total_payment_amount int,
   payment_time timestamp,
   order_type int,
   
   foreign key (member_id) references member(id),
   foreign key (order_state_id) references order_state(id)
);

drop table if exists category;
create table category (
	id int primary key auto_increment,
    name varchar(30)
);

drop table if exists product;
create table product (
   id int primary key auto_increment,
   category_id int,
   name varchar(50),
   price int,
   stock_count int,
   out_of_stock boolean,
   image_path varchar(255),
   
   foreign key (category_id) references category(id)
);

drop table if exists order_detail;
create table order_detail (
   id int primary key auto_increment,
   order_id int not null,
   product_id int not null,
   price int,
   `count` int,
   
   foreign key (order_id) references `order`(id),
   foreign key (product_id) references product(id)
);

drop table if exists option_type;
create table option_type(
   id int primary key auto_increment,
   name varchar(30),
   display_priority int,
   `duplicate` boolean
);

drop table if exists `option`;
create table `option` (
   id int primary key auto_increment,
   name varchar(30),
   price int,
   stock_count int,
   out_of_stock boolean,
   option_type_id int,
   
   foreign key (option_type_id) references option_type(id)
);

drop table if exists additional_option;
create table additional_option(
   id int primary key auto_increment,
   order_detail_id int not null,
   option_id int not null,
   `count` int,
      
   foreign key(order_detail_id) references order_detail(id),
   foreign key(option_id) references `option`(id)

);

drop table if exists product_option;
create table product_option(
   product_id int not null,
   option_id int not null,
   foreign key(product_id) references product(id),
   foreign key(option_id)  references `option`(id)
);

drop view if exists order_product_price;
create view order_product_price as
	select od.id id, p.price price
	from order_detail od
		join product p on od.product_id = p.id;
    

# 비회원 전용 record 추가
insert into member(phone_number) values ("000-0000-0000");

# View - 인기상품
drop view if exists popular_product;
create View popular_product as 
select product_id, count(product_id) 판매량
	from `order_detail`
    group by product_id;
;