use panaces;
select * from roles;
insert into roles(id,name) values(1,"ROLE_USER");
insert into roles(id,name) values(2,"ROLE_ADMIN");
select * from users;
select * from users_roles;
INSERT INTO 'panaces'.'users_roles' ('app_name', 'role_id', 'user_id') VALUES ('WORKFLOW_APP', '2', '1');

select * from privileges;
INSERT INTO 'panaces'.'privileges' ('id', 'name') VALUES ('1', 'READ_PRIVILEGE');
INSERT INTO 'panaces'.'privileges' ('id', 'name') VALUES ('2', 'WRITE_PRIVILEGE');

select * from roles_privileges;
insert into roles_privileges(role_id,privilege_id) values(1,1);
insert into roles_privileges(role_id,privilege_id) values(2,1);
insert into roles_privileges(role_id,privilege_id) values(2,2);
DELETE FROM roles_privileges
WHERE       role_id=2 and privilege_id=1
LIMIT       1;

drop database panaces;

select u.id,u.name from users u,users_roles ur, privileges p
where u.id=ur.user_id and ur.role_id=p.id;