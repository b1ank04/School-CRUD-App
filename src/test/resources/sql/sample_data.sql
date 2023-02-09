INSERT INTO groups (id, name)
values (1000, 'TEST');
INSERT INTO groups (id, name)
values (500, 'TEST1');

INSERT INTO students (id, group_id, first_name, last_name)
values (1000, 1000, 'max', 'payne');

INSERT INTO students (id, group_id, first_name, last_name)
values (1001, 1000, 'andrey', 'pavlov');

INSERT INTO courses (id, name, description) values (1000, 'test', null);
INSERT INTO courses (id, name, description) values (1001, 'test1', null);
INSERT INTO courses (id, name, description) VALUES (500, 'test2', null);

INSERT INTO student_course (student_id, course_id) VALUES (1000, 1000);
INSERT INTO student_course (student_id, course_id) VALUES (1000, 1001);