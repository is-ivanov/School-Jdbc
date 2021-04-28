CREATE TABLE public.groups
(
    group_id SERIAL NOT NULL,
    group_name character varying(10) NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
)

TABLESPACE pg_default;

ALTER TABLE public.groups
    OWNER to karyama;

CREATE TABLE public.students
(
    student_id SERIAL NOT NULL,
    group_id integer,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id),
    CONSTRAINT group_id FOREIGN KEY (group_id)
        REFERENCES public.groups (group_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.students
    OWNER to karyama;
	
CREATE TABLE public.courses
(
    course_id SERIAL NOT NULL,
    course_name character varying(50) NOT NULL,
    course_description character varying,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
)

TABLESPACE pg_default;

ALTER TABLE public.courses
    OWNER to karyama;