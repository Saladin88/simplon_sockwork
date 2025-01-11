DROP TABLE IF EXISTS t_accounts,t_roles,t_accounts_roles;

CREATE TABLE t_accounts (
	id INT GENERATED ALWAYS AS IDENTITY,
	username VARCHAR(255),
	password VARCHAR(72),
	CONSTRAINT t_account_pkey PRIMARY KEY (id),
	CONSTRAINT t_account_ukey UNIQUE (username)
);

create table t_roles(
id int generated always as identity,
code varchar(15) not null,
description text not null,
default_code boolean not null,
constraint t_roles_pkey primary key (id),
constraint t_roles_code_ukey unique (code),
constraint t_roles_description_ukey unique (description)
);


create table t_accounts_roles(
id_accounts int not null,
id_roles int not null,
constraint t_accounts_roles_pkey primary key(id_accounts, id_roles),
constraint t_accounts_roles_t_accounts_fkey foreign key (id_accounts) references t_accounts(id),
constraint t_accounts_roles_t_roles_fkey foreign key (id_roles) references t_roles(id),
constraint t_accounts_roles_ukey unique (id_accounts,id_roles)
);
