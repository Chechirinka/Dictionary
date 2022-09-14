CREATE TABLE IF NOT EXISTS public.words
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
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