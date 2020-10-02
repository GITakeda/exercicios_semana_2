create database escola;

create table aluno(
	id int not null primary key auto_increment,
	nome VARCHAR(255) not null,
	idade int not null
);