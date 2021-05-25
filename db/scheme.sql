CREATE TABLE type (
                          id serial primary key,
                          "name" varchar(50)
);
CREATE TABLE accident (
                          id serial primary key,
                          "name" varchar(50),
                          text varchar(2000),
                          address varchar(150),
                          type_id int not null references "type"(id)
);
CREATE TABLE rule (
                      id serial primary key,
                      "name" varchar(50)
);
CREATE TABLE accident_rule (
                      accident_id int not null references accident(id),
                      rule_id int not null references rule(id)
);

INSERT INTO rule (name) values ('Rule#1'), ('Rule#2'), ('Rule#3'), ('Rule#4');
INSERT INTO type (name) values ('Two cars'), ('Car and person'), ('Car and bike');

CREATE TABLE authorities (
                             id serial primary key,
                             authority VARCHAR(50) NOT NULL unique
);

CREATE TABLE users (
                       id serial primary key,
                       username VARCHAR(50) NOT NULL unique,
                       password VARCHAR(100) NOT NULL,
                       enabled boolean default true,
                       authority_id int not null references authorities(id)
);

insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (username, password, enabled, authority_id)
values ('admin', '$2a$10$bRxKx3mQzW3X/P4H.7GFgeQrAmPpBNxAkJGCU4t/vl1ZZcKAy7Upm', true,
        (select id from authorities where authority = 'ROLE_ADMIN'));