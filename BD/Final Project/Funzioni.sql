set search_path to "orti";

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

create or replace function abbinamentogruppo (in g1 int, in g2 int)
returns void
as
$$
declare 
begin
	if ((select scopo from gruppi where id = g1) = 'biomonitoraggio' and (select scopo from gruppi where id = g2) = 'biomonitoraggio') then
		if(select true from gruppi where id = g1 and specie = (select specie from gruppi where id = g2)) then
			if((select tipo from gruppi where id = g1) = 'controllo' and (select tipo from gruppi where id = g2) = 'monitoraggio') then 
				update gruppi set controparte = g1 where id = g2;
			elsif ((select tipo from gruppi where id = g2) = 'controllo' and (select tipo from gruppi where id = g1) = 'monitoraggio') then
				update gruppi set controparte = g2 where id = g1;
			else
				raise exception 'Un gruppo deve essere di controllo mentre l''altro di monitoraggio';
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



-- select nome, media, unita_misura from avgparametrireplica(710, 710, '2000-01-02', '2000-01-04');

-- select abbinamentogruppo(2, 3);