CREATE TABLE IF NOT EXISTS public.words
(
    id bigint NOT NULL DEFAULT nextval('words_id_seq'::regclass),
    word character varying(39) COLLATE pg_catalog."default" NOT NULL,
    lan_id bigint NOT NULL,
    CONSTRAINT words_pkey PRIMARY KEY (id),
    CONSTRAINT lan_id FOREIGN KEY (lan_id)
    REFERENCES public.languages (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.words
    OWNER to postgres;