-- Employee
insert into employee (id, forename, surname, username, designation, salary, password, birth_date) values (999911, 'Stefan', 'Bauer', 'admin', 'admin', 1000000, '$2a$15$cgpaU6ClArLV103pKmTTj.RLCrmvUI3uk5vpwGb6zNdeHi3v07isO', '1985-07-09');
insert into employee (id, forename, surname, username, designation, salary, password, birth_date) values (999912, 'Max', 'Muster', 'max', 'account manager', 1000000, '$2a$15$cgpaU6ClArLV103pKmTTj.RLCrmvUI3uk5vpwGb6zNdeHi3v07isO', '1985-01-17');
insert into employee (id, forename, surname, username, designation, salary, password, birth_date) values (999913, 'Thomas', 'Lübbe', 'thomas', 'account manager', 1000000, '$2a$15$cgpaU6ClArLV103pKmTTj.RLCrmvUI3uk5vpwGb6zNdeHi3v07isO', '1985-12-22');
-- Associate: vigopay
insert into address(id, street, house_nr, zip_code, city, country) values (999921, 'Lange Straße', 1, '65132', 'City', 'Germany');
insert into customer (id, forename, surname, username, tax_number, address_id, attendant, birth_date, password) values (999931, 'vigo', 'pay', 'vigopay', '1234567890', 999921, 999912, '2000-01-01', '$2a$15$NmuGDkDpnpvFDlSAXV/uIO.CxQBLYRwOYTMd5CYH1Ixh/T.aSftHW');
insert into account (id, balance, iban, customer_id) values (999941, 1000000, 'DE1001001000000999931', 999931)
-- Privilege
insert into privilege (id, privilege_name) value (999991, 'ROLE_ADMIN');
insert into privilege (id, privilege_name) value (999992, 'ROLE_EMPLOYEE');
insert into privilege (id, privilege_name) value (999993, 'ROLE_CUSTOMER');
-- Person Authority: Admin & Employee
insert into person_authority (id, person_id, privilege_id) values (6, 999911, 999991);
insert into person_authority (id, person_id, privilege_id) values (7, 999911, 999992);
insert into person_authority (id, person_id, privilege_id) values (8, 999912, 999992);
insert into person_authority (id, person_id, privilege_id) values (9, 999913, 999992);