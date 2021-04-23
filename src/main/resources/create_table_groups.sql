-- Table: public.groups

-- DROP TABLE public.groups;

CREATE TABLE public.groups
(
    group_id bigint NOT NULL DEFAULT nextval('groups_group_id_seq'::regclass),
    group_name character varying(10) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
)

TABLESPACE pg_default;

ALTER TABLE public.groups
    OWNER to karyama;