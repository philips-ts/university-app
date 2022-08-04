INSERT INTO groups(name)
VALUES ('651001');
INSERT INTO groups(name)
VALUES ('651002');
INSERT INTO groups(name)
VALUES ('651003');
INSERT INTO groups(name)
VALUES ('651004');


INSERT INTO students(first_name, last_name, group_id)
VALUES ('Ivan', 'Ivanov', (SELECT id from groups WHERE id = 1));

INSERT INTO students(first_name, last_name, group_id)
VALUES ('Dmitry', 'Sodorov', (SELECT id from groups WHERE id = 1));

INSERT INTO students(first_name, last_name, group_id)
VALUES ('Anna', 'Vividov', (SELECT id from groups WHERE id = 1));

INSERT INTO students(first_name, last_name, group_id)
VALUES ('Alexander', 'Divanov', (SELECT id from groups WHERE id = 2));

INSERT INTO students(first_name, last_name, group_id)
VALUES ('Sofia', 'Testanov', (SELECT id from groups WHERE id = 2));

INSERT INTO students(first_name, last_name, group_id)
VALUES ('Maksim', 'Vidirov', (SELECT id from groups WHERE id = 2));

INSERT INTO students(first_name, last_name, group_id)
VALUES ('Maria', 'Todorov', (SELECT id from groups WHERE id = 2));

INSERT INTO students(first_name, last_name, group_id)
VALUES ('Anastasia', 'Klassikov', (SELECT id from groups WHERE id = 2));

INSERT INTO students(first_name, last_name, group_id)
VALUES ('Aleksey', 'Sadavov', (SELECT id from groups WHERE id = 3));

INSERT INTO students(first_name, last_name, group_id)
VALUES ('Olya', 'Madanov', (SELECT id from groups WHERE id = 3));


INSERT INTO subjects(name)
VALUES ('Math');
INSERT INTO subjects(name)
VALUES ('Physics');
INSERT INTO subjects(name)
VALUES ('Economics');
INSERT INTO subjects(name)
VALUES ('Geology');


INSERT INTO lessons_info
VALUES (1, '08:00', '09:45');
INSERT INTO lessons_info
VALUES (2, '10:00', '11:45');
INSERT INTO lessons_info
VALUES (3, '12:00', '13:45');
INSERT INTO lessons_info
VALUES (4, '14:00', '15:45');
INSERT INTO lessons_info
VALUES (5, '16:00', '17:45');
INSERT INTO lessons_info
VALUES (6, '18:00', '19:45');
INSERT INTO lessons_info
VALUES (7, '20:00', '21:45');



INSERT INTO timetable(subject_id, class_date, lesson_number, class_room, group_id)
VALUES ((SELECT id from subjects WHERE name = 'Math'),
        '2022-09-01',
        1,
        201,
        (SELECT id from groups WHERE name = '651001'));


INSERT INTO timetable(subject_id, class_date, lesson_number, class_room, group_id)
VALUES ((SELECT id from subjects WHERE name = 'Physics'),
        '2022-09-01',
        2,
        103,
        (SELECT id from groups WHERE name = '651001'));

INSERT INTO timetable(subject_id, class_date, lesson_number, class_room, group_id)
VALUES ((SELECT id from subjects WHERE name = 'Geology'),
        '2022-09-01',
        3,
        101,
        (SELECT id from groups WHERE name = '651001'));


INSERT INTO timetable(subject_id, class_date, lesson_number, class_room, group_id)
VALUES ((SELECT id from subjects WHERE name = 'Math'),
        '2022-09-02',
        2,
        404,
        (SELECT id from groups WHERE name = '651002'));

INSERT INTO timetable(subject_id, class_date, lesson_number, class_room, group_id)
VALUES ((SELECT id from subjects WHERE name = 'Physics'),
        '2022-09-02',
        3,
        303,
        (SELECT id from groups WHERE name = '651002'));

INSERT INTO timetable(subject_id, class_date, lesson_number, class_room, group_id)
VALUES ((SELECT id from subjects WHERE name = 'Geology'),
        '2022-09-02',
        4,
        202,
        (SELECT id from groups WHERE name = '651002'));

INSERT INTO timetable(subject_id, class_date, lesson_number, class_room, group_id)
VALUES ((SELECT id from subjects WHERE name = 'Economics'),
        '2022-09-03',
        1,
        88,
        (SELECT id from groups WHERE name = '651003'));

INSERT INTO timetable(subject_id, class_date, lesson_number, class_room, group_id)
VALUES ((SELECT id from subjects WHERE name = 'Physics'),
        '2022-09-03',
        2,
        44,
        (SELECT id from groups WHERE name = '651003'));

INSERT INTO timetable(subject_id, class_date, lesson_number, class_room, group_id)
VALUES ((SELECT id from subjects WHERE name = 'Math'),
        '2022-09-03',
        3,
        55,
        (SELECT id from groups WHERE name = '651003'));

