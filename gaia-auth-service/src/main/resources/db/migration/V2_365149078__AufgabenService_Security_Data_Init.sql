insert into _authorities (oid, auth_authority) values (365149078000000001,'AufgabenService_ADMIN');
insert into _authorities (oid, auth_authority) values (365149078000000002,'AufgabenService_READ_ONLY_USER');

insert into _users_authorities (authority_oid, user_oid) values (365149078000000001, 1);
insert into _users_authorities (authority_oid, user_oid) values (365149078000000001, 2);
insert into _users_authorities (authority_oid, user_oid) values (365149078000000001, 5);

insert into _users_authorities (authority_oid, user_oid) values (365149078000000002, 3);
insert into _users_authorities (authority_oid, user_oid) values (365149078000000002, 4);
insert into _permissions (oid,perm_permission) values ('365149078100000000','AufgabenService_READ_Mitarbeiter');
insert into _permissions (oid,perm_permission) values ('365149078200000000','AufgabenService_WRITE_Mitarbeiter');
insert into _permissions (oid,perm_permission) values ('365149078300000000','AufgabenService_DELETE_Mitarbeiter');
insert into _authorities_permissions (authority_oid, permission_oid) values ('365149078000000002','365149078100000000');
insert into _authorities_permissions (authority_oid, permission_oid) values ('365149078000000001','365149078100000000');
insert into _authorities_permissions (authority_oid, permission_oid) values ('365149078000000001','365149078200000000');
insert into _authorities_permissions (authority_oid, permission_oid) values ('365149078000000001','365149078300000000');

insert into _permissions (oid,perm_permission) values ('365149078100000001','AufgabenService_READ_Aufgabe');
insert into _permissions (oid,perm_permission) values ('365149078200000001','AufgabenService_WRITE_Aufgabe');
insert into _permissions (oid,perm_permission) values ('365149078300000001','AufgabenService_DELETE_Aufgabe');
insert into _authorities_permissions (authority_oid, permission_oid) values ('365149078000000002','365149078100000001');
insert into _authorities_permissions (authority_oid, permission_oid) values ('365149078000000001','365149078100000001');
insert into _authorities_permissions (authority_oid, permission_oid) values ('365149078000000001','365149078200000001');
insert into _authorities_permissions (authority_oid, permission_oid) values ('365149078000000001','365149078300000001');

insert into _permissions (oid,perm_permission) values ('365149078900000000','AufgabenService_BUSINESSACTION_MitarbeiterInformieren');
insert into _authorities_permissions (authority_oid, permission_oid) values ('365149078000000001','365149078900000000');

