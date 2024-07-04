set search_path to "orti"; 

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



select scuole.comune, gruppi.specie 
from scuole join piante on piante.scuola = scuole.codice join gruppi on piante.gruppo = gruppi.id 
group by scuole.comune, gruppi.specie;



select codice as scuola_rilevazione, classe_rilevazione, tipo_classe_rilevazione, ordine_classe_rilevazione, persona_rilevazione 
from (rilevazioni join persone on persona_rilevazione = mail join scuole on scuola_rilevazione = codice or scuola = codice) Q 
group by codice, classe_rilevazione, tipo_classe_rilevazione, ordine_classe_rilevazione, scuola_rilevazione, persona_rilevazione, scuola 
having count(*) >= all (
	select count(*) as n_rilevazioni 
	from rilevazioni join persone on persona_rilevazione = mail 
	where scuola_rilevazione = Q.codice or scuola = Q.codice  
	group by classe_rilevazione, tipo_classe_rilevazione, ordine_classe_rilevazione, scuola_rilevazione, persona_rilevazione, scuola
);