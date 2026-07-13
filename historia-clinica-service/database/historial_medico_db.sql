--
-- PostgreSQL database dump
--

\restrict fmR2aXgCDfSRcTceiLb5eX2WeePZW5w9nTrkb4lgtzIoUdmeVjAogLfVldbrwLB

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

-- Started on 2026-07-13 02:59:11 -05

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 17085)
-- Name: medical_history_event; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medical_history_event (
    id uuid NOT NULL,
    patient_id uuid NOT NULL,
    event_type character varying(30) NOT NULL,
    occurred_at timestamp without time zone NOT NULL,
    source_service character varying(50) NOT NULL,
    reference_id uuid,
    projection jsonb NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.medical_history_event OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17068)
-- Name: patient_summary; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.patient_summary (
    patient_id uuid NOT NULL,
    full_name character varying(200) NOT NULL,
    dni character varying(20) NOT NULL,
    age smallint,
    sex character varying(20) NOT NULL,
    systolic_pressure smallint,
    diastolic_pressure smallint,
    spo2 smallint,
    weight numeric(5,2),
    height numeric(4,2),
    allergies jsonb DEFAULT '[]'::jsonb NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.patient_summary OWNER TO postgres;

--
-- TOC entry 4493 (class 0 OID 17085)
-- Dependencies: 220
-- Data for Name: medical_history_event; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.medical_history_event (id, patient_id, event_type, occurred_at, source_service, reference_id, projection, created_at) FROM stdin;
\.


--
-- TOC entry 4492 (class 0 OID 17068)
-- Dependencies: 219
-- Data for Name: patient_summary; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.patient_summary (patient_id, full_name, dni, age, sex, systolic_pressure, diastolic_pressure, spo2, weight, height, allergies, updated_at) FROM stdin;
\.


--
-- TOC entry 4344 (class 2606 OID 17099)
-- Name: medical_history_event medical_history_event_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medical_history_event
    ADD CONSTRAINT medical_history_event_pkey PRIMARY KEY (id);


--
-- TOC entry 4336 (class 2606 OID 17084)
-- Name: patient_summary patient_summary_dni_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient_summary
    ADD CONSTRAINT patient_summary_dni_key UNIQUE (dni);


--
-- TOC entry 4338 (class 2606 OID 17082)
-- Name: patient_summary patient_summary_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient_summary
    ADD CONSTRAINT patient_summary_pkey PRIMARY KEY (patient_id);


--
-- TOC entry 4339 (class 1259 OID 17100)
-- Name: idx_history_patient; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_history_patient ON public.medical_history_event USING btree (patient_id);


--
-- TOC entry 4340 (class 1259 OID 17101)
-- Name: idx_history_patient_date; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_history_patient_date ON public.medical_history_event USING btree (patient_id, occurred_at DESC);


--
-- TOC entry 4341 (class 1259 OID 17103)
-- Name: idx_history_projection; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_history_projection ON public.medical_history_event USING gin (projection);


--
-- TOC entry 4342 (class 1259 OID 17370)
-- Name: idx_history_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_history_type ON public.medical_history_event USING btree (event_type);


--
-- TOC entry 4334 (class 1259 OID 17104)
-- Name: idx_patient_allergies; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_patient_allergies ON public.patient_summary USING gin (allergies);


-- Completed on 2026-07-13 02:59:15 -05

--
-- PostgreSQL database dump complete
--

\unrestrict fmR2aXgCDfSRcTceiLb5eX2WeePZW5w9nTrkb4lgtzIoUdmeVjAogLfVldbrwLB

