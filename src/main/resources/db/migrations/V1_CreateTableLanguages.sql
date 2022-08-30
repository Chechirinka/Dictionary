CREATE TABLE IF NOT EXISTS public.languages
(
    id bigint NOT NULL DEFAULT nextval('languages_ig_seq'::regclass),
    name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    pattern character varying(30) COLLATE pg_catalog."default",
    CONSTRAINT languages_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.languages
    OWNER to postgres;