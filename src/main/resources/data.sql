INSERT INTO program
    (program_name)
VALUES ('Computer Programmer'),
       ('Systems Technology'),
       ('Engineering Technician');

INSERT INTO student
(first_name, last_name, program_id, international, program_year, program_coop)
VALUES ('Harry', 'Potter', 1, false, 1, true),
       ('Ronald', 'Weasley', 2, true, 2, false),
       ('Hermione', 'Granger', 2, false, 1, false),
       ('Draco', 'Malfoy', 3, true, 2, true),
       ('George', 'Weasley', 2, true, 2, false),
       ('Fred', 'Weasley', 2, false, 1, false),
       ('Ginny', 'Weasley', 3, true, 2, true),
       ('Neville', 'Longbottom', 1, false, 1, false),
       ('Vincent', 'Crabbe', 1, true, 1, true),
       ('Gregory', 'Goyle', 3, false, 1, false),
       ('Pansy','Parkinson', 1, true, 2, true),
       ('Blaise', 'Zabini', 2, false, 1, false),
       ('Luna', 'Lovegood', 3, true, 2, true),
       ('Cho', 'Chang', 2, true, 2, false),
       ('Padma', 'Patil', 3, false, 1, false),
       ('Terry', 'Boot', 2, false, 2, false),
       ('Cedric', 'Diggory', 1, false, 2, true),
       ('Hannah', 'Abbott', 2, true, 2, false),
       ('Ernie', 'Macmillan', 3, false, 1, false),
       ('Justin', 'Finch-Fletchley', 2, true, 2, true);

/* all these passwords are "sesame" */
INSERT INTO app_user
    (username, password_hash, first_name, last_name)
VALUES ('marge', '$2a$10$bxGtVIu12/dXFQ8I1VrCmeFap8AXK.8EFgp.NRgaGt5no27uZd8Ty', 'Marge', 'Simpson'),
       ('homer', '$2a$10$5y39gonhJWNtUXFHi3gLaumMYLKmK/O4Jshi4/IlhryYNxhEFSNuy', 'Homer', 'Simpson'),
       ('bart', '$2a$10$WFceIBbBe2ynUC6ckJltOeI9qNgKSqGzE/PqD2BbxBHSVZyscOF8O', 'Bart', 'Simpson'),
       ('lisa', '$2a$10$/0le0donOsBt.kSva6CNNeNXRjm83m.VQeEsWHyY9ORQwJeGN/DAa', 'Lisa', 'Simpson');

INSERT INTO app_role
    (role_name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO app_user_role
    (user_id, role_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 2);

