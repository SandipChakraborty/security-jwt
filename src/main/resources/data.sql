
--select * from users_table;
insert into users_table (user_id, username, role, password) values (nextval ('user_id_seq'), 'SandipAdmin', 'Admin', '$2a$10$DNIaGeSRsB0rwjPH1oKfzen2dtpPuPtclNBwfsw/Fg2qxQB/2PFgy');
insert into users_table (user_id, username, role, password) values (nextval ('user_id_seq'), 'SandipUser', 'User', '$2a$10$DNIaGeSRsB0rwjPH1oKfzen2dtpPuPtclNBwfsw/Fg2qxQB/2PFgy');