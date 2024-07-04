set search_path to "orti";

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




create trigger ControlloSpecieRepliche
after insert or update on piante
for each row
execute function specierepliche();

create trigger ControlloBiomassa
before insert or update on misure
for each row
execute function buomassa();