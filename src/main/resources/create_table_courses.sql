CREATE TABLE public.courses
(
    course_id SERIAL NOT NULL,
    course_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    course_description character varying COLLATE pg_catalog."default",
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
)

TABLESPACE pg_default;

ALTER TABLE public.courses
    OWNER to karyama;