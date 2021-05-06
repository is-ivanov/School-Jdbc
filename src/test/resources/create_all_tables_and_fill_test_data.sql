CREATE TABLE groups
(
    group_id SERIAL NOT NULL,
    group_name character varying(10) NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
);

CREATE TABLE students
(
    student_id SERIAL NOT NULL,
    group_id integer,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id),
    CONSTRAINT group_id FOREIGN KEY (group_id)
        REFERENCES public.groups (group_id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
	
CREATE TABLE courses
(
    course_id SERIAL NOT NULL,
    course_name character varying(50) NOT NULL,
    course_description character varying,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
);

CREATE TABLE students_courses
(
    student_id integer NOT NULL,
    course_id integer NOT NULL,
    CONSTRAINT students_courses_pkey PRIMARY KEY (student_id, course_id),
    CONSTRAINT course_id FOREIGN KEY (course_id) REFERENCES public.courses (course_id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
    CONSTRAINT student_id FOREIGN KEY (student_id) REFERENCES public.students (student_id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE
);

insert into groups (group_name) values ('OR-41');
insert into groups (group_name) values ('GM-87');
insert into groups (group_name) values ('XI-12');
insert into groups (group_name) values ('WC-93');
insert into groups (group_name) values ('BN-14');

insert into students (group_id, first_name, last_name) values (1, 'Wilmette', 'Sambles');
insert into students (group_id, first_name, last_name) values (5, 'Cobb', 'Muzzini');
insert into students (group_id, first_name, last_name) values (3, 'Kalinda', 'Reicharz');
insert into students (group_id, first_name, last_name) values (4, 'Rosemonde', 'Malecky');
insert into students (group_id, first_name, last_name) values (5, 'Jourdain', 'Mouser');
insert into students (group_id, first_name, last_name) values (2, 'Valencia', 'Templeton');
insert into students (group_id, first_name, last_name) values (4, 'Natala', 'Wederell');
insert into students (group_id, first_name, last_name) values (2, 'Rafaelia', 'Durtnell');
insert into students (group_id, first_name, last_name) values (2, 'Alaster', 'Hadwin');
insert into students (group_id, first_name, last_name) values (1, 'Mayor', 'Anespie');

insert into courses (course_name, course_description) values ('math', 'course of Mathematics');
insert into courses (course_name, course_description) values ('biology', 'course of Biology');
insert into courses (course_name, course_description) values ('chemistry', 'course of Chemistry');
insert into courses (course_name, course_description) values ('physics', 'course of Physics');
insert into courses (course_name, course_description) values ('English', 'course of English');

insert into students_courses (student_id, course_id) values (1, 1);
insert into students_courses (student_id, course_id) values (1, 2);
insert into students_courses (student_id, course_id) values (1, 3);
insert into students_courses (student_id, course_id) values (2, 2);
insert into students_courses (student_id, course_id) values (3, 5);
insert into students_courses (student_id, course_id) values (3, 4);
insert into students_courses (student_id, course_id) values (4, 1);
insert into students_courses (student_id, course_id) values (5, 2);
insert into students_courses (student_id, course_id) values (5, 5);
insert into students_courses (student_id, course_id) values (6, 4);
insert into students_courses (student_id, course_id) values (8, 1);
insert into students_courses (student_id, course_id) values (9, 1);
insert into students_courses (student_id, course_id) values (10, 2);