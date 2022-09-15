
CREATE TABLE IF NOT EXISTS public.languages

(

    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    pattern character varying(30) COLLATE pg_catalog."default",
    CONSTRAINT languages_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.languages
    OWNER to postgres;