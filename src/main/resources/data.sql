INSERT INTO program
    (program_name)
VALUES ('Computer Programmer'),
       ('Systems Technology'),
       ('Engineering Technician');

INSERT INTO student
(first_name, last_name, date_of_birth, program_id, international, program_year, program_coop)
VALUES ('Harry', 'Potter', '2018-07-15',1, false, 1, true),
       ('Ronald', 'Weasley', '2017-03-10', 2, true, 2, false),
       ('Hermione', 'Granger', '2018-11-05', 2, false, 1, false),
       ('Draco', 'Malfoy', '2017-02-14', 3, true, 2, true),
       ('George', 'Weasley', '2017-04-18', 2, true, 2, false),
       ('Fred', 'Weasley', '2018-12-30',  2, false, 1, false),
       ('Ginny', 'Weasley', '2017-05-16', 3, true, 2, true),
       ('Neville', 'Longbottom', '2018-03-07', 1, false, 1, false),
       ('Vincent', 'Crabbe', '2018-08-09', 1, true, 1, true),
       ('Gregory', 'Goyle', '2018-04-19', 3, false, 1, false),
       ('Pansy','Parkinson', '2017-01-11', 1, true, 2, true),
       ('Blaise', 'Zabini', '2018-09-25', 2, false, 1, false),
       ('Luna', 'Lovegood', '2017-10-21', 3, true, 2, true),
       ('Cho', 'Chang', '2017-01-08', 2, true, 2, false),
       ('Padma', 'Patil', '2018-09-10', 3, false, 1, false),
       ('Terry', 'Boot', '2017-11-29', 2, false, 2, false),
       ('Cedric', 'Diggory', '2017-03-20', 1, false, 2, true),
       ('Hannah', 'Abbott', '2017-01-24', 2, true, 2, false),
       ('Ernie', 'Macmillan', '2018-07-12', 3, false, 1, false),
       ('Justin', 'Finch-Fletchley', '2017-10-08', 2, true, 2, true);

/* all these passwords are "sesame" */
INSERT INTO app_user
    (username, password_hash, first_name, last_name)
VALUES ('marge', '$2a$10$bxGtVIu12/dXFQ8I1VrCmeFap8AXK.8EFgp.NRgaGt5no27uZd8Ty', 'Marge', 'Simpson'),
       ('homer', '$2a$10$5y39gonhJWNtUXFHi3gLaumMYLKmK/O4Jshi4/IlhryYNxhEFSNuy', 'Homer', 'Simpson'),
       ('bart', '$2a$10$WFceIBbBe2ynUC6ckJltOeI9qNgKSqGzE/PqD2BbxBHSVZyscOF8O', 'Bart', 'Simpson'),
       ('lisa', '$2a$10$/0le0donOsBt.kSva6CNNeNXRjm83m.VQeEsWHyY9ORQwJeGN/DAa', 'Lisa', 'Simpson');

INSERT INTO app_role
    (role_name)
VALUES ('USER_ADMIN'),
       ('DATA_ADMIN'),
       ('DATA_USER');

INSERT INTO app_user_role
    (user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (3, 3),
       (4, 2);

