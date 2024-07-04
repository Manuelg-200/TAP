set search_path to "orti";

-- 11. Schema fisico e query carico di lavoro

CREATE INDEX idx_ord_parametri_ambientali
ON parametri_ambientali(umidita);

CLUSTER parametri_ambientali
USING idx_ord_parametri_ambientali;

CREATE INDEX idx_hash_specie_nome_comune
ON specie USING HASH(nome_comune);

CREATE INDEX idx_hash_gruppi_specie
ON gruppi USING HASH (specie);

CREATE INDEX idx_hash_orti_nome
ON orti USING HASH (nome);

CREATE INDEX idx_hash_persone_ruolo
ON persone USING HASH (ruolo);

CREATE INDEX idx_hash_rilevazioni_persona_rilevazione
ON rilevazioni USING HASH (persona_rilevazione);

CREATE INDEX idx_hash_destinatari_finanziamenti_scuola
ON destinatari_finanziamenti(scuola);

CLUSTER destinatari_finanziamenti
USING idx_hash_destinatari_finanziamenti_scuola;


--I nomi degli orti che contengono cetrioli
select orti.nome from orti join gruppi on orti.nome = gruppi.orto and orti.possessore = gruppi.scuola join specie on gruppi.specie = specie.nome_scientifico where specie.nome_comune = 'cetriolo';

--Le piante su cui sono state fatte rilevazioni da un dirigente, referente di una scuola che ha ricevuto almeno un finanziamento
select rilevazioni.replica, rilevazioni.gruppo from rilevazioni, persone, destinatari_finanziamenti where rilevazioni.persona_rilevazione = persone.mail and persone.ruolo = 'dirigente' and persone.scuola = destinatari_finanziamenti.scuola and persone.referente = true;

--I parametri ambientali con ph > 4 o temperatura < 10 e umidita compresa fra il 10 e il 90 % maggiore rilevati nel 2022
select P.data_ora_rilevazione, P.orto, P.scuola from parametri_ambientali P where extract (year from P.data_ora_rilevazione) = 2022 and (P.ph >= 3 or temperatura < 10) and (umidita > 10 and umidita < 90);


-- 12. controllo dell'accesso

CREATE ROLE studente;
GRANT select ON orti, parametri_ambientali, sensori, specie, piante, gruppi, rilevazioni, misure, nomi_misure, monitoraggi, orti_condivisi, misurazionigruppopermese TO studente;
GRANT update, insert ON parametri_ambientali, sensori, piante, rilevazioni, misure, monitoraggi TO studente;

CREATE ROLE insegnante;
GRANT studente TO insegnante;
GRANT select ON classi TO insegnante;
GRANT update, insert ON gruppi TO insegnante;

CREATE ROLE referente_scuola;
GRANT insegnante TO referente_scuola;
GRANT select ON scuole, finanziamenti, persone, destinatari_finanziamenti TO referente_scuola;
GRANT update, insert ON persone, classi, orti, orti_condivisi TO referente_scuola;

CREATE ROLE gestore_globale;
GRANT referente_scuola TO gestore_globale WITH ADMIN OPTION;
GRANT update, insert ON scuole, finanziamenti, specie, nomi_misure, destinatari_finanziamenti TO gestore_globale WITH GRANT OPTION;


CREATE USER marco PASSWORD 'password';
CREATE USER gigi PASSWORD 'password';
CREATE USER gina PASSWORD 'password';
CREATE USER pina PASSWORD 'password';
CREATE USER ugo PASSWORD 'password';

GRANT studente to marco;
GRANT studente to gigi;
GRANT insegnante to gina;
GRANT referente_scuola to pina;
GRANT gestore_globale to ugo;

