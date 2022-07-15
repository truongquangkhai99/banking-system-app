--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;


--
-- Databases
--

--
-- Database "template1" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4
-- Dumped by pg_dump version 14.4

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

UPDATE pg_catalog.pg_database SET datistemplate = false WHERE datname = 'template1';
DROP DATABASE template1;
--
-- Name: template1; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE template1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE template1 OWNER TO postgres;

\connect template1

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
-- Name: DATABASE template1; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- Name: template1; Type: DATABASE PROPERTIES; Schema: -; Owner: postgres
--

ALTER DATABASE template1 IS_TEMPLATE = true;


\connect template1

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
-- Name: DATABASE template1; Type: ACL; Schema: -; Owner: postgres
--

REVOKE CONNECT,TEMPORARY ON DATABASE template1 FROM PUBLIC;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;


--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4
-- Dumped by pg_dump version 14.4

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

DROP DATABASE postgres;
--
-- Name: postgres; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

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
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- Name: acc_sec_details_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.acc_sec_details_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.acc_sec_details_seq_id OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account (
    id bigint NOT NULL,
    balance numeric(18,4) NOT NULL,
    currency character varying(255) NOT NULL,
    account_history_id bigint NOT NULL,
    account_security_details_id bigint NOT NULL
);


ALTER TABLE public.account OWNER TO postgres;

--
-- Name: account_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account_history (
    id bigint NOT NULL,
    deposit_id bigint
);


ALTER TABLE public.account_history OWNER TO postgres;

--
-- Name: account_history_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.account_history_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_history_seq_id OWNER TO postgres;

--
-- Name: account_security_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account_security_details (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    password_hash character varying(255) NOT NULL
);


ALTER TABLE public.account_security_details OWNER TO postgres;

--
-- Name: account_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.account_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_seq_id OWNER TO postgres;

--
-- Name: credit; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credit (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    currency integer NOT NULL,
    current numeric(18,4) NOT NULL,
    date_between_payments_in_days integer NOT NULL,
    is_closed boolean,
    remain numeric(18,4) NOT NULL,
    total numeric(18,4) NOT NULL,
    account_history_id bigint NOT NULL
);


ALTER TABLE public.credit OWNER TO postgres;

--
-- Name: credit_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.credit_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.credit_seq_id OWNER TO postgres;

--
-- Name: deposit; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.deposit (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    balance numeric(18,4) NOT NULL,
    currency character varying(3) NOT NULL,
    intense_rate numeric(18,6) NOT NULL
);


ALTER TABLE public.deposit OWNER TO postgres;

--
-- Name: deposit_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.deposit_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.deposit_seq_id OWNER TO postgres;

--
-- Name: exc_rate_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.exc_rate_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.exc_rate_seq_id OWNER TO postgres;

--
-- Name: exchange_rate; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.exchange_rate (
    id bigint NOT NULL,
    from_currency character varying(3) NOT NULL,
    ratio numeric(18,4) NOT NULL,
    to_currency character varying(3) NOT NULL
);


ALTER TABLE public.exchange_rate OWNER TO postgres;

--
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    date_of_birth date NOT NULL,
    email character varying(100) NOT NULL,
    first_name character varying(70) NOT NULL,
    last_name character varying(70) NOT NULL,
    password character varying(100) NOT NULL,
    phone character varying(30) NOT NULL,
    status boolean NOT NULL,
    account_id bigint NOT NULL
);


ALTER TABLE public.person OWNER TO postgres;

--
-- Name: person_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person_role (
    person_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.person_role OWNER TO postgres;

--
-- Name: person_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_seq_id OWNER TO postgres;

--
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    id bigint NOT NULL,
    role character varying(255) NOT NULL
);


ALTER TABLE public.role OWNER TO postgres;

--
-- Name: role_seq_gen; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.role_seq_gen
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.role_seq_gen OWNER TO postgres;

--
-- Name: transaction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transaction (
    id bigint NOT NULL,
    data date NOT NULL,
    account_history_id bigint,
    transaction_details_id bigint NOT NULL
);


ALTER TABLE public.transaction OWNER TO postgres;

--
-- Name: transaction_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transaction_details (
    id bigint NOT NULL,
    amount numeric(18,4) NOT NULL,
    transaction_type character varying(255) NOT NULL,
    credit_id bigint,
    from_account_id bigint,
    to_account_id bigint,
    currency character varying(3)
);


ALTER TABLE public.transaction_details OWNER TO postgres;

--
-- Name: transaction_details_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transaction_details_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transaction_details_seq_id OWNER TO postgres;

--
-- Name: transaction_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transaction_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transaction_seq_id OWNER TO postgres;

--
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.account (id, balance, currency, account_history_id, account_security_details_id) FROM stdin;
1	0.0000	EUR	1	1
\.


--
-- Data for Name: account_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.account_history (id, deposit_id) FROM stdin;
1	\N
\.


--
-- Data for Name: account_security_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.account_security_details (id, created_at, updated_at, password_hash) FROM stdin;
1	2022-07-04 00:34:03.565	2022-07-04 00:34:03.565	$2a$08$cybSjI6wJMoM1SIqCg6yAOXy0kv5CHTFKsubpMaKPdW3fKN/Ol7Hu
\.


--
-- Data for Name: credit; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.credit (id, created_at, updated_at, currency, current, date_between_payments_in_days, is_closed, remain, total, account_history_id) FROM stdin;
\.


--
-- Data for Name: deposit; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.deposit (id, created_at, updated_at, balance, currency, intense_rate) FROM stdin;
\.


--
-- Data for Name: exchange_rate; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.exchange_rate (id, from_currency, ratio, to_currency) FROM stdin;
1	RUB	0.0160	USD
2	RUB	0.0170	EUR
3	EUR	1.0110	USD
4	EUR	58.8400	RUB
5	USD	0.9890	EUR
6	USD	58.2300	RUB
\.


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.person (id, created_at, updated_at, date_of_birth, email, first_name, last_name, password, phone, status, account_id) FROM stdin;
1	2022-07-04 00:34:03.492	2022-07-04 00:34:03.492	2022-07-03	admin@admin.com	admin	admin	$2a$08$W5SHGtJ8/XtZ7Lyk5QnKhe8vKTX4IDsrVLYkmXi.eRRUS969yuX5u	+9 (999) 999-99-99	t	1
\.


--
-- Data for Name: person_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.person_role (person_id, role_id) FROM stdin;
1	2
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.role (id, role) FROM stdin;
1	USER
2	ADMIN
\.


--
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transaction (id, data, account_history_id, transaction_details_id) FROM stdin;
\.


--
-- Data for Name: transaction_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transaction_details (id, amount, transaction_type, credit_id, from_account_id, to_account_id, currency) FROM stdin;
\.


--
-- Name: acc_sec_details_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.acc_sec_details_seq_id', 1, true);


--
-- Name: account_history_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.account_history_seq_id', 1, true);


--
-- Name: account_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.account_seq_id', 1, true);


--
-- Name: credit_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.credit_seq_id', 1, false);


--
-- Name: deposit_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.deposit_seq_id', 1, false);


--
-- Name: exc_rate_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.exc_rate_seq_id', 6, true);


--
-- Name: person_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_seq_id', 1, true);


--
-- Name: role_seq_gen; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.role_seq_gen', 2, true);


--
-- Name: transaction_details_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transaction_details_seq_id', 1, false);


--
-- Name: transaction_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transaction_seq_id', 1, false);


--
-- Name: account_history account_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account_history
    ADD CONSTRAINT account_history_pkey PRIMARY KEY (id);


--
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (id);


--
-- Name: account_security_details account_security_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account_security_details
    ADD CONSTRAINT account_security_details_pkey PRIMARY KEY (id);


--
-- Name: credit credit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit
    ADD CONSTRAINT credit_pkey PRIMARY KEY (id);


--
-- Name: exchange_rate currencies; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exchange_rate
    ADD CONSTRAINT currencies UNIQUE (from_currency, to_currency);


--
-- Name: deposit deposit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deposit
    ADD CONSTRAINT deposit_pkey PRIMARY KEY (id);


--
-- Name: person email_uk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT email_uk UNIQUE (email);


--
-- Name: exchange_rate exchange_rate_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exchange_rate
    ADD CONSTRAINT exchange_rate_pkey PRIMARY KEY (id);


--
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- Name: person_role person_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_role
    ADD CONSTRAINT person_role_pkey PRIMARY KEY (person_id, role_id);


--
-- Name: person phone_uk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT phone_uk UNIQUE (phone);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: role role_uk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_uk UNIQUE (role);


--
-- Name: transaction_details transaction_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction_details
    ADD CONSTRAINT transaction_details_pkey PRIMARY KEY (id);


--
-- Name: transaction transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (id);


--
-- Name: account account_account_history_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_account_history_fk FOREIGN KEY (account_history_id) REFERENCES public.account_history(id);


--
-- Name: account account_account_security_details_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_account_security_details_fk FOREIGN KEY (account_security_details_id) REFERENCES public.account_security_details(id);


--
-- Name: account_history account_history_deposit_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account_history
    ADD CONSTRAINT account_history_deposit_fk FOREIGN KEY (deposit_id) REFERENCES public.deposit(id);


--
-- Name: credit credit_account_history_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit
    ADD CONSTRAINT credit_account_history_fk FOREIGN KEY (account_history_id) REFERENCES public.account_history(id);


--
-- Name: transaction_details fk7xw1deu1s389bd13c6wkcgcys; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction_details
    ADD CONSTRAINT fk7xw1deu1s389bd13c6wkcgcys FOREIGN KEY (credit_id) REFERENCES public.credit(id);


--
-- Name: person_role fkhyx1efsls0f00lxs6xd4w2b3j; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_role
    ADD CONSTRAINT fkhyx1efsls0f00lxs6xd4w2b3j FOREIGN KEY (person_id) REFERENCES public.person(id);


--
-- Name: person_role fks7asxi8amiwjjq1sonlc4rihn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person_role
    ADD CONSTRAINT fks7asxi8amiwjjq1sonlc4rihn FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- Name: person person_account_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_account_fk FOREIGN KEY (account_id) REFERENCES public.account(id);


--
-- Name: transaction transaction_account_history_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_account_history_fk FOREIGN KEY (account_history_id) REFERENCES public.account_history(id);


--
-- Name: transaction_details transaction_from_account_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction_details
    ADD CONSTRAINT transaction_from_account_fk FOREIGN KEY (from_account_id) REFERENCES public.account(id);


--
-- Name: transaction_details transaction_to_account_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction_details
    ADD CONSTRAINT transaction_to_account_fk FOREIGN KEY (to_account_id) REFERENCES public.account(id);


--
-- Name: transaction transaction_transaction_details_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_transaction_details_fk FOREIGN KEY (transaction_details_id) REFERENCES public.transaction_details(id);


--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

