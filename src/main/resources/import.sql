-- Employee
insert into employee (id, forename, surname, username, designation, salary, password, birth_date) values (9001, 'Employee Admin', 'Bauer', 'admin', 'admin', 1000000, '$2a$15$cgpaU6ClArLV103pKmTTj.RLCrmvUI3uk5vpwGb6zNdeHi3v07isO', '1996-9-09');
insert into employee (id, forename, surname, username, designation, salary, password, birth_date) values (9002, 'Employee 2', 'Bauer', 'e2', 'account manager', 1000000, '$2a$15$cgpaU6ClArLV103pKmTTj.RLCrmvUI3uk5vpwGb6zNdeHi3v07isO', '1996-9-09');
insert into employee (id, forename, surname, username, designation, salary, password, birth_date) values (9003, 'Employee 3', 'Bauer', 'e3', 'account manager', 1000000, '$2a$15$cgpaU6ClArLV103pKmTTj.RLCrmvUI3uk5vpwGb6zNdeHi3v07isO', '1996-9-09');
-- Address
insert into address (id, street, house_nr, zip_code, city, country) values (9101, 'Thenrieder Weg', 42, 93479, 'Grafenwiesen','Deutschland');
-- Customer
insert into customer (id, forename, surname, username, tax_number, address_id, attendant, password, birth_date) values (9201, 'Customer_Account1', 'Bauer', 'user', '123456', 9101, 9002, '$2a$15$4wg8OtXU81nMkKTI7Pe39ejutAK.1.yWky3RcB1TG4cgslmkEVxAm', '1996-9-09');
insert into customer (id, forename, surname, username, tax_number, address_id, attendant, password, birth_date) values (9202, 'Customer_Account2', 'Bauer', 'user2', '223456', 9101, 9002, '$2a$15$4wg8OtXU81nMkKTI7Pe39ejutAK.1.yWky3RcB1TG4cgslmkEVxAm', '1996-9-09');
insert into customer (id, forename, surname, username, tax_number, address_id, attendant, password, birth_date) values (9203, 'Customer_Account3', 'Bauer', 'user3', '323456', 9101, 9002, '$2a$15$4wg8OtXU81nMkKTI7Pe39ejutAK.1.yWky3RcB1TG4cgslmkEVxAm', '1996-9-09');
insert into customer (id, forename, surname, username, tax_number, address_id, attendant, password, birth_date) values (9204, 'Customer_Account4', 'Bauer', 'user4', '423456', 9101, 9002, '$2a$15$4wg8OtXU81nMkKTI7Pe39ejutAK.1.yWky3RcB1TG4cgslmkEVxAm', '1996-9-09');
insert into customer (id, forename, surname, username, tax_number, address_id, attendant, password, birth_date) values (9205, 'Customer_Account5', 'Bauer', 'user5', '523456', 9101, 9002, '$2a$15$4wg8OtXU81nMkKTI7Pe39ejutAK.1.yWky3RcB1TG4cgslmkEVxAm', '1996-9-09');
insert into customer (id, forename, surname, username, tax_number, address_id, attendant, password, birth_date) values (9206, 'Customer_Account6', 'Bauer', 'user6', '623456', 9101, 9002, '$2a$15$4wg8OtXU81nMkKTI7Pe39ejutAK.1.yWky3RcB1TG4cgslmkEVxAm', '1996-9-09');
-- Privilege
insert into privilege (id, privilege_name) value (9301, 'ROLE_ADMIN');
insert into privilege (id, privilege_name) value (9302, 'ROLE_EMPLOYEE');
insert into privilege (id, privilege_name) value (9303, 'ROLE_CUSTOMER');
-- Person Authority: Admin & Employee
insert into person_authority (id, person_id, privilege_id) values (9401, 9001, 9301);
insert into person_authority (id, person_id, privilege_id) values (9402, 9001, 9302);
insert into person_authority (id, person_id, privilege_id) values (9403, 9002, 9302);
insert into person_authority (id, person_id, privilege_id) values (9404, 9003, 9302);
-- Person Authority: Customer
insert into person_authority (id, person_id, privilege_id) values (9411, 9201, 9303);
insert into person_authority (id, person_id, privilege_id) values (9412, 9202, 9303);
insert into person_authority (id, person_id, privilege_id) values (9413, 9203, 9303);
insert into person_authority (id, person_id, privilege_id) values (9414, 9204, 9303);
insert into person_authority (id, person_id, privilege_id) values (9415, 9205, 9303);
insert into person_authority (id, person_id, privilege_id) values (9416, 9206, 9303);