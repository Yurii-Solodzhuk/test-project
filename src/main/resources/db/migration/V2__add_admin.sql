insert into users (id, avatar, first_name, last_name, email, password)
    values(1, 'defaultAvatar.jpeg', 'ADMIN', 'ADMIN', 'admin@test.project', 'admin');

insert into user_role (user_id, roles)
    values (1, 'ADMIN');