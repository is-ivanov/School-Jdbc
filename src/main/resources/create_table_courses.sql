-- Table: public.courses

-- DROP TABLE public.courses;

CREATE TABLE public.courses
(
    course_id integer NOT NULL DEFAULT nextval('courses_course_id_seq'::regclass),
    course_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    course_description character varying COLLATE pg_catalog."default",
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
)

TABLESPACE pg_default;

ALTER TABLE public.courses
    OWNER to karyama;