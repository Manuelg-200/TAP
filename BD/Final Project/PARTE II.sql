-- 4) Creazione schema

CREATE schema orti;
set search_path to "orti";


CREATE TABLE Scuole
	(codice VARCHAR(10) PRIMARY KEY,
	 nome VARCHAR(20) NOT NULL, 
	 comune VARCHAR(30) NOT NULL, 
	 provincia VARCHAR(20) NOT NULL, -- o forse meglio due char?
	 ciclo_istr VARCHAR(30) NOT NULL); -- da vedere se mettere enum

		
CREATE TABLE Persone
	(mail VARCHAR PRIMARY KEY,
	 nome VARCHAR(20) NOT NULL,
	 cognome VARCHAR(20) NOT NULL,
	 telefono NUMERIC(10),
	 ruolo VARCHAR(20) NOT NULL,
	 scuola VARCHAR(10),
	 FOREIGN KEY (scuola) REFERENCES Scuole,
	 referente BOOL,
	 CHECK ((scuola IS NOT NULL AND referente IS NOT NULL) OR (scuola IS NULL AND referente IS NULL)));

	 
CREATE TABLE Finanziamenti
	(id BIGINT PRIMARY KEY,
	 tipo VARCHAR NOT NULL,
	 titolare VARCHAR NOT NULL,
	 FOREIGN KEY (titolare) REFERENCES Persone);
	 
CREATE TABLE Classi
	(classe VARCHAR(2) NOT NULL,
	 tipo VARCHAR(20) NOT NULL, 
	 ordine VARCHAR(20) NOT NULL, --si potrebbe considerare l'uso dell'enum
	 scuola VARCHAR(10) NOT NULL,
	 FOREIGN KEY (scuola) REFERENCES Scuole,
	 PRIMARY KEY (classe, tipo, ordine, scuola),
	 docente VARCHAR NOT NULL,
	 FOREIGN KEY (docente) REFERENCES Persone);
	

CREATE TYPE TIPO_ORTO AS ENUM('campo', 'vaso');

CREATE TABLE Orti
	(nome VARCHAR(20) NOT NULL,
	 possessore VARCHAR(10) NOT NULL,
	 FOREIGN KEY (possessore) REFERENCES Scuole,
	 PRIMARY KEY (nome, possessore),
	 latitudine DECIMAL NOT NULL,
	 longitudine DECIMAL NOT NULL,
	 tipo TIPO_ORTO NOT NULL,
	 sup DECIMAL(6,2) NOT NULL,
	 substrato VARCHAR NOT NULL,
	 pulito BOOL NOT NULL,
	 CHECK (latitudine >= -90 AND latitudine < 90),
	 CHECK (longitudine >= -180 AND longitudine < 180));
	 
CREATE TABLE Sensori
	(id BIGINT PRIMARY KEY,
	 tipo VARCHAR NOT NULL,
	 codice_modello VARCHAR NOT NULL,
	 orto VARCHAR(20) NOT NULL,
	 scuola VARCHAR(10) NOT NULL,
	 FOREIGN KEY (orto, scuola) REFERENCES Orti);
	 
CREATE TYPE MOD_INS AS ENUM('app', 'auto'); 

CREATE TABLE Parametri_ambientali
	(data_ora_rilevazione TIMESTAMP NOT NULL,
	 orto VARCHAR(20) NOT NULL,
	 scuola VARCHAR(10) NOT NULL,
	 FOREIGN KEY (orto, scuola) REFERENCES Orti,
	 PRIMARY KEY (data_ora_rilevazione, orto, scuola),
	 data_ora_inserimento TIMESTAMP NOT NULL,
	 ph DECIMAL(4,2) NOT NULL,
	 temperatura DECIMAL(5,2) NOT NULL,
	 umidita DECIMAL(3) NOT NULL,
	 sensore BIGINT NOT NULL,
	 FOREIGN KEY (sensore) REFERENCES Sensori,
	 modalita MOD_INS NOT NULL
	 CHECK (data_ora_rilevazione <= data_ora_inserimento),
	 CHECK (ph >= 0 AND ph <= 14),
	 CHECK (umidita >= 0 AND umidita <= 100),
	 CHECK (temperatura > -273.15));
	 
CREATE TABLE Specie
	(nome_scientifico VARCHAR PRIMARY KEY,
	 nome_comune VARCHAR NOT NULL,
	 esposizione_consigliata VARCHAR NOT NULL);
	 
CREATE TABLE Piante
	(n_replica SMALLINT NOT NULL,
	 gruppo BIGINT NOT NULL,
	 PRIMARY KEY (n_replica, gruppo),
	 data TIMESTAMP NOT NULL,
	 esposizione VARCHAR NOT NULL,
	 classe VARCHAR(2) NOT NULL,
	 tipo_classe VARCHAR(20) NOT NULL,
	 ordine_classe VARCHAR(20) NOT NULL,
	 scuola VARCHAR(10) NOT NULL,
	 FOREIGN KEY (classe, tipo_classe, ordine_classe, scuola) REFERENCES Classi);
	
CREATE TYPE SCOPO_GRUPPO AS ENUM('fitobonifica','biomonitoraggio'); 
CREATE TYPE TIPO_GRUPPO AS ENUM('controllo','monitoraggio'); 

CREATE TABLE Gruppi
	(id BIGINT PRIMARY KEY,
	 scopo SCOPO_GRUPPO NOT NULL,
	 tipo TIPO_GRUPPO NOT NULL,
	 orto VARCHAR(20) NOT NULL,
	 scuola VARCHAR(10) NOT NULL,
	 FOREIGN KEY (orto, scuola) REFERENCES Orti,
	 controparte BIGINT, 
	 FOREIGN KEY (controparte) REFERENCES Gruppi,
	 specie VARCHAR NOT NULL,
	 FOREIGN KEY (specie) REFERENCES Specie);

CREATE TABLE Rilevazioni
	(data_ora_rilevazione TIMESTAMP NOT NULL,
	 replica SMALLINT NOT NULL,
	 gruppo BIGINT NOT NULL,
	 FOREIGN KEY (replica, gruppo) REFERENCES Piante,
	 PRIMARY KEY (data_ora_rilevazione, replica, gruppo),
	 data_ora_inserimento TIMESTAMP NOT NULL,
	 
	 classe_rilevazione VARCHAR(2),
	 tipo_classe_rilevazione VARCHAR(20), 
	 ordine_classe_rilevazione VARCHAR(20),
	 scuola_rilevazione VARCHAR(10),
	 FOREIGN KEY (classe_rilevazione, tipo_classe_rilevazione, ordine_classe_rilevazione, scuola_rilevazione) REFERENCES Classi,
	 persona_rilevazione VARCHAR,
	 FOREIGN KEY (persona_rilevazione) REFERENCES Persone,
	 
	 classe_inserimento VARCHAR(2),
	 tipo_classe_inserimento VARCHAR(20),
	 ordine_classe_inserimento VARCHAR(20),
	 scuola_inserimento VARCHAR(10),
	 FOREIGN KEY (classe_inserimento, tipo_classe_inserimento, ordine_classe_inserimento, scuola_inserimento) REFERENCES Classi,
	 persona_inserimento VARCHAR,
	 FOREIGN KEY (persona_inserimento) REFERENCES Persone,
	
	 CHECK ((classe_rilevazione IS NULL AND tipo_classe_rilevazione IS NULL AND ordine_classe_rilevazione IS NULL AND scuola_rilevazione IS NULL AND persona_rilevazione IS NOT NULL) OR (classe_rilevazione IS NOT NULL AND tipo_classe_rilevazione IS NOT NULL AND ordine_classe_rilevazione IS NOT NULL AND scuola_rilevazione IS NOT NULL AND persona_rilevazione IS NULL)),
	 CHECK ((classe_inserimento IS NULL AND tipo_classe_inserimento IS NULL AND ordine_classe_inserimento IS NULL AND scuola_inserimento IS NULL AND persona_inserimento IS NOT NULL) OR (classe_inserimento IS NOT NULL AND tipo_classe_inserimento IS NOT NULL AND ordine_classe_inserimento IS NOT NULL AND scuola_inserimento IS NOT NULL AND persona_inserimento IS NULL)),
	 CHECK (data_ora_rilevazione <= data_ora_inserimento));


CREATE TYPE CATEGORIA_MISURA AS ENUM('biomassa', 'alterazioni', 'danni'); 

CREATE TABLE Nomi_misure
	(nome VARCHAR(20) PRIMARY KEY,
	 categoria CATEGORIA_MISURA NOT NULL,
	 unita_misura VARCHAR(10));
	 
CREATE TABLE Misure
	(id BIGINT PRIMARY KEY,
	 nome VARCHAR(20) NOT NULL,
	 FOREIGN KEY (nome) REFERENCES Nomi_misure,
	 data_ora_rilevazione TIMESTAMP NOT NULL,
	 replica SMALLINT NOT NULL,
	 gruppo BIGINT NOT NULL,
	 FOREIGN KEY (data_ora_rilevazione, replica, gruppo) REFERENCES Rilevazioni,
	 note VARCHAR,
	 dato DECIMAL NOT NULL);
	 
	 
CREATE TABLE Destinatari_finanziamenti
	(scuola VARCHAR(10) NOT NULL,
	 FOREIGN KEY (scuola) REFERENCES Scuole,
	 finanziamento BIGINT NOT NULL,
	 FOREIGN KEY (finanziamento) REFERENCES Finanziamenti,
	 PRIMARY KEY (scuola, finanziamento));
	 
CREATE TABLE Monitoraggi
	(sensore BIGINT NOT NULL,
	 FOREIGN KEY (sensore) REFERENCES Sensori,
	 replica SMALLINT NOT NULL,
	 gruppo BIGINT NOT NULL,
	 FOREIGN KEY (replica, gruppo) REFERENCES Piante,
	 PRIMARY KEY (sensore, replica, gruppo));

CREATE TABLE Orti_condivisi 
	(scuola VARCHAR(10) NOT NULL,
	 FOREIGN KEY (scuola) REFERENCES Scuole,
	 orto VARCHAR(20) NOT NULL,
	 possessore VARCHAR(10) NOT NULL,
	 FOREIGN KEY (orto, possessore) REFERENCES Orti,
	 PRIMARY KEY (scuola, orto, possessore));


-- 6) Interrogazioni e Vista

-- determinare le scuole che, pur avendo un finanziamento per il progetto, non hanno inserito rilevazioni in questo anno scolastico;

select distinct D.scuola 
from destinatari_finanziamenti D 
where not exists (
	(
		select scuola_inserimento 
		from rilevazioni 
		where rilevazioni.scuola_inserimento = D.scuola and data_ora_inserimento BETWEEN '2023-09-01' AND '2024-06-30'
	) 
	union 
	(
		select persone.scuola 
		from persone join rilevazioni on persone.mail = rilevazioni.persona_inserimento 
		where persone.scuola = D.scuola and data_ora_inserimento BETWEEN '2023-09-01' AND '2024-06-30'
	)
);

-- determinare le specie utilizzate in tutti i comuni in cui ci sono scuole aderenti al progetto

select distinct nome_scientifico 
from specie SP
where not exists (
	select * 
	from scuole SC
	where not exists (
		select *
		from scuole join piante on piante.scuola = scuole.codice join gruppi on piante.gruppo = gruppi.id 
		where specie = SP.nome_scientifico and comune = SC.comune
	)
);

-- determinare per ogni scuola l’individuo/la classe della scuola che ha effettuato più rilevazioni.

-- la subquery è necessaria per prendere il maggiore fra classe e persone 
select codice as scuola_rilevazione, classe_rilevazione, tipo_classe_rilevazione, ordine_classe_rilevazione, persona_rilevazione 
from (rilevazioni join persone on persona_rilevazione = mail join scuole on scuola_rilevazione = codice or scuola = codice) Q 
group by codice, classe_rilevazione, tipo_classe_rilevazione, ordine_classe_rilevazione, scuola_rilevazione, persona_rilevazione, scuola 
having count(*) >= all (
	select count(*) as n_rilevazioni 
	from rilevazioni join persone on persona_rilevazione = mail 
	where scuola_rilevazione = Q.codice or scuola = Q.codice  
	group by classe_rilevazione, tipo_classe_rilevazione, ordine_classe_rilevazione, scuola_rilevazione, persona_rilevazione, scuola
);

-- La definizione di una vista che fornisca alcune informazioni riassuntive per ogni attività di biomonitoraggio: per ogni gruppo e per il corrispondente gruppo di controllo mostrare il numero di piante, la specie, l’orto in cui è posizionato il gruppo e, su base mensile, il valore medio dei parametri ambientali e di crescita delle piante (selezionare almeno tre parametri, quelli che si ritengono più significativi). 

--abbaimo interpretato la richiesta come un controllo delle misurazioni fra gruppo di controllo e monitoraggio per mese, per cui se non ci sono misurazioni per quel gruppo in uno dei mesi, non verrà visualizzata la tupla
--dalla consegna si intuiva che i parametri da predere su base mensile fossero quelli relativi a quel gruppo
create or replace view misurazionigruppopermese as
select A.id_gruppo_monitoraggio, A.n_painte_monitoraggio, A.orto_monitoraggio, B.id_gruppo_controllo, B.n_painte_controllo, B.orto_controllo, A.specie, A.anno, A.mese, A.ph_monitoaggio, A.temperatura_monitoraggio, A.umdita_monitoraggio, B.ph_controllo, B.temperatura_controllo, B.umdita_controllo from
(
	select  gruppi.id as id_gruppo_monitoraggio, gruppi.controparte, count(*) as n_painte_monitoraggio, gruppi.specie, gruppi.orto as orto_monitoraggio, extract(month from data_ora_rilevazione) as mese, extract(year from data_ora_rilevazione) as anno, avg(ph) as ph_monitoaggio, avg(temperatura) as temperatura_monitoraggio, avg(umidita) as umdita_monitoraggio 
	from piante 
	join gruppi on piante.gruppo = gruppi.id 
	left outer join parametri_ambientali on gruppi.scuola = parametri_ambientali.scuola and gruppi.orto = parametri_ambientali.orto
	group by gruppi.id, gruppi.specie, gruppi.orto, extract(month from data_ora_rilevazione), extract(year from data_ora_rilevazione)
	) A
join (
	select  gruppi.id as id_gruppo_controllo, gruppi.orto as orto_controllo, count(*) as n_painte_controllo, extract(month from data_ora_rilevazione) as mese, extract(year from data_ora_rilevazione) as anno, avg(ph) as ph_controllo, avg(temperatura) as temperatura_controllo, avg(umidita) as umdita_controllo
	from piante 
	join gruppi on piante.gruppo = gruppi.id 
	left outer join parametri_ambientali on gruppi.scuola = parametri_ambientali.scuola and gruppi.orto = parametri_ambientali.orto
	group by gruppi.id, gruppi.specie, gruppi.orto, extract(month from data_ora_rilevazione), extract(year from data_ora_rilevazione) 
) B on A.controparte = B.id_gruppo_controllo
where (A.anno = B.anno) and (A.mese = B.mese);


-- 7) Funzioni e procedure 

-- funzione che realizza l’abbinamento tra gruppo e gruppo di controllo nel caso di operazioni di biomonitoraggio

create or replace function abbinamentogruppo (in g1 int, in g2 int)
returns void
as
$$
declare 
begin
	if ((select scopo from gruppi where id = g1) = 'biomonitoraggio' and (select scopo from gruppi where id = g2) = 'biomonitoraggio') then
		if(select true from gruppi where id = g1 and specie = (select specie from gruppi where id = g2)) then
			if((select count(*) from piante where piante.gruppo = g1) = (select count(*) from piante where piante.gruppo = g2)) then
				if((select tipo from gruppi where id = g1) = 'controllo' and (select tipo from gruppi where id = g2) = 'monitoraggio') then 
					update gruppi set controparte = g1 where id = g2;
				elsif ((select tipo from gruppi where id = g2) = 'controllo' and (select tipo from gruppi where id = g1) = 'monitoraggio') then
					update gruppi set controparte = g2 where id = g1;
				else
					raise exception 'Un gruppo deve essere di controllo mentre l''altro di monitoraggio';
				end if;
			else
				raise exception 'I due gruppi devono avere lo stesso numero di repliche';
			end if;
		else
			raise exception 'I due gruppi devono appartenere alla stessa specie';
		end if;
	else
		raise exception 'I due gruppi devono essere entrambi di biomonitoraggio';
	end if;
end;
$$
language plpgsql;

-- funzione che corrisponde alla seguente query parametrica: data una replica con finalità di fitobonifica e due date, determina i valori medi dei parametri rilevati per tale replica nel periodo compreso tra le due date

-- r e g sono le chiavi per la replica (n_replica, gruppo)
create function avgparametrireplica (in r int, in g int, in data1 timestamp, in data2 timestamp)
returns table (nome VARCHAR(20), media DECIMAL, unita_misura VARCHAR(20))
as
$$
declare 
begin
	return query (select misure.nome, avg(dato)as media from misure where misure.replica = r and misure.gruppo = g and data_ora_rilevazione between data1 and data2 group by misure.nome);
end;
$$
language plpgsql;

-- 8) Trigger

-- verifica del vincolo che ogni scuola dovrebbe concentrarsi su tre specie e ogni gruppo dovrebbe contenere 20 repliche;

create or replace function specierepliche() returns trigger as
$$
declare 
begin
	if((select count(distinct orti.gruppi.specie) from orti.piante join orti.gruppi on orti.piante.gruppo = orti.gruppi.id where orti.piante.scuola = new.scuola) > 3) then
		raise exception 'Troppe specie';
	end if;
	if((select count(*) from orti.piante where orti.piante.gruppo = new.gruppo) > 20) then
		raise exception 'Troppe repliche';
	end if;
	return new;
end;
$$
language plpgsql;

create trigger ControlloSpecieRepliche
after insert or update on piante
for each row
execute function specierepliche();

-- generazione di un messaggio (o inserimento di una informazione di warning in qualche tabella) quando viene rilevato un valore decrescente per un parametro di biomassa.

create or replace function biomassa() returns trigger as
$$
declare 
begin
	if((select orti.nomi_misure.categoria from orti.nomi_misure where orti.nomi_misure.nome = new.nome) = 'biomassa') then
		if(new.dato < (select orti.misure.dato from orti.misure natural join orti.nomi_misure where orti.nomi_misure.categoria = 'biomassa' and orti.misure.nome = new.nome and orti.misure.replica = new.replica and orti.misure.gruppo = new.gruppo and orti.misure.data_ora_rilevazione = (select max(orti.misure.data_ora_rilevazione) from orti.misure natural join orti.nomi_misure where orti.nomi_misure.categoria = 'biomassa' and orti.misure.nome = new.nome and orti.misure.replica = new.replica and orti.misure.gruppo = new.gruppo))) then
			raise notice 'Attenzione, valore di bio massa in diminuzione';
		end if;
	end if;
	return new;
end;
$$
language plpgsql;

create trigger ControlloBiomassa
before insert or update on misure
for each row
execute function buomassa();