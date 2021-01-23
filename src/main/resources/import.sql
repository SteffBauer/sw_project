-- Employee
insert into employee (id, forename, surname, username, designation, salary, password, birth_date) values (9001, 'Stefan', 'Bauer', 'admin', 'admin', 1000000, '$2a$15$cgpaU6ClArLV103pKmTTj.RLCrmvUI3uk5vpwGb6zNdeHi3v07isO', '1985-07-09');
insert into employee (id, forename, surname, username, designation, salary, password, birth_date) values (9002, 'Max', 'Muster', 'max', 'account manager', 1000000, '$2a$15$cgpaU6ClArLV103pKmTTj.RLCrmvUI3uk5vpwGb6zNdeHi3v07isO', '1985-01-17');
insert into employee (id, forename, surname, username, designation, salary, password, birth_date) values (9003, 'Thomas', 'Lübbe', 'thomas', 'account manager', 1000000, '$2a$15$cgpaU6ClArLV103pKmTTj.RLCrmvUI3uk5vpwGb6zNdeHi3v07isO', '1985-12-22');
-- insert into customer (id, forename, surname, username, )
-- todo insert user for vitalis
-- 3,1,2000-01-01 01:00:00,vigo,$2a$15$NmuGDkDpnpvFDlSAXV/uIO.CxQBLYRwOYTMd5CYH1Ixh/T.aSftHW,pay,vigopay,1234567890,4,9003
-- Privilege
insert into privilege (id, privilege_name) value (999101, 'ROLE_ADMIN');
insert into privilege (id, privilege_name) value (999102, 'ROLE_EMPLOYEE');
insert into privilege (id, privilege_name) value (999103, 'ROLE_CUSTOMER');
-- Person Authority: Admin & Employee
insert into person_authority (id, person_id, privilege_id) values (9401, 9001, 999101);
insert into person_authority (id, person_id, privilege_id) values (9402, 9001, 999102);
insert into person_authority (id, person_id, privilege_id) values (9403, 9002, 999102);
insert into person_authority (id, person_id, privilege_id) values (9404, 9003, 999102);