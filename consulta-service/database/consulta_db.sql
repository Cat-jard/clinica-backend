--
-- PostgreSQL database dump
--

\restrict IgoaDiDP2Sfeps8dOkJEIo0lQAX081AdALkEzA7judIZuGsgSIOV6PM9ooAFfy8

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

-- Started on 2026-07-13 03:00:32 -05

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
-- TOC entry 220 (class 1259 OID 17118)
-- Name: anamnesis; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.anamnesis (
    consultation_id uuid NOT NULL,
    chief_complaint text,
    present_illness text,
    personal_history text,
    surgical_history text,
    family_history text,
    lifestyle text
);


ALTER TABLE public.anamnesis OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 17172)
-- Name: clinical_plan; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clinical_plan (
    consultation_id uuid NOT NULL,
    general_indications text
);


ALTER TABLE public.clinical_plan OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17105)
-- Name: consultation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.consultation (
    id uuid NOT NULL,
    appointment_id uuid NOT NULL,
    patient_id uuid NOT NULL,
    doctor_id uuid NOT NULL,
    consultation_type character varying(30) NOT NULL,
    status character varying(30) NOT NULL,
    started_at timestamp without time zone,
    finished_at timestamp without time zone,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.consultation OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17131)
-- Name: current_medication; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.current_medication (
    id uuid NOT NULL,
    consultation_id uuid NOT NULL,
    medicine_id uuid NOT NULL,
    dose character varying(100),
    frequency character varying(100)
);


ALTER TABLE public.current_medication OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 17157)
-- Name: diagnosis; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.diagnosis (
    id uuid NOT NULL,
    consultation_id uuid NOT NULL,
    cie10_code character varying(20) NOT NULL,
    description character varying(255),
    principal boolean DEFAULT false NOT NULL
);


ALTER TABLE public.diagnosis OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 17245)
-- Name: interconsultation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.interconsultation (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    consultation_id uuid NOT NULL,
    specialty_id uuid NOT NULL,
    doctor_id uuid,
    reason text NOT NULL,
    relevant_findings text,
    clinical_question text NOT NULL,
    priority character varying(20) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.interconsultation OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 17144)
-- Name: physical_evaluation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.physical_evaluation (
    consultation_id uuid NOT NULL,
    general_exam text,
    head_neck text,
    thorax_lungs text,
    heart text,
    abdomen text,
    extremities text,
    neurological text,
    other_findings text
);


ALTER TABLE public.physical_evaluation OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 17216)
-- Name: prescribed_medication; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.prescribed_medication (
    id uuid NOT NULL,
    prescription_id uuid NOT NULL,
    medicine_id uuid NOT NULL,
    dose character varying(100),
    route character varying(100),
    frequency character varying(100),
    duration character varying(100),
    quantity character varying(100),
    special_instructions text
);


ALTER TABLE public.prescribed_medication OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 17200)
-- Name: prescription; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.prescription (
    id uuid NOT NULL,
    consultation_id uuid NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.prescription OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 17185)
-- Name: procedure_performed; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.procedure_performed (
    id uuid NOT NULL,
    consultation_id uuid NOT NULL,
    description text NOT NULL
);


ALTER TABLE public.procedure_performed OWNER TO postgres;

--
-- TOC entry 4558 (class 0 OID 17118)
-- Dependencies: 220
-- Data for Name: anamnesis; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.anamnesis (consultation_id, chief_complaint, present_illness, personal_history, surgical_history, family_history, lifestyle) FROM stdin;
\.


--
-- TOC entry 4562 (class 0 OID 17172)
-- Dependencies: 224
-- Data for Name: clinical_plan; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clinical_plan (consultation_id, general_indications) FROM stdin;
\.


--
-- TOC entry 4557 (class 0 OID 17105)
-- Dependencies: 219
-- Data for Name: consultation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.consultation (id, appointment_id, patient_id, doctor_id, consultation_type, status, started_at, finished_at, created_at) FROM stdin;
\.


--
-- TOC entry 4559 (class 0 OID 17131)
-- Dependencies: 221
-- Data for Name: current_medication; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.current_medication (id, consultation_id, medicine_id, dose, frequency) FROM stdin;
\.


--
-- TOC entry 4561 (class 0 OID 17157)
-- Dependencies: 223
-- Data for Name: diagnosis; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.diagnosis (id, consultation_id, cie10_code, description, principal) FROM stdin;
\.


--
-- TOC entry 4566 (class 0 OID 17245)
-- Dependencies: 228
-- Data for Name: interconsultation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.interconsultation (id, consultation_id, specialty_id, doctor_id, reason, relevant_findings, clinical_question, priority, created_at) FROM stdin;
\.


--
-- TOC entry 4560 (class 0 OID 17144)
-- Dependencies: 222
-- Data for Name: physical_evaluation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.physical_evaluation (consultation_id, general_exam, head_neck, thorax_lungs, heart, abdomen, extremities, neurological, other_findings) FROM stdin;
\.


--
-- TOC entry 4565 (class 0 OID 17216)
-- Dependencies: 227
-- Data for Name: prescribed_medication; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.prescribed_medication (id, prescription_id, medicine_id, dose, route, frequency, duration, quantity, special_instructions) FROM stdin;
\.


--
-- TOC entry 4564 (class 0 OID 17200)
-- Dependencies: 226
-- Data for Name: prescription; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.prescription (id, consultation_id, created_at) FROM stdin;
\.


--
-- TOC entry 4563 (class 0 OID 17185)
-- Dependencies: 225
-- Data for Name: procedure_performed; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.procedure_performed (id, consultation_id, description) FROM stdin;
\.


--
-- TOC entry 4374 (class 2606 OID 17125)
-- Name: anamnesis anamnesis_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.anamnesis
    ADD CONSTRAINT anamnesis_pkey PRIMARY KEY (consultation_id);


--
-- TOC entry 4385 (class 2606 OID 17179)
-- Name: clinical_plan clinical_plan_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clinical_plan
    ADD CONSTRAINT clinical_plan_pkey PRIMARY KEY (consultation_id);


--
-- TOC entry 4369 (class 2606 OID 17117)
-- Name: consultation consultation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consultation
    ADD CONSTRAINT consultation_pkey PRIMARY KEY (id);


--
-- TOC entry 4376 (class 2606 OID 17138)
-- Name: current_medication current_medication_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.current_medication
    ADD CONSTRAINT current_medication_pkey PRIMARY KEY (id);


--
-- TOC entry 4381 (class 2606 OID 17166)
-- Name: diagnosis diagnosis_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.diagnosis
    ADD CONSTRAINT diagnosis_pkey PRIMARY KEY (id);


--
-- TOC entry 4400 (class 2606 OID 17260)
-- Name: interconsultation interconsultation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interconsultation
    ADD CONSTRAINT interconsultation_pkey PRIMARY KEY (id);


--
-- TOC entry 4379 (class 2606 OID 17151)
-- Name: physical_evaluation physical_evaluation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.physical_evaluation
    ADD CONSTRAINT physical_evaluation_pkey PRIMARY KEY (consultation_id);


--
-- TOC entry 4395 (class 2606 OID 17225)
-- Name: prescribed_medication prescribed_medication_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescribed_medication
    ADD CONSTRAINT prescribed_medication_pkey PRIMARY KEY (id);


--
-- TOC entry 4390 (class 2606 OID 17210)
-- Name: prescription prescription_consultation_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription
    ADD CONSTRAINT prescription_consultation_id_key UNIQUE (consultation_id);


--
-- TOC entry 4392 (class 2606 OID 17208)
-- Name: prescription prescription_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription
    ADD CONSTRAINT prescription_pkey PRIMARY KEY (id);


--
-- TOC entry 4387 (class 2606 OID 17194)
-- Name: procedure_performed procedure_performed_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.procedure_performed
    ADD CONSTRAINT procedure_performed_pkey PRIMARY KEY (id);


--
-- TOC entry 4370 (class 1259 OID 17234)
-- Name: idx_consultation_appointment; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_consultation_appointment ON public.consultation USING btree (appointment_id);


--
-- TOC entry 4371 (class 1259 OID 17233)
-- Name: idx_consultation_doctor; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_consultation_doctor ON public.consultation USING btree (doctor_id);


--
-- TOC entry 4372 (class 1259 OID 17232)
-- Name: idx_consultation_patient; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_consultation_patient ON public.consultation USING btree (patient_id);


--
-- TOC entry 4377 (class 1259 OID 17236)
-- Name: idx_current_medication_consultation; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_current_medication_consultation ON public.current_medication USING btree (consultation_id);


--
-- TOC entry 4382 (class 1259 OID 17235)
-- Name: idx_diagnosis_consultation; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_diagnosis_consultation ON public.diagnosis USING btree (consultation_id);


--
-- TOC entry 4396 (class 1259 OID 17266)
-- Name: idx_interconsultation_consultation; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_interconsultation_consultation ON public.interconsultation USING btree (consultation_id);


--
-- TOC entry 4397 (class 1259 OID 17268)
-- Name: idx_interconsultation_doctor; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_interconsultation_doctor ON public.interconsultation USING btree (doctor_id);


--
-- TOC entry 4398 (class 1259 OID 17267)
-- Name: idx_interconsultation_specialty; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_interconsultation_specialty ON public.interconsultation USING btree (specialty_id);


--
-- TOC entry 4393 (class 1259 OID 17238)
-- Name: idx_prescribed_medication_prescription; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_prescribed_medication_prescription ON public.prescribed_medication USING btree (prescription_id);


--
-- TOC entry 4388 (class 1259 OID 17237)
-- Name: idx_prescription_consultation; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_prescription_consultation ON public.prescription USING btree (consultation_id);


--
-- TOC entry 4383 (class 1259 OID 17231)
-- Name: idx_principal_diagnosis; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_principal_diagnosis ON public.diagnosis USING btree (consultation_id) WHERE (principal = true);


--
-- TOC entry 4401 (class 2606 OID 17126)
-- Name: anamnesis anamnesis_consultation_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.anamnesis
    ADD CONSTRAINT anamnesis_consultation_id_fkey FOREIGN KEY (consultation_id) REFERENCES public.consultation(id) ON DELETE CASCADE;


--
-- TOC entry 4405 (class 2606 OID 17180)
-- Name: clinical_plan clinical_plan_consultation_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clinical_plan
    ADD CONSTRAINT clinical_plan_consultation_id_fkey FOREIGN KEY (consultation_id) REFERENCES public.consultation(id) ON DELETE CASCADE;


--
-- TOC entry 4402 (class 2606 OID 17139)
-- Name: current_medication current_medication_consultation_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.current_medication
    ADD CONSTRAINT current_medication_consultation_id_fkey FOREIGN KEY (consultation_id) REFERENCES public.consultation(id) ON DELETE CASCADE;


--
-- TOC entry 4404 (class 2606 OID 17167)
-- Name: diagnosis diagnosis_consultation_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.diagnosis
    ADD CONSTRAINT diagnosis_consultation_id_fkey FOREIGN KEY (consultation_id) REFERENCES public.consultation(id) ON DELETE CASCADE;


--
-- TOC entry 4409 (class 2606 OID 17261)
-- Name: interconsultation interconsultation_consultation_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interconsultation
    ADD CONSTRAINT interconsultation_consultation_id_fkey FOREIGN KEY (consultation_id) REFERENCES public.consultation(id) ON DELETE CASCADE;


--
-- TOC entry 4403 (class 2606 OID 17152)
-- Name: physical_evaluation physical_evaluation_consultation_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.physical_evaluation
    ADD CONSTRAINT physical_evaluation_consultation_id_fkey FOREIGN KEY (consultation_id) REFERENCES public.consultation(id) ON DELETE CASCADE;


--
-- TOC entry 4408 (class 2606 OID 17226)
-- Name: prescribed_medication prescribed_medication_prescription_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescribed_medication
    ADD CONSTRAINT prescribed_medication_prescription_id_fkey FOREIGN KEY (prescription_id) REFERENCES public.prescription(id) ON DELETE CASCADE;


--
-- TOC entry 4407 (class 2606 OID 17211)
-- Name: prescription prescription_consultation_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription
    ADD CONSTRAINT prescription_consultation_id_fkey FOREIGN KEY (consultation_id) REFERENCES public.consultation(id) ON DELETE CASCADE;


--
-- TOC entry 4406 (class 2606 OID 17195)
-- Name: procedure_performed procedure_performed_consultation_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.procedure_performed
    ADD CONSTRAINT procedure_performed_consultation_id_fkey FOREIGN KEY (consultation_id) REFERENCES public.consultation(id) ON DELETE CASCADE;


-- Completed on 2026-07-13 03:00:34 -05

--
-- PostgreSQL database dump complete
--

\unrestrict IgoaDiDP2Sfeps8dOkJEIo0lQAX081AdALkEzA7judIZuGsgSIOV6PM9ooAFfy8

