CREATE TABLE category ( 
   id INT NOT NULL, 
   name VARCHAR(250) NOT NULL,
   primary key(id)
);

CREATE TABLE product ( 
   id INT NOT NULL, 
   name VARCHAR(250) NOT NULL,
   category_id int not null,
   primary key(id),
   foreign key (category_id) references category(id)
);