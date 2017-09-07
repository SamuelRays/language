create database words;

use words;

create table languages(
language varchar(100) not null,
primary key(language));

create table categories(
category varchar(100) not null,
primary key(category));

create table parts(
part_of_speech varchar(100) not null,
primary key(part_of_speech));

insert into parts values ("Noun");
insert into parts values ("Verb");
insert into parts values ("Adjective");
insert into parts values ("Adverb");
insert into parts values ("Conjunction");
insert into parts values ("Pronoun");
insert into parts values ("Preposition");
insert into parts values ("Interjection");
insert into parts values ("Phrase");

create table main(
word varchar(255) not null,
translation varchar(255) not null,
language varchar(100) not null,
category varchar(100) not null,
part_of_speech varchar(100) not null,
rate float not null,
uses int not null,
corrects int not null,
wrongs int not null,
add_date date not null,
last_used date,
foreign key(language) references languages(language),
foreign key(category) references categories(category),
foreign key(part_of_speech) references parts(part_of_speech));