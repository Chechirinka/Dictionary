CREATE TABLE IF NOT EXISTS public.dictionaries
(
    id bigint NOT NULL DEFAULT nextval('dictionaries_id_seq'::regclass),
    keys bigint NOT NULL,
    "values" bigint NOT NULL,
    CONSTRAINT dictionaries_pkey PRIMARY KEY (id),
    CONSTRAINT key FOREIGN KEY (keys)
    REFERENCES public.words (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT value FOREIGN KEY ("values")
    REFERENCES public.words (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.dictionaries
    OWNER to postgres;