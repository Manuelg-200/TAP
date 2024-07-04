set search_path to "orti";
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


