drop table if exists hibernate_sequence;

drop table if exists user_role;

drop table if exists users;

create table hibernate_sequence (next_val bigint) engine=InnoDB;

insert into hibernate_sequence values ( 1 );

create table user_role (
    user_id bigint not null,
    roles varchar(255)
) engine=InnoDB;

create table users (
    id bigint not null, avatar varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    email varchar(255) not null,
    password varchar(255) not null,
    phone_number varchar(255),
    bio varchar(255),
    primary key (id)
) engine=InnoDB;

alter table user_role
    add constraint user_role_user_fk foreign key (user_id) references users (id);