--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: gymcrm_shema; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA gymcrm_shema;


ALTER SCHEMA gymcrm_shema OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: trainee; Type: TABLE; Schema: gymcrm_shema; Owner: postgres
--

CREATE TABLE gymcrm_shema.trainee (
    date_of_birth timestamp(6) without time zone,
    id bigint NOT NULL,
    user_id bigint,
    address character varying(255)
);


ALTER TABLE gymcrm_shema.trainee OWNER TO postgres;

--
-- Name: trainee_trainer; Type: TABLE; Schema: gymcrm_shema; Owner: postgres
--

CREATE TABLE gymcrm_shema.trainee_trainer (
    trainee_id bigint NOT NULL,
    trainer_id bigint NOT NULL
);


ALTER TABLE gymcrm_shema.trainee_trainer OWNER TO postgres;

--
-- Name: trainer; Type: TABLE; Schema: gymcrm_shema; Owner: postgres
--

CREATE TABLE gymcrm_shema.trainer (
    id bigint NOT NULL,
    specialization bigint,
    user_id bigint
);


ALTER TABLE gymcrm_shema.trainer OWNER TO postgres;

--
-- Name: training; Type: TABLE; Schema: gymcrm_shema; Owner: postgres
--

CREATE TABLE gymcrm_shema.training (
    training_duration integer,
    id bigint NOT NULL,
    trainee_id bigint,
    trainer_id bigint,
    training_date timestamp(6) without time zone,
    training_type_id bigint,
    training_name character varying(255)
);


ALTER TABLE gymcrm_shema.training OWNER TO postgres;

--
-- Name: training_type; Type: TABLE; Schema: gymcrm_shema; Owner: postgres
--

CREATE TABLE gymcrm_shema.training_type (
    id bigint NOT NULL,
    training_type_name character varying(255)
);


ALTER TABLE gymcrm_shema.training_type OWNER TO postgres;

--
-- Name: user; Type: TABLE; Schema: gymcrm_shema; Owner: postgres
--

CREATE TABLE gymcrm_shema."user" (
    is_active boolean,
    id bigint NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255),
    username character varying(255)
);


ALTER TABLE gymcrm_shema."user" OWNER TO postgres;

--
-- Name: trainee_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.trainee_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.trainee_seq OWNER TO postgres;

--
-- Name: trainer_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.trainer_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.trainer_seq OWNER TO postgres;

--
-- Name: training_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.training_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.training_seq OWNER TO postgres;

--
-- Name: training_type_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.training_type_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.training_type_seq OWNER TO postgres;

--
-- Name: user_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_seq OWNER TO postgres;

--
-- Data for Name: trainee; Type: TABLE DATA; Schema: gymcrm_shema; Owner: postgres
--

COPY gymcrm_shema.trainee (date_of_birth, id, user_id, address) FROM stdin;
\N	1	1	\N
\N	2	2	\N
\N	5	6	\N
\.


--
-- Data for Name: trainee_trainer; Type: TABLE DATA; Schema: gymcrm_shema; Owner: postgres
--

COPY gymcrm_shema.trainee_trainer (trainee_id, trainer_id) FROM stdin;
\.


--
-- Data for Name: trainer; Type: TABLE DATA; Schema: gymcrm_shema; Owner: postgres
--

COPY gymcrm_shema.trainer (id, specialization, user_id) FROM stdin;
1	2	4
\.


--
-- Data for Name: training; Type: TABLE DATA; Schema: gymcrm_shema; Owner: postgres
--

COPY gymcrm_shema.training (training_duration, id, trainee_id, trainer_id, training_date, training_type_id, training_name) FROM stdin;
\.


--
-- Data for Name: training_type; Type: TABLE DATA; Schema: gymcrm_shema; Owner: postgres
--

COPY gymcrm_shema.training_type (id, training_type_name) FROM stdin;
1	boxing
2	tennis
3	karate
4	swimming
5	basketball
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: gymcrm_shema; Owner: postgres
--

COPY gymcrm_shema."user" (is_active, id, first_name, last_name, password, username) FROM stdin;
t	1	J	Douglas	$2a$10$6EM6bl30Cn.yNjEmmWzPhOb4Np5ErVfpWR5HoEOP1XJGY3ZgSHvhu	J.Douglas
t	2	J	Douglas	$2a$10$Lihz2kik.Wi6wNXYKxn.puxjJ4VlYvpK1mc9jO7DQUU7V9OdDL/7m	J.Douglas1
t	4	John	Doe	$2a$10$rFqV7pXv7UUZweMdB4yFCe0LfucAl.9b8t1HmrFh9I89MkeL1OFSe	John.Doe
t	6	J	Douglas	$2a$10$fl9laqAzPvrNvuDQ62yYsuVr4UgTzVUWeuWQgXpoViuuO4Kp84LtK	J.Douglas3
\.


--
-- Name: trainee_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.trainee_seq', 8, true);


--
-- Name: trainer_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.trainer_seq', 1, true);


--
-- Name: training_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.training_seq', 1, true);


--
-- Name: training_type_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.training_type_seq', 1, false);


--
-- Name: user_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_seq', 9, true);


--
-- Name: trainee trainee_pkey; Type: CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.trainee
    ADD CONSTRAINT trainee_pkey PRIMARY KEY (id);


--
-- Name: trainee trainee_user_id_key; Type: CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.trainee
    ADD CONSTRAINT trainee_user_id_key UNIQUE (user_id);


--
-- Name: trainer trainer_pkey; Type: CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.trainer
    ADD CONSTRAINT trainer_pkey PRIMARY KEY (id);


--
-- Name: trainer trainer_user_id_key; Type: CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.trainer
    ADD CONSTRAINT trainer_user_id_key UNIQUE (user_id);


--
-- Name: training training_pkey; Type: CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.training
    ADD CONSTRAINT training_pkey PRIMARY KEY (id);


--
-- Name: training_type training_type_pkey; Type: CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.training_type
    ADD CONSTRAINT training_type_pkey PRIMARY KEY (id);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: trainee_trainer fk2ga1l3899l1l9cvggypwy8nx1; Type: FK CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.trainee_trainer
    ADD CONSTRAINT fk2ga1l3899l1l9cvggypwy8nx1 FOREIGN KEY (trainer_id) REFERENCES gymcrm_shema.trainer(id);


--
-- Name: trainee fk79ckx21bd3fwl19nf0dr8wkea; Type: FK CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.trainee
    ADD CONSTRAINT fk79ckx21bd3fwl19nf0dr8wkea FOREIGN KEY (user_id) REFERENCES gymcrm_shema."user"(id);


--
-- Name: training fk7r3b25ygw5bdjamojskmpk0b9; Type: FK CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.training
    ADD CONSTRAINT fk7r3b25ygw5bdjamojskmpk0b9 FOREIGN KEY (trainer_id) REFERENCES gymcrm_shema.trainer(id);


--
-- Name: trainer fkbfajghb0yafuemho3rlbsa68h; Type: FK CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.trainer
    ADD CONSTRAINT fkbfajghb0yafuemho3rlbsa68h FOREIGN KEY (user_id) REFERENCES gymcrm_shema."user"(id);


--
-- Name: trainee_trainer fkdylvwu2o2shyw0derk7laallo; Type: FK CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.trainee_trainer
    ADD CONSTRAINT fkdylvwu2o2shyw0derk7laallo FOREIGN KEY (trainee_id) REFERENCES gymcrm_shema.trainee(id);


--
-- Name: training fki2dctw34e0xl50d8tsnrre7te; Type: FK CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.training
    ADD CONSTRAINT fki2dctw34e0xl50d8tsnrre7te FOREIGN KEY (trainee_id) REFERENCES gymcrm_shema.trainee(id);


--
-- Name: trainer fklmfts5yeramsr8tlyf6n4xp1s; Type: FK CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.trainer
    ADD CONSTRAINT fklmfts5yeramsr8tlyf6n4xp1s FOREIGN KEY (specialization) REFERENCES gymcrm_shema.training_type(id);


--
-- Name: training fkosdbocw0x9ygfmna67s7vtewh; Type: FK CONSTRAINT; Schema: gymcrm_shema; Owner: postgres
--

ALTER TABLE ONLY gymcrm_shema.training
    ADD CONSTRAINT fkosdbocw0x9ygfmna67s7vtewh FOREIGN KEY (training_type_id) REFERENCES gymcrm_shema.training_type(id);


--
-- PostgreSQL database dump complete
--

