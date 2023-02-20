insert into users (id, avatar, first_name, last_name, email, password)
    values(0, 'defaultAvatar.jpeg', 'ADMIN', 'ADMIN', 'admin@test.project', '$2a$08$tlB35/70mJqWkBIlnV8VtOJoETa.dpAPmpP/XBW7tFAjckHKGxksG');

insert into user_role (user_id, roles)
    values (0, 'ADMIN');