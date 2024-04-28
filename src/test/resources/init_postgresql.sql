CREATE SCHEMA IF NOT EXISTS public
    AUTHORIZATION pg_database_owner;

CREATE TABLE IF NOT EXISTS public."user"
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT nickname UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS public.chat
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name text COLLATE pg_catalog."default" NOT NULL,
    owner integer NOT NULL,
    CONSTRAINT chat_pkey PRIMARY KEY (id),
    CONSTRAINT owner FOREIGN KEY (owner)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

CREATE TABLE IF NOT EXISTS public.user_chat
(
    user_id integer NOT NULL,
    chat_id integer NOT NULL,
    since timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT user_chat_pkey PRIMARY KEY (user_id, chat_id),
    CONSTRAINT chat_id FOREIGN KEY (chat_id)
        REFERENCES public.chat (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT user_id FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.message
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    chat_id integer NOT NULL,
    user_id integer NOT NULL,
    text_content text COLLATE pg_catalog."default" NOT NULL,
    date_time timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT message_pkey PRIMARY KEY (id),
    CONSTRAINT chat_id FOREIGN KEY (chat_id)
        REFERENCES public.chat (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT user_id FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

INSERT INTO public."user" (name, password) VALUES ('oldUser', '0000');
INSERT INTO public."user" (name, password) VALUES ('veryOldUser', '1111');
INSERT INTO public."user" (name, password) VALUES ('anotherUser', '2222');

INSERT INTO public."chat" (name, owner) VALUES ('oldChat', 1);
INSERT INTO public."chat" (name, owner) VALUES ('veryOldChat', 2);
INSERT INTO public."chat" (name, owner) VALUES ('thirdChat', 1);

INSERT INTO public."user_chat" (user_id, chat_id) VALUES (1, 1);
INSERT INTO public."user_chat" (user_id, chat_id) VALUES (1, 2);
INSERT INTO public."user_chat" (user_id, chat_id) VALUES (2, 2);
INSERT INTO public."user_chat" (user_id, chat_id) VALUES (1, 3);
INSERT INTO public."user_chat" (user_id, chat_id) VALUES (2, 3);

INSERT INTO public."message" (chat_id, user_id, text_content) VALUES (3, 1, 'hi');
INSERT INTO public."message" (chat_id, user_id, text_content) VALUES (3, 2, 'hello');
INSERT INTO public."message" (chat_id, user_id, text_content) VALUES (3, 1, 'cool');