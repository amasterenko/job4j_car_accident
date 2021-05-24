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